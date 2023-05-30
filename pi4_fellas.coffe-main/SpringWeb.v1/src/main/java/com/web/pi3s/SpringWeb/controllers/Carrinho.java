package com.web.pi3s.SpringWeb.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.web.pi3s.SpringWeb.models.ItenCompraProduto;
import com.web.pi3s.SpringWeb.models.Produtomodels;
import com.web.pi3s.SpringWeb.repositorio.Produtorespo;

@Controller
public class Carrinho {

    @Autowired
    Produtorespo produtorespo;

    private List<ItenCompraProduto> intemCompra = new ArrayList<ItenCompraProduto>();

    @GetMapping("/carrinho")

    public ModelAndView comprar() {
        ModelAndView mv = new ModelAndView("cliente/carrinho");
        mv.addObject("listaItens", intemCompra);
        return mv;

    }

    @GetMapping("/adcionarProduto/{id}")
    public ModelAndView mostrarCarrinho(@PathVariable("id") Long id) {

        System.out.println("ID" + id);
        ModelAndView mv = new ModelAndView("cliente/carrinho");
        Optional<Produtomodels> prod = produtorespo.findById(id);
        Produtomodels produto = prod.get();

        int controle = 0;
        for (ItenCompraProduto it : intemCompra) {
            if (it.getProduto().getId().equals(produto.getId())) {
                it.setQuantidade(it.getQuantidade() + 1);
                controle = 1;
                break;
            }

        }
        if (controle == 0) {
            ItenCompraProduto item = new ItenCompraProduto();
            item.setProduto(produto);
            item.setValorProduto(produto.getPrecoProduto());
            item.setQuantidade(item.getQuantidade() + 1);
            item.setValorTotal(item.getQuantidade() * item.getValorProduto());
            intemCompra.add(item);
        }
        mv.addObject("listaItens", intemCompra);
        return mv;

    }

    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public ModelAndView alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
        ModelAndView mv = new ModelAndView("cliente/carrinho");
        for (ItenCompraProduto it : intemCompra) {
            if (it.getProduto().getId().equals(id)) {
                if(acao == 1){
                it.setQuantidade(it.getQuantidade() + 1);
            } else if (acao == 0) {
                if(it.getQuantidade() <= 0){
                    break;
                }
                it.setQuantidade(it.getQuantidade() - 1);
             
            }
            break;
            }
        }
        
        mv.addObject("listaItens", intemCompra);
        return mv;
    
     
}



@GetMapping("/removerItem/{id}")
public ModelAndView removerItem(@PathVariable Long id) {
    ModelAndView mv = new ModelAndView("cliente/carrinho");
    
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
    
    mv.addObject("listaItens", intemCompra);
    return mv;
}


}