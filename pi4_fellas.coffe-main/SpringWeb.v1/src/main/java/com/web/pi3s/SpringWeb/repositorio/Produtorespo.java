package com.web.pi3s.SpringWeb.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.pi3s.SpringWeb.models.Produtomodels;

public interface Produtorespo extends JpaRepository<Produtomodels, Integer> {

    Optional<Produtomodels> findByNomeProduto(String nomeProduto);
    List<Produtomodels> findByNomeProdutoContainingIgnoreCase(String nomeProduto);
   
    @Query(value="select * from tb_produto order by id desc", nativeQuery = true)
    public List<Produtomodels> ordernar();

    

}
