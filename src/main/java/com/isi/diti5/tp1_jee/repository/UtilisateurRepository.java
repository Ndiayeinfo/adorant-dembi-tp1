package com.isi.diti5.tp1_jee.repository;

import com.isi.diti5.tp1_jee.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

}
