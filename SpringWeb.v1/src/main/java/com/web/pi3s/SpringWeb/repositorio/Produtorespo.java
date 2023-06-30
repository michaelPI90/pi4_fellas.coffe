package com.web.pi3s.SpringWeb.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.web.pi3s.SpringWeb.models.Produtomodels;

public interface Produtorespo extends CrudRepository<Produtomodels, Integer> {

    Optional<Produtomodels> findByNomeProduto(String nomeProduto);
   
   Produtomodels findByid(Long userID);
Optional <Produtomodels> findById(Long userID);

    List<Produtomodels> findByNomeProdutoContainingIgnoreCase(String nomeProduto);

  

     @Query(value="select * from tb_produto order by id desc", nativeQuery = true)
     public List<Produtomodels> ordernar();

     @Modifying(clearAutomatically = true)
     @Transactional
     @Query(value="UPDATE TB_PRODUTO SET qntd_estoque=? WHERE id=?", nativeQuery = true)
     public Integer alterarProduto(int qntd_estoque, Long long1);
    

}
