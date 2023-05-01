package com.web.pi3s.SpringWeb.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.web.pi3s.SpringWeb.models.Clientemodels;
import com.web.pi3s.SpringWeb.models.Produtomodels;

public interface Clienterespo extends CrudRepository<Clientemodels, Integer> {

    Optional<Clientemodels> findByEmail(String email);
    

}
