package com.web.pi3s.SpringWeb.models;




import java.util.ArrayList;
import java.util.List;



import jakarta.persistence.*;
import jakarta.transaction.Transactional;


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
    private boolean statusAtivo = true;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List <Imagenmodels> imagem;

  

   
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produtomodels() {
    }
    
    public Produtomodels(Integer id, String nomeProduto, double precoProduto, int qntdEstoque, String avaliacao,
            boolean statusAtivo) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.qntdEstoque = qntdEstoque;
        this.avaliacao = avaliacao;
        this.statusAtivo = statusAtivo;
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

    public boolean isStatusAtivo() {
        return statusAtivo;
    }

    public void setStatusAtivo(boolean statusAtivo) {
        this.statusAtivo = statusAtivo;
    }


    
    public List<Imagenmodels> getImagem() {
        return imagem;
    }

    public void setImagem(List<Imagenmodels> imagem) {
        this.imagem = imagem;
    }

    public Produtomodels(List<Imagenmodels> imagem) {
        this.imagem =  new ArrayList<>();
    }

    public void setImagem(byte[] bytes) {
    }
   
   
    
    

   

}
