package com.web.pi3s.SpringWeb.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.web.pi3s.SpringWeb.models.Usermodels;
import com.web.pi3s.SpringWeb.repositorio.Userrespo;

@RestController
@RequestMapping("/api/usuario")
public class UserController {

    UserController userController;

    @Autowired
    PasswordEncoder enconder;
    @Autowired
    Userrespo repository;

    public void apagarUsuarioPorId(UUID id) {

    }

    @PostMapping("/login")
    public String login() {
        return "index";
    }

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        Usermodels usuario = new Usermodels();
        modelAndView.setViewName("/home/index/");
        modelAndView.addObject("usuario", usuario);

        return modelAndView;
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

    @GetMapping("/admin/apagar/{id}")
    public String deleteUser(@PathVariable("id") UUID id, Usermodels model) {
        userController.apagarUsuarioPorId(id);
        return "redirect:/usuario/admin/listar";
    }

}
