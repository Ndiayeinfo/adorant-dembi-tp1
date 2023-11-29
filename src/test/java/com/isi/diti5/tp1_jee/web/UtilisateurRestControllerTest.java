package com.isi.diti5.tp1_jee.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.isi.diti5.tp1_jee.entities.Utilisateur;
import com.isi.diti5.tp1_jee.repository.UtilisateurRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UtilisateurRestController.class)
class UtilisateurRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private UtilisateurRestController utilisateurRestController;
    private Utilisateur utilisateurOne;
    private Utilisateur utilisateurTwo;
    List<Utilisateur> utilisateurList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        utilisateurOne = new Utilisateur(1L, "jeremie", "jeremie@gmail.com");
        utilisateurTwo = new Utilisateur(2L, "youssou", "youssou@gmail.com");
        utilisateurList.add(utilisateurOne);
        utilisateurList.add(utilisateurTwo);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void TestUserList() throws Exception{
        when(utilisateurRepository.findAll()).thenReturn(utilisateurList);
        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void TestUserById() throws Exception {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.ofNullable(utilisateurOne));
        this.mockMvc.perform(get("/users/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void TestSaveUser() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(utilisateurOne);

        when(utilisateurRepository.save(utilisateurOne)).thenReturn(utilisateurOne);
        this.mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void TestDeleteUser() {
        mock(Utilisateur.class);
        mock(UtilisateurRepository.class, Mockito.CALLS_REAL_METHODS);

        doAnswer(Answers.CALLS_REAL_METHODS).when(
                utilisateurRepository).deleteById(any());
        //assertThat(utilisateurRestController.delete(1L)).isEqualTo("Success");
    }
}