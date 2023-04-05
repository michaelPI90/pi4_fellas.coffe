package com.web.pi3s.SpringWeb.repositorio;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;

import com.web.pi3s.SpringWeb.models.Rolesmodels;
import com.web.pi3s.SpringWeb.models.Usermodels;




public interface Userrespo extends JpaRepository<Usermodels, UUID>{

    
    Optional<Usermodels> findByEmail(String email);
    Optional<Usermodels> findByCpf(String cpf);



    
}
