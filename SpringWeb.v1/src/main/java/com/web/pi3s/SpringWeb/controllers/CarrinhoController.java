package com.web.pi3s.SpringWeb.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.web.pi3s.SpringWeb.Enum.StatusPedido;
import com.web.pi3s.SpringWeb.models.Clientemodels;
import com.web.pi3s.SpringWeb.models.Compra;
import com.web.pi3s.SpringWeb.models.ItenCompraProduto;
import com.web.pi3s.SpringWeb.models.Produtomodels;
import com.web.pi3s.SpringWeb.repositorio.Clienterespo;
import com.web.pi3s.SpringWeb.repositorio.Comprasrespo;
import com.web.pi3s.SpringWeb.repositorio.ItenComprarespo;
import com.web.pi3s.SpringWeb.repositorio.Produtorespo;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class CarrinhoController {

    @Autowired
    Produtorespo produtorespo;
    @Autowired
    Clienterespo clienterespo;
    Clientemodels cliente;
    @Autowired
    Comprasrespo comprasrespo;
    @Autowired
    ItenComprarespo itenComprarespo;
    
    private List<ItenCompraProduto> intemCompra = new ArrayList<ItenCompraProduto>();
    public Compra compra = new Compra();

    public void calcularTotal() {
        compra.setValorTotal(0.);
        for (ItenCompraProduto it : intemCompra) {
             compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
            System.out.println(compra);
        }
        if (compra.getValorTotal() == 0.0) {
            compra.setValorTotal(compra.getValorTotal() + 0);
        }

       compra.setValorTotal(compra.getValorTotal() + compra.getFrete());
      
    }

    @GetMapping("/carrinho")
    public ModelAndView comprar() {
        ModelAndView mv = new ModelAndView("cliente/carrinho");

        calcularTotal();
        mv.addObject("compra", compra);
        mv.addObject("listaItens", intemCompra);
        return mv;

    }

    @GetMapping("/calculoFrete")
    public ModelAndView frete(@RequestParam("frete") String frete) {
        Double freteConvertido = Double.parseDouble(frete);
        compra.setFrete(freteConvertido);

        return new ModelAndView("redirect:/carrinho");
    }

    @GetMapping("/adcionarProduto/{id}")
    public String mostrarCarrinho(@PathVariable("id") Long id) {

        System.out.println("ID" + id);

        Optional<Produtomodels> prod = produtorespo.findById(id);
        Produtomodels produto = prod.get();

        boolean produtoExistente = false;

        for (ItenCompraProduto it : intemCompra) {
            if (it.getProduto().getId().equals(produto.getId())) {
                it.setQuantidade(it.getQuantidade() + 1);
                it.setValorTotal(it.getValorTotal() + it.getValorUnitario());
                produtoExistente = true;
                break;
            }
        }

        if (!produtoExistente) {
            ItenCompraProduto item = new ItenCompraProduto();
            item.setProduto(produto);
            item.setValorUnitario(produto.getPrecoProduto());
            item.setQuantidade(1);
            item.setValorTotal(item.getValorUnitario());

            intemCompra.add(item);
        }

        return "redirect:/carrinho";
    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {

        for (ItenCompraProduto it : intemCompra) {
            if (it.getProduto().getId().equals(id)) {
                if (acao == 1) {
                    it.setQuantidade(it.getQuantidade() + 1);
                    it.setValorTotal(0.);

                    it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                } else if (acao == 0) {
                    if (it.getQuantidade() <= 0) {
                        break;
                    }
                    it.setQuantidade(it.getQuantidade() - 1);
                    it.setValorTotal(0.);
                    it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                }

                break;
            }
        }

        return "redirect:/carrinho";
    }


    @GetMapping("/alterarQuantidadeCheckout/{id}/{acao}")
    public String alterarQuantidadeChekout(@PathVariable Long id, @PathVariable Integer acao) {

        for (ItenCompraProduto it : intemCompra) {
            if (it.getProduto().getId().equals(id)) {
                if (acao == 1) {
                    it.setQuantidade(it.getQuantidade() + 1);
                    it.setValorTotal(0.);

                    it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                } else if (acao == 0) {
                    if (it.getQuantidade() <= 0) {
                        break;
                    }
                    it.setQuantidade(it.getQuantidade() - 1);
                    it.setValorTotal(0.);
                    it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
                }

                break;
            }
        }

        return "redirect:/resumoDaCompra";
    }






    @GetMapping("/removerItem/{id}")
    public String removerItem(@PathVariable Long id) {

        // Encontre o item a ser removido com base no ID
        ItenCompraProduto itemRemover = null;
        for (ItenCompraProduto it : intemCompra) {
            if (it.getProduto().getId().equals(id)) {
                itemRemover = it;
                break;
            }
        }

        // Remova o item da lista
        if (itemRemover != null) {
            intemCompra.remove(itemRemover);
        }

        return "redirect:/carrinho";
    }

    @PostMapping("/checkout")
    public ModelAndView buscarUserlogado(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        Clientemodels usuarioLogado = (Clientemodels) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            modelAndView.setViewName("redirect:/fellas.coffe/clienteLogin");
            return modelAndView;
        }
        if (intemCompra.size() == 0){
            modelAndView.setViewName("redirect:/carrinho");
            
            return modelAndView;
        }

        // Lógica para finalizar a compra
        calcularTotal();
        compra.setCliente(usuarioLogado);
        modelAndView.addObject("compra", compra);

        modelAndView.addObject("listarItens", intemCompra);
        modelAndView.addObject("user", usuarioLogado);
        modelAndView.setViewName("cliente/resumoDoPedido");
        return modelAndView;
    }

    @GetMapping("/checkout")
public ModelAndView buscarUserLogado(HttpSession session) {
    ModelAndView modelAndView = new ModelAndView();

    Clientemodels usuarioLogado = (Clientemodels) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        modelAndView.setViewName("redirect:/fellas.coffe/clienteLogin");
        return modelAndView;
    }
    if (intemCompra.size() == 0){
        modelAndView.setViewName("redirect:/carrinho");
        return modelAndView;
    }

    // Lógica para finalizar a compra
    calcularTotal();
    compra.setCliente(usuarioLogado);
    modelAndView.addObject("compra", compra);

    modelAndView.addObject("listarItens", intemCompra);
    modelAndView.addObject("user", usuarioLogado);
    modelAndView.setViewName("cliente/resumoDoPedido");
    return modelAndView;
}



    @PostMapping("adicionarEnderecoEntrega")
    public ModelAndView adicionarEnderecoEntrega(HttpSession session,  @RequestParam("enderecoEntrega") String enderecoEntrega) {
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
        modelAndView.addObject("enderecoEntrega", enderecosEntrega);
        session.setAttribute("usuarioLogado", usuarioLogado);

     
        return buscarUserlogado(session);
    }

    @PostMapping("/enderecoSelecionado")
    public ModelAndView salvarEndereco(@RequestParam("flexRadioDefault") List<String> enderecoEscolhido, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Clientemodels usuarioLogado = (Clientemodels) session.getAttribute("usuarioLogado");
        session.setAttribute("usuarioLogado", usuarioLogado);


        
        List<String> endereco = usuarioLogado.getEnderecosEntrega();
        usuarioLogado.setEnderecosEntrega(enderecoEscolhido);
        mv.addObject("endereco", endereco);
        mv.addObject("formaDePagamnto", compra);
        
        return pagamento(session);

    }


    @GetMapping("/enderecoSelecionado")
public ModelAndView exibirEnderecoSelecionado(HttpSession session) {
    ModelAndView mv = new ModelAndView();
    Clientemodels usuarioLogado = (Clientemodels) session.getAttribute("usuarioLogado");

    List<String> enderecoSelecionado = usuarioLogado.getEnderecosEntrega();
    mv.addObject("enderecoSelecionado", enderecoSelecionado);
    
    // Adicione aqui qualquer outra informação adicional necessária para a página
    
  // Substitua "nome_da_sua_pagina" pelo nome correto da sua página
    
    return pagamento(session);
}




    @GetMapping("/formaDePagamento")
    public ModelAndView pagamento(HttpSession session) {
        ModelAndView mv = new ModelAndView("cliente/pagamento");
        Clientemodels usuarioLogado = (Clientemodels) session.getAttribute("usuarioLogado");

        session.setAttribute("usuarioLogado", usuarioLogado);

        mv.addObject("formaDePagamnto", compra);
        mv.setViewName("cliente/pagamento");
        return mv;

    }



    @GetMapping("/resumoDaCompra")
public ModelAndView exibirResumoDaCompra(HttpSession session) {
    ModelAndView modelAndView = new ModelAndView();
    
    Clientemodels usuarioLogado = (Clientemodels) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        modelAndView.setViewName("redirect:/fellas.coffe/clienteLogin");
        return modelAndView;
    }

    // Lógica para finalizar a compra
    calcularTotal();
    compra.setCliente(usuarioLogado);

    // Adicionar a compra salva ao modelo
    List<String> endereco = usuarioLogado.getEnderecosEntrega();

    modelAndView.addObject("endereco", endereco);
    modelAndView.addObject("compra", compra);
    modelAndView.addObject("listarItens", intemCompra);
    modelAndView.addObject("user", usuarioLogado);
    modelAndView.setViewName("cliente/resumoDaCompra");
    return modelAndView;
}



    @PostMapping("/finalizarCompra")
    public ModelAndView finalizarCompra(@RequestParam("payment") String formaPagamento, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        Clientemodels usuarioLogado = (Clientemodels) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null) {
            modelAndView.setViewName("redirect:/fellas.coffe/clienteLogin");
            return modelAndView;
        }

        // Lógica para finalizar a compra
        calcularTotal();
        compra.setCliente(usuarioLogado);

        // Adicionar forma de pagamento selecionada
        compra.setFormaDePagamnto(formaPagamento);
        

        
        // Adicionar a compra salva ao modelo
        List<String> endereco = usuarioLogado.getEnderecosEntrega();
     
        modelAndView.addObject("endereco", endereco);
        modelAndView.addObject("compra", compra);
        modelAndView.addObject("listarItens", intemCompra);
        modelAndView.addObject("usuarioLogado", usuarioLogado);
        modelAndView.setViewName("cliente/resumoDaCompra");
        // Resto do código...

        return modelAndView;
    }




    @PostMapping("/salvarPedido")
public ModelAndView exibirPedido( HttpSession session) {
    ModelAndView modelAndView = new ModelAndView();

    Clientemodels usuarioLogado = (Clientemodels) session.getAttribute("usuarioLogado");

    if (usuarioLogado == null) {
        modelAndView.setViewName("redirect:/fellas.coffe/clienteLogin");
        return modelAndView;
    }

    // Lógica para finalizar a compra
    calcularTotal();
    compra.setCliente(usuarioLogado);

    // Adicionar forma de pagamento selecionada
   

    // Gerar número sequencial do pedido
    String numeroPedido = gerarNumeroPedido();
    compra.setNumeroPedido(numeroPedido);
     // Definir o status do pedido
    compra.setStatus(StatusPedido.EM_ANDAMENTO);

  
 
    // Salvar a compra no banco de dados
    Compra compraSalva = comprasrespo.save(compra);

    // Salvar os itens da compra no banco de dados
    for (ItenCompraProduto it : intemCompra) { 
        it.setCompra(compraSalva);
        itenComprarespo.save(it);
    }

    // Exibir mensagem ao cliente
    String mensagem = "Pedido criado com sucesso!\n";
    mensagem += "Número do Pedido: " + numeroPedido + "\n";
    mensagem += "Valor Total: " + compraSalva.getValorTotal() + "\n";
    mensagem +=  "Status do Pedido: " + compraSalva.getStatus();
 
    // Adicionar a compra salva e a mensagem ao modelo
    modelAndView.addObject("compra", compraSalva);
    modelAndView.addObject("mensagem", mensagem);
    modelAndView.setViewName("cliente/confirmacaodepedido");

    // Resto do código...

    return modelAndView;
}

private String gerarNumeroPedido() {
    // Lógica para gerar um número sequencial do pedido
    // Pode ser implementado de várias maneiras, por exemplo, utilizando um contador no banco de dados
    // Neste exemplo, vamos gerar um número aleatório entre 1000 e 9999
    int numero = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;
    return String.valueOf(numero);
}




    @PostMapping("/Logout")
    public String logout(HttpSession session) {
        // Recupera o carrinho do cliente da sessão
        Clientemodels clientemodels = (Clientemodels) session.getAttribute("userLogado");

        // Zera o carrinho

        intemCompra.removeAll(intemCompra);

        session.invalidate();

        return "redirect:fellas.coffe/homeCliente";
    }

}