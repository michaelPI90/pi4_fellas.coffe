package com.web.pi3s.SpringWeb.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;

@Transactional
@Entity
@Table(name = "TB_IMAGENS")
public class Imagenmodels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;
    @Lob
    private byte[] conteudoImagem;
    private String nomeImagem;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tb_produto_id")
    private Produtomodels produto;

    public Imagenmodels() {
    }

    public byte[] getConteudoImagem() {
        return conteudoImagem;
    }

    public void setConteudoImagem(byte[] conteudoImagem) {
        this.conteudoImagem = conteudoImagem;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public Produtomodels getProduto() {
        return produto;
    }

    public void setProduto(Produtomodels produto) {
        this.produto = produto;
    }

}