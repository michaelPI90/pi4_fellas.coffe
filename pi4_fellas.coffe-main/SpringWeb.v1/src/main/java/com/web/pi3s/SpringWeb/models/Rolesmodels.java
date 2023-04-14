package com.web.pi3s.SpringWeb.models;


import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;


import com.web.pi3s.SpringWeb.Enum.RoleName;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_ROLE")
public class Rolesmodels implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private UUID roleId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName roleName;
 @ManyToMany
    private List<Usermodels> usuarios;


    //TIPO DE CONTROLE DE ACESSO DO SECURITY AQUI SERÁ DEFINDDO EM MODELS O MAPEMAENTI DO RELACUINAMENTO
    @Override
    public String getAuthority() {
        return this.roleName.toString();

    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }


    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

}
