package com.web.pi3s.SpringWeb.controllers;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.web.pi3s.SpringWeb.models.Usermodels;
import com.web.pi3s.SpringWeb.repositorio.Userrespo;


@RestController
@RequestMapping("/api/usuario")
public class UserController {
  
  

    private final PasswordEncoder enconder;
    private final Userrespo repository;


    public UserController(Userrespo repository, PasswordEncoder enconder) {
        this.repository = repository;
        this.enconder = new BCryptPasswordEncoder();
    }



    @GetMapping("/")
	public String index(){
		return "/home/index";
	}


    @GetMapping("/listarTodos")
    public ResponseEntity<List<Usermodels>> listarTodos() {

        return ResponseEntity.ok(repository.findAll());

    }


    @PostMapping("/salvar")
    public ResponseEntity<Usermodels>  salvar(@RequestBody Usermodels usuario) {

        usuario.setPassword(enconder.encode(usuario.getPassword()));
        return ResponseEntity.ok(repository.save(usuario));

}


// @PutMapping(value ="/{@id}")
// public ResponseEntity<Usermodels>  atualizar(){

// }




}
