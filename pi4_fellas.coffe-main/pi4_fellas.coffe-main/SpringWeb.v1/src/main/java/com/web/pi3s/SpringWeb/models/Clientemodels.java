package com.web.pi3s.SpringWeb.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class Clientemodels implements UserDetails {
    // private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;
    @Column(nullable = true)
    private String nome;
    @Column(nullable = true)
    private String password;

    @Column(nullable = true, unique = true)
    private String cpf;
 
    private String genero;
    @Column(nullable = false)
    private boolean statusAtivo = true;

    @Column(nullable = true)
    private String password2;
    @Column(nullable = true, unique = true)
    private String email;
    @Column(nullable = true)
    @DateTimeFormat(pattern = "dd-MMM-YYYY")
    private String dataNasc;
    @Column(nullable = true)
    private String enderecoFaturamento;
    @Column(nullable = true)  
    private List<String> enderecosEntrega = new ArrayList<>();

    

  

    @Override
    public String toString() {
        return "Clientemodels id=" + id + ", nome=" + nome + ", password=" + password + ", cpf=" + cpf + ", genero="
                + genero + ", statusAtivo=" + statusAtivo + ", password2=" + password2 + ", email=" + email
                + ", dataNasc=" + dataNasc + ", enderecoFaturamento=" + enderecoFaturamento + ", enderecosEntrega="
                + enderecosEntrega + "";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

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

    


    public Clientemodels(int id, String nome, String password, String cpf, String genero, boolean statusAtivo,
            String password2, String email, String dataNasc, String enderecoFaturamento,
            List<String> enderecosEntrega) {
        this.id = id;
        this.nome = nome;
        this.password = password;
        this.cpf = cpf;
        this.genero = genero;
        this.statusAtivo = statusAtivo;
        this.password2 = password2;
        this.email = email;
        this.dataNasc = dataNasc;
        this.enderecoFaturamento = enderecoFaturamento;
        this.enderecosEntrega = enderecosEntrega;
    }

    public Clientemodels() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public boolean isStatusAtivo() {
        return statusAtivo;
    }

    public void setStatusAtivo(boolean statusAtivo) {
        this.statusAtivo = statusAtivo;
    }

    public List<String> getEnderecosEntrega() {
        return enderecosEntrega;
    }

    public void setEnderecosEntrega(List<String> enderecosEntrega) {
        this.enderecosEntrega = enderecosEntrega;
    }

}

    


