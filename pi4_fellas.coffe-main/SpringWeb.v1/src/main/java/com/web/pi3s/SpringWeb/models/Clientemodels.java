package com.web.pi3s.SpringWeb.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * 
 * Uma entidade de usuário que serve
 */
@Entity
@Table(name = "TB_CLIENTE")
public class Clientemodels {
    //private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String senha;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String dataNasc;
    @Column(nullable = false)
    private String enderecoFaturamento;
    @Column(nullable = false)
    private String enderecoEntrega;
    @Column(nullable = false)
    private String cep;
    @Column(nullable = false, unique = true)
    private String cpf;
    @Column(nullable = false, unique = true)
    private String genero;
    @Column(nullable = false)
    private boolean statusAtivo = true;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }
   
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }

  
    public String getEnderecoFaturamento() {
        return enderecoFaturamento;
    }
    public void setEnderecoFaturamento(String enderecoFaturamento) {
        this.enderecoFaturamento = enderecoFaturamento;
    }
    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }
    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    

    public Clientemodels(int id, String nome, String senha, String email, String dataNasc, String enderecoFaturamento,
            String enderecoEntrega, String cep, String cpf, String genero) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.dataNasc = dataNasc;
        this.enderecoFaturamento = enderecoFaturamento;
        this.enderecoEntrega = enderecoEntrega;
        this.cep = cep;
        this.cpf = cpf;
        this.genero = genero;
    }
    public Clientemodels() {
    }

    

    


    

    
    
    

    

   

}
