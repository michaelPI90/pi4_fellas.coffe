package com.web.pi3s.SpringWeb.models;





import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_IMAGENS")
public class Imagenmodels {
    


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)


    private int Id;
    private byte[] conteudoImagem;
    private String nomeImagem;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TB_PRODUTO_Id")
    private Produtomodels produto;



    
      public byte[] getConteudoProduto() {
        return conteudoImagem;
    }
    public void setConteudoProduto(byte[] conteudoImagem) {
        this.conteudoImagem = conteudoImagem;
    }



    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public byte[] getConteudoImagem() {
        return conteudoImagem;
    }
    public void setConteudoImagem(byte[] conteudoImagem) {
        this.conteudoImagem = conteudoImagem;
    }
    public Produtomodels getProduto() {
        return produto;
    }
    public void setProduto(Produtomodels produto) {
        this.produto = produto;
    }
    public Imagenmodels() {
    }
    public String getNomeImagem() {
        return nomeImagem;
    }
    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }


}
