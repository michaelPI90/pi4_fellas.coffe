package com.web.pi3s.SpringWeb.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_ITEM_COMPRA")
public class ItenCompraProduto {
    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Produtomodels produto;


    @ManyToOne
    private Compra compra;

    private Integer quantidade;
    


    private Double valorUnitario=0.0;
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    private Double valorTotal=0.0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produtomodels getProduto() {
        return produto;
    }

    public void setProduto(Produtomodels produto) {
        this.produto = produto;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Integer getQuantidade() {
        if(quantidade == null){
            quantidade = 0;
        }
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

 

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public ItenCompraProduto(int id, Produtomodels produto, Compra compra, Integer quantidade, Double valorUnitario,
            Double valorTotal) {
        this.id = id;
        this.produto = produto;
        this.compra = compra;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
    }

    public ItenCompraProduto() {
    }

    




}
