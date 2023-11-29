package com.isi.diti5.tp1_jee.web;

import com.isi.diti5.tp1_jee.entities.Utilisateur;
import com.isi.diti5.tp1_jee.repository.UtilisateurRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
@AllArgsConstructor
public class UtilisateurRestController {

    private UtilisateurRepository utilisateurRepository;

    @Operation(
            summary = "retourne la liste de tous les utilisateurs",
            description = "Cette méthode nous retourne l'ensemble des utilisateurs contenus dans notre base de données"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Utilisateur.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/user/index")
    @PreAuthorize("hasRole('USER')")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int p,
                        @RequestParam(name = "size", defaultValue = "10") int s,
                        @RequestParam(name = "keyword", defaultValue = "") String kw){
        //Page<Utilisateur> pageUsers = utilisateurRepository.findAll(PageRequest.of(p,s));
        Page<Utilisateur> pageUsers = utilisateurRepository.findByNomContains(kw, PageRequest.of(p,s));
        model.addAttribute("listUsers", pageUsers.getContent());
        model.addAttribute("pages", new int[pageUsers.getTotalPages()]);
        model.addAttribute("currentPage", p);
        model.addAttribute("keyword", kw);
        return "users/users";
    }

    @GetMapping("/")
    public String home()
    {
        return "redirect:/user/index";
    }

    @Operation(
            summary = "Retourne un utilisateur via son Id",
            description = "Cette methode nous retourne l'id, le nom et l'email de l'utilisateur demandé"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Utilisateur.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/getUserById/{id}")
    public Utilisateur getUserById(@PathVariable Long id){
        return utilisateurRepository.findById(id).get();
    }

    @Operation(
            summary = "Ajoute un utilisateur",
            description = "Cette méthode ajoute un nouvel utilisateur dans la base"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Utilisateur.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/admin/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String save(@Valid Utilisateur utilisateur, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "users/add";
        }
        utilisateurRepository.save(utilisateur);
        return "redirect:/user/index?keyword="+utilisateur.getNom();
    }

    @Operation(
            summary = "Supprime un utilisateur dont l'id est passé en paramètre",
            description = "Cette méthode récupère l'identifiant de l'utilisateur puis le supprime de la BD"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Utilisateur.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@RequestParam(name = "id") Long id,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword){
        utilisateurRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/admin/saveForm")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveForm(Model model)
    {
        model.addAttribute("utilisateur", new Utilisateur());
        return "users/add";
    }

    @GetMapping("/admin/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String edit(Model model, @RequestParam(name = "id") Long id)
    {
        Utilisateur utilisateur= utilisateurRepository.findById(id).get();
        model.addAttribute("utilisateur", utilisateur);
        return "users/edit";
    }
}
