package com.web.pi3s.SpringWeb.controllers;

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
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


    @GetMapping("/admin/listar")
    String listarUsuario(Model model, Usermodels user) {
        Optional<Usermodels> userStatus = this.repository.findByEmail(user.getEmail());

        if (!enconder.matches(user.getPassword(), userStatus.get().getPassword())) {
            model.addAttribute("erro", "Dados inválidos");
            return "/home/index";
        }
        else if (!userStatus.get().isStatusAtivo()) {
            model.addAttribute("erro", "Usuário inativo");
            return "/home/index";
        }

        model.addAttribute("usuarios", repository.findAll());
        return "/alterar/alterar";
    }

    @GetMapping("/ConsultarUsuario") // @PostMapping("/ConsultarUsuario")
    public String consultar(Model model, @RequestParam String nome) {

        Optional<Usermodels> username = this.repository.findByUsername(nome);

        if (username.isPresent()) {
            model.addAttribute("usuarios", Arrays.asList(username.get()));
            return "/alterar/alterar";
        }

        model.addAttribute("erro", "Usuário não encontrado!");
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
