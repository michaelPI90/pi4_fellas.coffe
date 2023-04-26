package com.web.pi3s.SpringWeb.repositorio;

import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.pi3s.SpringWeb.models.Usermodels;

public interface Userrespo extends JpaRepository<Usermodels, UUID> {

    Optional<Usermodels> findByEmail(String email);

    Optional<Usermodels> findByCpf(String cpf);

    Optional<Usermodels> findByUsername(String username);

    Usermodels findByUserId(UUID id);

    // @Query(value="update tb_user set cpf=?, username=?, password=?, email=?, grupo=?, statusAtivo=? where userId = :userId", nativeQuery = true)
    // public Usermodels updateUser (@Param("id") UUID userId );
    //public Usermodels updateUser (@Param("cpf") String cpf, @Param("username") String username, @Param("password") String password, @Param("email") String email, @Param("grupo") String grupo, @Param("statusAtivo") boolean statusAtivo);




}
