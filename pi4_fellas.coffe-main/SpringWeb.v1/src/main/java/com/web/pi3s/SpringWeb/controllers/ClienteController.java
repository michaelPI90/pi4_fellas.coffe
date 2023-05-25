package com.web.pi3s.SpringWeb.controllers;

import java.util.InputMismatchException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.web.pi3s.SpringWeb.models.Clientemodels;
import com.web.pi3s.SpringWeb.models.Produtomodels;
import com.web.pi3s.SpringWeb.repositorio.Clienterespo;
import com.web.pi3s.SpringWeb.repositorio.Userrespo;

import jakarta.servlet.http.HttpSession;

@Controller

@RequestMapping("/fellas.coffe")

public class ClienteController {

  @Autowired
  Clienterespo clienterespo;
  @Autowired
  PasswordEncoder enconder;

  @GetMapping("/homeCliente")
  public String index() {
    return "/cliente/home";
  }

  @GetMapping("/logado")
  public ModelAndView logado() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("cliente/homeLogado");
    mv.addObject("usuarioLogado", new Clientemodels());

    return mv;

  }

  @GetMapping("/clienteLogin")
  public ModelAndView formCliente() {
    ModelAndView modelAndView = new ModelAndView();
    Clientemodels usuario = new Clientemodels();
    modelAndView.setViewName("cliente/loginCliente");
    modelAndView.addObject("usuario", usuario);

    return modelAndView;

  }

  @GetMapping("/clienteCadastro")
  public ModelAndView cadastroCliente(Model model) {
    ModelAndView modelAndView = new ModelAndView();
    Clientemodels usuario = new Clientemodels();
    modelAndView.setViewName("cliente/cadastroCliente");
    modelAndView.addObject("usuario", usuario);

    return modelAndView;

  }

  @GetMapping("/detalhesProduto")
  public ModelAndView detalhesProduto(Model model) {

    ModelAndView modelAndView = new ModelAndView();
    Produtomodels detalhesProduto = new Produtomodels();

    modelAndView.setViewName("cliente/detalhesProduto");
    modelAndView.addObject("detalhesProduto", detalhesProduto);

    return modelAndView;

  }

  @PostMapping("clienteLogar")
  ModelAndView listarUsuario(Model model, Clientemodels user, HttpSession session) {
    Clientemodels userEncontrado = this.clienterespo.findByEmail(user.getEmail());
    ModelAndView mv = new ModelAndView();
    String erroMsg = null;
    if (userEncontrado == null) {
      erroMsg = "Email não encontrado clique em cadastra-se caso não tenha cadastro";

    } else if (!enconder.matches(user.getPassword(), userEncontrado.getPassword())) {
      erroMsg = "verifique se a senha foi digitada corretamente";

    } else if (!userEncontrado.isStatusAtivo()) {
      erroMsg = "Usuário inativo!!!";
    }

    else {
      session.setAttribute("usuarioLogado", user);
      System.out.println(user);
      session.setMaxInactiveInterval(1);

      return logado();
    }
    model.addAttribute("erro", erroMsg);
    mv.setViewName("/cliente/loginCliente");
    return mv;
  }

  @PostMapping("/clienteCadastro")
  public String salvar(Model model, Clientemodels c) throws Exception {
    String erroMsg = null;
    if (this.clienterespo.findByEmail(c.getEmail()) != null) {
      erroMsg = "EMAIL JÁ CADASTRADO";
    } else if (this.clienterespo.findByCpf(c.getCpf()).isPresent()) {
      erroMsg = "CPF JÁ CADASTRADO";
    } else if (validadorCPF(c.getCpf()) == false) {
      erroMsg = "CPF INVÁLIDO";
    } else if (!c.getPassword().equals(c.getPassword2())) {
      erroMsg = "SENHAS INFORMADAS NÃO ESTÃO IGUAIS";
    } else {
      c.setStatusAtivo(true);
      c.setPassword(enconder.encode(c.getPassword()));
      clienterespo.save(c); // Função que executa o cadastro
      return "cliente/homeLogado";
    }
    model.addAttribute("erro", erroMsg);
    return "/cliente/cadastroCliente";

  }

  public static boolean validadorCPF(String CPF) {

    if (CPF == null)
      return false;

    // considera-se erro CPF's formados por uma sequencia de numeros iguais
    if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222") ||
        CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
        || CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
        || CPF.equals("99999999999") || (CPF.length() != 11)) {
      return (false);
    }
    char dig10,
        dig11;
    int sm, i, r, num, peso;
    // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
    try {
      // Calculo do 1o. Digito Verificador
      sm = 0;
      peso = 10;
      for (i = 0; i < 9; i++) {
        // converte o i-esimo caractere do CPF em um numero:
        // por exemplo, transforma o caractere '0' no inteiro 0
        // (48 eh a posicao de '0' na tabela ASCII)
        num = (int) (CPF.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso - 1;
      }
      r = 11 - (sm % 11);
      if ((r == 10) || (r == 11))
        dig10 = '0';
      else
        dig10 = (char) (r + 48);
      // converte no respectivo caractere numerico
      // Calculo do 2o. Digito Verificador
      sm = 0;
      peso = 11;
      for (i = 0; i < 10; i++) {
        num = (int) (CPF.charAt(i) - 48);
        sm = sm + (num * peso);
        peso = peso - 1;
      }
      r = 11 - (sm % 11);
      if ((r == 10) || (r == 11))
        dig11 = '0';
      else
        dig11 = (char) (r + 48);
      // Verifica se os digitos calculados conferem com os digitos informados.
      if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
        return (true);
      } else {
        return (false);
      }
    } catch (InputMismatchException erro) {
      return (false);
    }
  }

  @PostMapping("/Logout")
  public ModelAndView logout(HttpSession session) {
    session.invalidate();

    return formCliente();
  }

}
