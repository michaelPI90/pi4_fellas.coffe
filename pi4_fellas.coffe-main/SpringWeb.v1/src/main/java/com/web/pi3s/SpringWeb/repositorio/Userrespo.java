package com.web.pi3s.SpringWeb.repositorio;

import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.web.pi3s.SpringWeb.models.Usermodels;

public interface Userrespo extends JpaRepository<Usermodels, UUID> {

    Optional<Usermodels> findByEmail(String email);

    Optional<Usermodels> findByCpf(String cpf);

    Optional<Usermodels> findByUsername(String username);

    Usermodels findByUserId(UUID userId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value="UPDATE TB_USER SET username=?, email=?, grupo=?, status_ativo=? WHERE user_id=?", nativeQuery = true)
    public Integer alterarUsuario(String username, String email, String grupo, boolean statusAtivo, UUID userId);
   

}
