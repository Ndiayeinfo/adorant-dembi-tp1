package com.isi.diti5.tp1_jee.web;

import com.isi.diti5.tp1_jee.entities.Utilisateur;
import com.isi.diti5.tp1_jee.repository.UtilisateurRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    @GetMapping("/users")
    public List<Utilisateur> userList(){
        return utilisateurRepository.findAll();
    }

    @Operation(
            summary = "Retourne un utilisateur via son Id",
            description = "Cette methode nous retourne l'id, le nom et l'email de l'utilisateur demandé"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Utilisateur.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/users/{id}")
    public Utilisateur userById(@PathVariable Long id){
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
    @PostMapping("/users")
    public Utilisateur saveUser(@RequestBody Utilisateur utilisateur){
        utilisateurRepository.save(utilisateur);
        return utilisateur;
    }

    @Operation(
            summary = "Supprime un utilisateur dont l'id est passé en paramètre",
            description = "Cette méthode récupère l'identifiant de l'utilisateur puis le supprime de la BD"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Utilisateur.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        utilisateurRepository.deleteById(id);
        return "Success";
    }
}
