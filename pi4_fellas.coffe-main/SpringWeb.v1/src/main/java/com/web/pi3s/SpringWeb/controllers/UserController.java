package com.web.pi3s.SpringWeb.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.web.pi3s.SpringWeb.models.Compra;
import com.web.pi3s.SpringWeb.models.Produtomodels;
import com.web.pi3s.SpringWeb.models.Usermodels;
import com.web.pi3s.SpringWeb.repositorio.Comprasrespo;
import com.web.pi3s.SpringWeb.repositorio.Produtorespo;
import com.web.pi3s.SpringWeb.repositorio.Userrespo;

@Controller
@RequestMapping("/api/usuario")
public class UserController {

    @Autowired
    Comprasrespo comprasrespo;

    UserController userController;

    @Autowired
    PasswordEncoder enconder;
    @Autowired
    Userrespo repository;
    Produtorespo repoProd;



    @PostMapping("/logar")
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
        return "redirect:/home/index";
    }

    @GetMapping("/logar")
String LoginUser(Model model) {
    model.addAttribute("user", new Usermodels());
    return "nomeDoSeuTemplate"; // substitua "nomeDoSeuTemplate" pelo nome do seu template para exibir o formulário de login
}


    @GetMapping("/admin/listar")
    String listar(Model model, Usermodels user) {

        model.addAttribute("usuarios", repository.findAll());
        return "/alterar/alterar";

    }

    @GetMapping("/home/logar")
    public String exibirFormularioLogin(Model model) {
        model.addAttribute("user", new Usermodels());
        return "/logado/logadoEstoquista";
    }
    



    @GetMapping("/admin/home")
public String exibirListaUsuarios(Model model) {
    model.addAttribute("usuarios", repository.findAll());
    return "/logado/logadoAdmin";
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


    @GetMapping("/listaTodasCompras")
    public ModelAndView listaTodasCompras() {
        ModelAndView modelAndView = new ModelAndView();
    
        // Buscar todas as compras feitas na loja, ordenadas por data de forma decrescente
       

       

        List<Compra> compras = (List<Compra>) comprasrespo.findAll();
        // Adicionar a lista de compras ao modelo
        modelAndView.addObject("compra", compras);
        modelAndView.setViewName("pedidos/statusPedidosBackOffice");
        return modelAndView;
    }
    






}
