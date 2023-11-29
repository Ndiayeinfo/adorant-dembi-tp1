package com.isi.diti5.tp1_jee.repository;

import com.isi.diti5.tp1_jee.entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Page<Utilisateur> findByNomContains(String keyword, Pageable pageable);

    @Query("select p from Utilisateur p where p.nom like :x")

    Page<Utilisateur> chercher(@Param("x") String keyword, Pageable pageable);
}
