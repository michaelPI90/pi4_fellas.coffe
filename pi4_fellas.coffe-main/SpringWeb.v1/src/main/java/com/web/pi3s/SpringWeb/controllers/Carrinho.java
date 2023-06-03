package com.web.pi3s.SpringWeb.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.web.pi3s.SpringWeb.models.Clientemodels;
import com.web.pi3s.SpringWeb.models.Compra;
import com.web.pi3s.SpringWeb.models.ItenCompraProduto;
import com.web.pi3s.SpringWeb.models.Produtomodels;
import com.web.pi3s.SpringWeb.repositorio.Clienterespo;
import com.web.pi3s.SpringWeb.repositorio.Produtorespo;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class Carrinho {

    @Autowired
    Produtorespo produtorespo;
@Autowired 
Clienterespo clienterespo;
    Clientemodels cliente;
    
    private List<ItenCompraProduto> intemCompra = new ArrayList<ItenCompraProduto>();
    public Compra compra = new Compra();

    public void calcularTotal() {
        compra.setValorTotal(0.);
        for (ItenCompraProduto it : intemCompra) {
            compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
            System.out.println(compra);
        }
    }

    @GetMapping("/carrinho")
    public ModelAndView comprar() {
        ModelAndView mv = new ModelAndView("cliente/carrinho");

        calcularTotal();
        mv.addObject("compra", compra);
        mv.addObject("listaItens", intemCompra);
        return mv;

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

    @PostMapping("/finalizarCompra")
    public ModelAndView buscarUserlogado(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
    
        Clientemodels usuarioLogado = (Clientemodels) session.getAttribute("usuarioLogado");
    
        if (usuarioLogado == null) {
            modelAndView.setViewName("redirect:/fellas.coffe/clienteLogin");
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



// @PostMapping("finalizarCompra")
// ModelAndView finalizarCompra(String formaDePagamento){
//     ModelAndView mv =  new ModelAndView("/");
//     compra.setCliente(cliente);
   


// }


//     @GetMapping("/resumoDoPedido")
// public String exibirResumoDoPedido(Model model, HttpSession session) {
//     // Verificar se o cliente está logado na sessão
//     Clientemodels clienteLogado = (Clientemodels) session.getAttribute("usuarioLogado");
//     if (clienteLogado == null) {
//         // Cliente não está logado, redirecionar para a página de login
//         return "redirect:/clienteLogin";
//     }

//     // Aqui você pode recuperar os dados do banco de dados e definir o modelo
//     // Buscar a lista de pedidos do cliente logado
//     ItenCompraProduto it  = clienteLogado.
//     model.addAttribute("pedidos", pedidos);

//     // Dados da entrega
//     String endereco = clienteLogado.getEnderecoEntrega();
//     model.addAttribute("endereco", endereco);

//     // Dados do pagamento
    

//     return "/cliente/resumoDoPedido";
// }


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