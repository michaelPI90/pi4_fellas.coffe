package com.web.pi3s.SpringWeb.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.pi3s.SpringWeb.models.Produtomodels;

public interface Produtorespo extends JpaRepository<Produtomodels, Integer> {

    Optional<Produtomodels> findByNomeProduto(String nomeProduto);

}
