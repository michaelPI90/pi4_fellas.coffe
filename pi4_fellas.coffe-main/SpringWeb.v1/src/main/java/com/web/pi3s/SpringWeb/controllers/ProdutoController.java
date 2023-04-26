package com.web.pi3s.SpringWeb.controllers;

import java.io.IOException;
import java.util.*;


import javax.print.event.PrintEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import com.web.pi3s.SpringWeb.models.Produtomodels;
import com.web.pi3s.SpringWeb.repositorio.Produtorespo;
import com.web.pi3s.SpringWeb.util.UploadUtil;

@Controller
public class ProdutoController {

     @Autowired
    Produtorespo produtorespo;

    @GetMapping("/ListarProdutos")
    public ModelAndView listaProduto(ModelAndView modelAndView) {
      List<Produtomodels> produtosOrdenados =  this.produtorespo.ordernar();
        //Produtomodels produtos = new Produtomodels();
        //Collections.sort(produtosOrdenados, Collections.reverseOrder());
        modelAndView.setViewName("produtos/produtos");
        //modelAndView.addObject("produtos", Collections.sort(produtosOrdenados, Collections.reverseOrder()));
        modelAndView.addObject("produtos", produtosOrdenados);

        return modelAndView;

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ListarProdutosEstoquista")
    public ModelAndView listaProdutoEstoquista(ModelAndView modelAndView) {
      List<Produtomodels> produtosOrdenados =  this.produtorespo.ordernar();
        //Produtomodels produtos = new Produtomodels();
        //Collections.sort(produtosOrdenados, Collections.reverseOrder());
        modelAndView.setViewName("produtos/produtosEstoquista");
        //modelAndView.addObject("produtos", Collections.sort(produtosOrdenados, Collections.reverseOrder()));
        modelAndView.addObject("produtos", produtosOrdenados);

        return modelAndView;

    }

   
    @GetMapping("/CadastroProduto")
    public ModelAndView formProduto(ModelAndView modelAndView) {
        
       Produtomodels produtos = new Produtomodels();
        modelAndView.setViewName("cadastrarProduto/cadastrarProduto");
        modelAndView.addObject("produtos", produtos);

        return modelAndView;

    }

    @PostMapping("/CadastroProduto/cadastrado")
    public ModelAndView cadastro(@ModelAttribute Produtomodels produtos, @RequestParam("imagem") MultipartFile imagem) {
        ModelAndView mv = new ModelAndView("produtos/produtos");

        mv.addObject("produtos", produtos);

        try {
            if (UploadUtil.fazerUploadImagem(imagem)) {
                produtos.setImagemProduto(imagem.getOriginalFilename());

            }
            produtos.setStatusAtivo(true);
            produtorespo.save(produtos);
            System.out.println("Salvo com sucesso: " + produtos.getNomeProduto() + " " + produtos.getImagemProduto());
            return listaProduto(mv);
        } catch (Exception e) {
            mv.addObject("erro", e.getMessage());
            System.out.println("Erro ao salvar " + e.getMessage());
            return mv;
        }

    }

    @GetMapping("/ConsultarProduto")
    public String consultar(Model model, @RequestParam String nomeProduto) {

        List<Produtomodels> produtoConsultado = this.produtorespo.findByNomeProdutoContainingIgnoreCase(nomeProduto);

        if (!produtoConsultado.isEmpty()) {
            model.addAttribute("produtos", Arrays.asList(produtoConsultado.toArray()));
            return "/produtos/produtos";
        }

        model.addAttribute("erro", "Produto não encontrado!");
        return "/produtos/produtos";
    }




    @GetMapping("/statusProduto/{productId}")
    public String atualizar(Model model, @PathVariable Integer productId, Produtomodels user) {

        salvarProduto( model, productId, user);

        return "redirect:/ListarProdutos";
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<Iterable<Produtomodels>> listarTodos() {

        return ResponseEntity.ok(produtorespo.findAll());

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/salvarProduto")
    public ModelAndView salvarProduto(Model model, Integer userID, Produtomodels product) {
        ModelAndView modelAndView = new ModelAndView();
        Produtomodels  u = produtorespo.findByid(userID);
       

        if (u.isStatusAtivo()) {
            u.setStatusAtivo(false);

        } else {
            u.setStatusAtivo(true);
        }
        produtorespo.save(u);

        modelAndView.setViewName("produtos/produtos");
        return modelAndView;

    }

}
