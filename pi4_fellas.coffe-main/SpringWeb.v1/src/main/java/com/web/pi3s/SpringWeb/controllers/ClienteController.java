package com.web.pi3s.SpringWeb.controllers;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.web.pi3s.SpringWeb.models.Clientemodels;
import com.web.pi3s.SpringWeb.models.ItenCompraProduto;
import com.web.pi3s.SpringWeb.models.Produtomodels;
import com.web.pi3s.SpringWeb.repositorio.Clienterespo;
import com.web.pi3s.SpringWeb.repositorio.ItenComprarespo;
import com.web.pi3s.SpringWeb.repositorio.Produtorespo;
import com.web.pi3s.SpringWeb.repositorio.Userrespo;

import jakarta.servlet.http.HttpSession;

@Controller

@RequestMapping("/fellas.coffe")

public class ClienteController {

  @Autowired
  Clienterespo clienterespo;
  @Autowired
  PasswordEncoder enconder;

  @Autowired
  ItenComprarespo itenComprarespo;
  @Autowired
  Produtorespo produtorespo;
  private List<ItenCompraProduto> intemCompra = new ArrayList<ItenCompraProduto>();

  @GetMapping("/homeCliente")
  public ModelAndView listaProduto(ModelAndView modelAndView) {
    List<Produtomodels> produtosOrdenados = this.produtorespo.ordernar();
    // Produtomodels produtos = new Produtomodels();
    // Collections.sort(produtosOrdenados, Collectionds.reverseOrder());
    modelAndView.setViewName("cliente/home");
    // modelAndView.addObject("produtos", Collections.sort(produtosOrdenados,
    // Collections.reverseOrder()));
    modelAndView.addObject("produtos", produtosOrdenados);

    return modelAndView;

  }

  @GetMapping("/logado")
  public ModelAndView logado() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("cliente/homeLogado");
    mv.addObject("userLogado", new Clientemodels());

    return mv;

  }

  @GetMapping("/alterar")
  public ModelAndView viwAlterar() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("cliente/alterarDadosCliente");
    mv.addObject("usuarioLogado", new Clientemodels());

    return mv;

  }

  @GetMapping("/alterarCadastro")
  public String exibirFormulario(Model model, HttpSession session, Clientemodels user) {
    Clientemodels usuarioLogado = (Clientemodels) session.getAttribute("usuarioLogado");

    // Verifica se o usuário está logado na sessão
    if (usuarioLogado == null) {
      // Redireciona para a página de login caso não esteja logado
      return "redirect:/fellas.coffe/clienteLogin";
    }

    // Adicione o usuário logado ao modelo para exibir os dados no formulário
    session.setAttribute("usuario", usuarioLogado);
    model.addAttribute("usuario", usuarioLogado);

    // Retorne o nome da página de formulário para que seja exibido o conteúdo
    return "cliente/alterarDadosCliente";
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

  // @GetMapping("/detalhesProduto")
  // public ModelAndView detalhesProduto() {

  // ModelAndView modelAndView = new ModelAndView();
  // Produtomodels detalhesProduto = new Produtomodels();

  // modelAndView.setViewName("cliente/detalhesProduto");
  // modelAndView.addObject("detalhesProduto", detalhesProduto);

  // return modelAndView;

  // }

  @GetMapping("/detalhesProduto/{id}")
  public ModelAndView exibirDetalhesProduto(@PathVariable Long id) {
    // Obtenha o produto do banco de dados com base no ID
    ModelAndView model = new ModelAndView();
    Produtomodels produto = produtorespo.findByid(id);

    // Adicione o produto ao modelo
    model.addObject("produto", produto);
    model.setViewName(("produtos/detalhesProduto"));
    // Retorne o nome da página HTML (sem a extensão .html)
    return model;
  }

  @PostMapping("clienteLogar")
  ModelAndView listarUsuario(Model model, Clientemodels user, HttpSession session) {

    ModelAndView mv = new ModelAndView();
    mv.addObject("usuario", new Clientemodels());
    String erroMsg = null;

    Clientemodels userEncontrado = this.clienterespo.findByEmail(user.getEmail());
    if (userEncontrado == null) {
      erroMsg = "Email não encontrado clique em cadastra-se caso não tenha cadastro";

    } else if (!userEncontrado.isStatusAtivo()) {
      erroMsg = "Usuário inativo!!!";
    }

    else {
      session.setAttribute("usuarioLogado", userEncontrado);
      return logado();
    }
    model.addAttribute("erro", erroMsg);
    mv.setViewName("/cliente/loginCliente");
    return mv;
  }

  @GetMapping("clienteLogar")
  public ModelAndView exibirFormularioLogin(Model model) {
    ModelAndView mv = new ModelAndView();
    mv.addObject("usuario", new Clientemodels());
    mv.setViewName("/cliente/homeLogado");
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
      return "cliente/loginCliente";
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

  @PostMapping("/salvarAltercaoCadastro")
  public String alterarCadastro(Model model, HttpSession session, Clientemodels novoUsuario) {
    Clientemodels usuarioLogado = (Clientemodels) session.getAttribute("usuarioLogado");

    // Verifica se o usuário está logado na sessão
    if (usuarioLogado == null) {
      // Redireciona para a página de login caso não esteja logado
      return "redirect:/fellas.coffe/clienteLogin";
    }

    // Atualiza os dados do usuário logado com os novos dados

    usuarioLogado.setCpf(novoUsuario.getCpf());
    // Outros campos que precisam ser atualizados

    // Salva as alterações no repositório
    clienterespo.save(usuarioLogado);

    // Atualiza o usuário na sessão
    session.setAttribute("usuarioLogado", usuarioLogado);

    // Redireciona para a página de perfil do usuário
    return "redirect:/fellas.coffe/logado";
  }

  @PostMapping("/alterarcliente")
  public ResponseEntity<String> alterarDadosUsuario(@RequestBody Clientemodels usuario) {
    // Encontre o usuário correspondente no banco de dados com base no ID ou algum
    // identificador único
    // e atualize os campos relevantes com os valores fornecidos no objeto "usuario"
    // Exemplo:
    Clientemodels usuarioExistente = clienterespo.findById(usuario.getId()).orElse(usuario);
    if (usuarioExistente != null) {
      usuarioExistente.setNome(usuario.getNome());
      usuarioExistente.setEmail(usuario.getEmail());
      usuarioExistente.setStatusAtivo(usuario.isStatusAtivo());
      clienterespo.save(usuarioExistente);
      return ResponseEntity.ok("Usuário atualizado com sucesso!");
    } else {
      return ResponseEntity.badRequest().body("Usuário não encontrado!");
    }

  }

  @GetMapping("/resumoDoPedido")
  public String exibirResumoDoPedido(Model model, HttpSession session) {
    // Verificar se o cliente está logado na sessão
    Clientemodels clienteLogado = (Clientemodels) session.getAttribute("usuarioLogado");
    if (clienteLogado == null) {
      // Cliente não está logado, redirecionar para a página de login
      return "redirect:/clienteLogin";
    }

    // Aqui você pode recuperar os dados do banco de dados e definir o modelo
    // Buscar a lista de pedidos do cliente logado
    List<ItenCompraProduto> pedidos = itenComprarespo.findByid(clienteLogado.getId());
    model.addAttribute("pedidos", pedidos);

    // Dados da entrega
    List <String> endereco = clienteLogado.getEnderecosEntrega();
    model.addAttribute("endereco", endereco);

    // Dados do pagamento

    return "/cliente/resumoDoPedido";
  }

  @PostMapping("/adicionarEnderecoEntrega")
public ModelAndView adicionarEnderecoEntrega(HttpSession session, @RequestParam("enderecoEntrega") String enderecoEntrega) {
    ModelAndView modelAndView = new ModelAndView();
    
    Clientemodels usuarioLogado = (Clientemodels) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        modelAndView.setViewName("redirect:/fellas.coffe/clienteLogin");
        return modelAndView;
    }

    // Adicione o novo endereço à lista de endereços de entrega do usuário
    List<String> enderecosEntrega = usuarioLogado.getEnderecosEntrega();
    enderecosEntrega.add(enderecoEntrega);
    usuarioLogado.setEnderecosEntrega(enderecosEntrega);

    // Atualize a sessão com o usuário atualizado
    session.setAttribute("usuarioLogado", usuarioLogado);

    modelAndView.setViewName("cliente/resumoDoPedido");
    return modelAndView;
}


  @PostMapping("/Logout")
  public ModelAndView logout(HttpSession session) {
    // Recupera o carrinho do cliente da sessão
    Clientemodels clientemodels = (Clientemodels) session.getAttribute("usuarioLogado");

    if (clientemodels != null) {
      // Zera o carrinho

      intemCompra.removeAll(intemCompra);
      

    }

    session.invalidate();

    return formCliente();
  }

  // @PostMapping("/Logout")
  // public ModelAndView logout(HttpSession session) {
  // session.invalidate();

  // return formCliente();
  // }

}
