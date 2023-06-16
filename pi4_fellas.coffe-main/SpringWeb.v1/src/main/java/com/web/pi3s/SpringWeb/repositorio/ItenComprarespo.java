package com.web.pi3s.SpringWeb.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.web.pi3s.SpringWeb.models.Compra;
import com.web.pi3s.SpringWeb.models.ItenCompraProduto;


public interface ItenComprarespo extends CrudRepository<ItenCompraProduto, Integer> {

    

    List<ItenCompraProduto> findByid(Integer userID);

    ItenCompraProduto findByCompra(Compra compra);

}
