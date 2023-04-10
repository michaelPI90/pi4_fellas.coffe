package com.web.pi3s.SpringWeb.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.web.pi3s.SpringWeb.models.Usermodels;
import com.web.pi3s.SpringWeb.repositorio.Userrespo;

@Controller
@RequestMapping("/api/usuario")
public class UserController {

    UserController userController;

    @Autowired
    PasswordEncoder enconder;
    @Autowired
    Userrespo repository;

    public void apagarUsuarioPorId(UUID id) {

    }

    // @GetMapping("/CadastroCliente")
    // public ModelAndView formCliente() {
    //     ModelAndView modelAndView = new ModelAndView();
    //     Usermodels usuario = new Usermodels();
    //     //Grupo grupo = new Grupo();
    //     modelAndView.setViewName("cadastro/cadastro");
    //     modelAndView.addObject("usuario", usuario);
    //     //modelAndView.addObject("grupo", grupo);

    //     return modelAndView;

    // }


   
    @GetMapping("/admin/listar")
	 String listarUsuario(Model model, Usermodels user) {
        Optional<Usermodels> userStatus = this.repository.findByEmail(user.getEmail());
        System.out.println("usuário trago no find: " + userStatus.get().isStatusAtivo());
        if(userStatus.get().isStatusAtivo() == false){
        model.addAttribute("erro", "Usuário inativo ou não cadastrado");
        return "/home/index";
        }
        
		model.addAttribute("usuarios", repository.findAll());		
		return "/alterar/alterar";		
	}


    
    @GetMapping("/listarTodos")
    public ResponseEntity<List<Usermodels>> listarTodos() {

        return ResponseEntity.ok(repository.findAll());

    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/salvar")
    public ResponseEntity<Usermodels> salvar(@RequestBody Usermodels usuario) {

        usuario.setPassword(enconder.encode(usuario.getPassword()));
        return ResponseEntity.ok(repository.save(usuario));

    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/apagar/{id}")
    public String deleteUser(@PathVariable("id") UUID id, Usermodels model) {
        userController.apagarUsuarioPorId(id);
        return "redirect:/usuario/admin/listar";
    }

}
