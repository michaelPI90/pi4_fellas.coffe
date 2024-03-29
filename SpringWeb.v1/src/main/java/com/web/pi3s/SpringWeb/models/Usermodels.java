package com.web.pi3s.SpringWeb.models;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * 
 * Uma entidade de usuário que serve
 */
@Entity
@Table(name = "TB_USER")
public class Usermodels implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private UUID userId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String grupo;
    @Column(nullable = false, unique = true)
    private String cpf;
    @Column(nullable = false)
    private boolean statusAtivo = true;

    @Transient
    private String password2;

        //SERÁ RELACIONADO AQUI OS UUID DE USER COM OS UUIDS DAS ROLES, JA ESTOU DEFININDO ID DE CONEÇÃO ENTRE AS TABELAS
        //AQUI PODEMOS DETERINAR QUE CADA USUARIO PODDERÁ ASSUMIR DIFERENTES PAPEIS 
    @ManyToMany
    @JoinTable(name = "TB_USER_ROLES",joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "role_id"))
                    private List<Rolesmodels> roles;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    // public long getCpf() {
    //     return cpf;
    // }

    // public void setCpf(long cpf) {
    //     this.cpf = cpf;
    // }

    public boolean isStatusAtivo() {
        return statusAtivo;
    }

    public void setStatusAtivo(boolean statusAtivo) {
        //statusAtivo =  false;
        this.statusAtivo = statusAtivo;
    }

    public List<Rolesmodels> getRoles() {
        return roles;
    }

    public void setRoles(List<Rolesmodels> roles) {
        this.roles = roles;
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

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return "Usermodels [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
                + ", grupo=" + grupo + ", cpf=" + cpf + ", statusAtivo=" + statusAtivo + ", roles=" + roles + "]";
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    

    public Usermodels() {
    }

    public Usermodels(String username, String email, String grupo, boolean statusAtivo, UUID userId) {
        this.username = username;
        this.email = email;
        this.grupo = grupo;
        this.statusAtivo = statusAtivo;
        this.userId = userId;
    }

    public Usermodels(UUID userId, String username, String password, String email, String grupo, String cpf,
            boolean statusAtivo) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.grupo = grupo;
        this.cpf = cpf;
        this.statusAtivo = statusAtivo;
       
    }
}
