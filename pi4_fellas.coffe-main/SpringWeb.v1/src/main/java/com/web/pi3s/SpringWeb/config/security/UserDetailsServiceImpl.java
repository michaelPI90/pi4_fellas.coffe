package com.web.pi3s.SpringWeb.config.security;


import javax.management.relation.Role;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.pi3s.SpringWeb.models.Rolesmodels;
import com.web.pi3s.SpringWeb.models.Usermodels;
import com.web.pi3s.SpringWeb.repositorio.Userrespo;

import jakarta.transaction.Transactional;

@Service
//QUANDO UMA INFROMAÇÃO FOR BUSCADA NO BANCO DE DADOS ENQUANTO OCRRER ESSA TRANSIÇÃO QUE DARA ACESSO AS ROLES
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    final Userrespo userrespo;

    public UserDetailsServiceImpl(Userrespo userrespo) {
        this.userrespo = userrespo;
    }


/**
 *  AQUI VAMOS UTILIZAR O RETORNO USER DO SPRING SECURITY ASSIM UTILIZAMOS UM CONSTRUTOR DO PROPRIO 
    SPRING SECURITY 
 * 
 */

   
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usermodels usermodels = userrespo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Cliente " + email + " não encontrado"));
       
       return new User(usermodels.getEmail(), usermodels.getPassword(),
       true, 
       true,
        true, 
        true, usermodels.getAuthorities());
    
    }

}
