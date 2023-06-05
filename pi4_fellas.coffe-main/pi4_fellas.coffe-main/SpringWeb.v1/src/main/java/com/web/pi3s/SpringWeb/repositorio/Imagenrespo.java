package com.web.pi3s.SpringWeb.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.web.pi3s.SpringWeb.models.Imagenmodels;

public interface Imagenrespo extends  CrudRepository<Imagenmodels, Integer>  {
    
    Optional<Imagenmodels> findBynomeImagem(String nomeProduto);
   
    Imagenmodels findByid(Integer id);

    List<Imagenmodels> findBynomeImagemContainingIgnoreCase(String nomeProduto);

    Optional<Imagenmodels> findById(Long id);

}
