package com.web.pi3s.SpringWeb.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.web.pi3s.SpringWeb.models.Produtomodels;
import com.web.pi3s.SpringWeb.models.Usermodels;
import com.web.pi3s.SpringWeb.repositorio.Produtorespo;
import com.web.pi3s.SpringWeb.repositorio.Userrespo;

import jakarta.annotation.Generated;

@Controller
@RequestMapping("/api/usuario")
public class UserController {

    UserController userController;

    @Autowired
    PasswordEncoder enconder;
    @Autowired
    Userrespo repository;
    Produtorespo repoProd;

    public void apagarUsuarioPorId(UUID id) {

    }

    @PostMapping("/home/logar")
    String listarUsuario(Model model, Usermodels user) {
        Optional<Usermodels> userEncontrado = this.repository.findByEmail(user.getEmail());

        String erroMsg = null;
        if (userEncontrado.isEmpty()) {
            erroMsg = "Email não encontrado!!!";
        } else if (!enconder.matches(user.getPassword(), userEncontrado.get().getPassword())) {
            erroMsg = "Senha incorreta!!!";
        } else if (!userEncontrado.get().isStatusAtivo()) {
            erroMsg = "Usuário inativo!!!";
        } else if (userEncontrado.get().getGrupo().equals("ROLE_CLIENTE")) {
            erroMsg = "Você não é usuário do BackOffice!!!";
        } else if (userEncontrado.get().getGrupo().equals("ROLE_ESTOQUISTA")) {
            return "/logado/logadoEstoquista";

        } else {
            model.addAttribute("usuarios", repository.findAll());
            return "/logado/logadoAdmin";
        }
        model.addAttribute("erro", erroMsg);
        return "/home/index";
    }

    @GetMapping("/admin/listar")
    String listar(Model model, Usermodels user) {

        model.addAttribute("usuarios", repository.findAll());
        return "/alterar/alterar";

    }

    @GetMapping("/ConsultarUsuario")
    public String consultar(Model model, @RequestParam String nome) {

        Optional<Usermodels> username = this.repository.findByUsername(nome);

        if (username.isPresent()) {
            model.addAttribute("usuarios", Arrays.asList(username.get()));
            return "/alterar/alterar";
        }

        model.addAttribute("erro", "Usuário não encontrado!");
        return "/alterar/alterar";
    }

    // @GetMapping("/alterarStatus/{userId}")
    // public String atualizar(Model model, @PathVariable UUID userId, Usermodels user) {

    //     salvar(userId, user);

    //     return "redirect:/api/usuario/admin/listar";
    // }

    // @GetMapping("/listarTodos")
    // public ResponseEntity<List<Usermodels>> listarTodos() {

    // return ResponseEntity.ok(repository.findAll());

    // }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @PostMapping("/salvar")
    // public ModelAndView salvar(UUID userID, Usermodels user) {
    //     ModelAndView modelAndView = new ModelAndView();
    //     Usermodels u = repository.findByUserId(user.getUserId());

    //     if (u.isStatusAtivo()) {
    //         u.setStatusAtivo(false);

    //     } else {
    //         u.setStatusAtivo(true);
    //     }
    //     repository.save(u);

    //     modelAndView.setViewName("alterar/alterar");
    //     return modelAndView;

    // }

    @PostMapping("/alterarDadosUsuario")
    public ResponseEntity<String> atualizarDados(@RequestBody Usermodels user) {
        ResponseEntity resp;
        try{
            repository.alterarUsuario(user.getUsername(), user.getEmail(), user.getGrupo(), user.isStatusAtivo(), user.getUserId());
            resp = ResponseEntity.ok("Usuário alterado com sucesso!");
        }catch(Exception e){
            e.printStackTrace();
            resp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível alterar o usuário: " + e.getMessage());
        }
        return resp;
    }

    // @PostMapping("/salvar")
    public ModelAndView salvarDados(Usermodels user) {
        ModelAndView modelAndView = new ModelAndView();

        repository.save(user);

        modelAndView.setViewName("alterar/alterar");
        return modelAndView;

    }

    @GetMapping("/ListarProdutos")
    public ModelAndView listaProduto(ModelAndView modelAndView) {
        List<Produtomodels> produtosOrdenados = this.repoProd.ordernar();
        // Produtomodels produtos = new Produtomodels();
        // Collections.sort(produtosOrdenados, Collections.reverseOrder());
        modelAndView.setViewName("produtos/produtos");
        // modelAndView.addObject("produtos", Collections.sort(produtosOrdenados,
        // Collections.reverseOrder()));
        modelAndView.addObject("produtos", produtosOrdenados);

        return modelAndView;

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/apagar/{id}")
    public String deleteUser(@PathVariable("id") UUID id, Usermodels model) {
        userController.apagarUsuarioPorId(id);
        return "redirect:/usuario/admin/listar";
    }

}
