package com.web.pi3s.SpringWeb.models;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import jakarta.persistence.Lob;

import jakarta.persistence.Table;


/**
 * 
 * Uma entidade de usuário que serve
 */
@Entity
@Table(name = "TB_PRODUTO")
public class Produtomodels {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;
    @Column(nullable = false)
    private String nomeProduto;
    @Column(nullable = false, length = 2000)
    private String descricaoDetalhada;
    @Column(nullable = false)
    private double precoProduto;
    @Column(nullable = false)
    private int qntdEstoque;
    @Column(nullable = false)
    private String avaliacao;
    @Column(nullable = false)
    private boolean statusAtivo;
    private String imagemProduto;

    

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }
    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
    }
    public double getPrecoProduto() {
        return precoProduto;
    }
    public void setPrecoProduto(double precoProduto) {
        this.precoProduto = precoProduto;
    }
    public int getQntdEstoque() {
        return qntdEstoque;
    }
    public void setQntdEstoque(int qntdEstoque) {
        this.qntdEstoque = qntdEstoque;
    }
    public String getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }
   
    
    
   
    public String getImagemProduto() {
        return imagemProduto;
    }
    public void setImagemProduto(String imagemProduto) {
        this.imagemProduto = imagemProduto;
    }
    public boolean isStatusAtivo() {
        return statusAtivo;
    }
    public void setStatusAtivo(boolean statusAtivo) {
        this.statusAtivo = statusAtivo;
    }
   
    
    

   

}
