package com.web.pi3s.SpringWeb.repositorio;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.web.pi3s.SpringWeb.models.Clientemodels;
import com.web.pi3s.SpringWeb.models.Compra;
import com.web.pi3s.SpringWeb.models.Produtomodels;

import java.util.List;


public interface Comprasrespo extends  CrudRepository<Compra, Integer>  {

Compra findByCliente(Clientemodels cliente);

@Query(value="select * from tb_compra where cliente_id = ? order by data_compra desc", nativeQuery = true)
public List<Compra> ordernarPedidos(int id);

Compra findById(int id);
}
