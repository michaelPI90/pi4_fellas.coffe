package com.web.pi3s.SpringWeb.repositorio;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.pi3s.SpringWeb.models.Usermodels;




public interface Userrespo extends JpaRepository<Usermodels, UUID>{

    
    Optional<Usermodels> findByUsername(String username);


    
}
