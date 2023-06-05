package com.web.pi3s.SpringWeb.repositorio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.web.pi3s.SpringWeb.models.Clientemodels;

public interface Clienterespo extends CrudRepository<Clientemodels, Integer> {

    Clientemodels findByEmail(String email);
 

    Optional<Clientemodels> findByCpf(String cpf);

 
    

}
