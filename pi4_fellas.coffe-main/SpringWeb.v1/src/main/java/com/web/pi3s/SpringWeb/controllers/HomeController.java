package com.web.pi3s.SpringWeb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.web.pi3s.SpringWeb.models.Rolesmodels;
import com.web.pi3s.SpringWeb.models.Usermodels;
import com.web.pi3s.SpringWeb.repositorio.Userrespo;



 @Controller
 public class HomeController {


    //private final PasswordEncoder encoder;


    @Autowired
    Userrespo repository;

    @RequestMapping("/")
    public String index(Model model){

		model.addAttribute("Seja ", "del");
		return "/home/index";
	}
  
    @PreAuthorize("permitAll()")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/logar")
    
    public String login(Model model){

        //Usermodels user = (Usermodels) this.repository.findByRoles(roles);
        //System.out.println("ROLES:  " + user);


		model.addAttribute(model);
		return "despensa/menuDespensa";
	}

    @GetMapping("/CadastroCliente")
    public ModelAndView formCliente() {
        ModelAndView modelAndView = new ModelAndView();
        Usermodels usuario = new Usermodels();
        //Grupo grupo = new Grupo();
        modelAndView.setViewName("cadastro/cadastro");
        modelAndView.addObject("usuario", usuario);
        //modelAndView.addObject("grupo", grupo);

        return modelAndView;

    }

    // @RequestMapping(value = "/CadastroCliente/salvo", method = RequestMethod.POST)
    // public String salvar(Usermodels c) {
    //     c.setPassword(encoder.encode(c.getPassword()));
    //     repository.save(c); // Função que executa o cadastro
    //     return "/home/index";
    // }


 }
 


