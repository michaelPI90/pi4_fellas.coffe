package com.web.pi3s.SpringWeb.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.web.pi3s.SpringWeb.models.Clientemodels;
import com.web.pi3s.SpringWeb.models.Compra;
import com.web.pi3s.SpringWeb.models.Produtomodels;

import java.util.List;
import java.util.Optional;
@Repository
public interface Comprasrespo extends JpaRepository<Compra, Long> {

    Compra findByCliente(Clientemodels cliente);

    @Query(value = "select * from tb_compra where cliente_id = ? order by data_compra desc", nativeQuery = true)
    public List<Compra> ordernarPedidos(int id);

      @Query(value = "SELECT * FROM tb_compra ORDER BY data_compra DESC", nativeQuery = true)
    List<Compra> ordernarPedidos();

    Compra findById(int id);

    Optional<Compra> findBynumeroPedido(String numeroPedido);
}
