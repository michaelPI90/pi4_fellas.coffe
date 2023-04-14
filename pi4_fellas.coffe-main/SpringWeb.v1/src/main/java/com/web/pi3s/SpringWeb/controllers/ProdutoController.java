package com.web.pi3s.SpringWeb.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javax.print.event.PrintEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import com.web.pi3s.SpringWeb.models.Produtomodels;
import com.web.pi3s.SpringWeb.repositorio.Produtorespo;
import com.web.pi3s.SpringWeb.util.UploadUtil;

@Controller
public class ProdutoController {

    @Autowired
    Produtorespo repository;

    @GetMapping("/ListarProdutos")
    public ModelAndView listaProduto(ModelAndView modelAndView) {
        // ModelAndView modelAndView = new ModelAndView();
        //Produtomodels produtos = new Produtomodels();
        modelAndView.setViewName("produtos/produtos");
        modelAndView.addObject("produtos", repository.findAll());

        return modelAndView;

    }

    @GetMapping("/CadastroProduto")
    public ModelAndView formProduto(ModelAndView modelAndView) {
        // ModelAndView modelAndView = new ModelAndView();
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
            repository.save(produtos);
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

        Optional<Produtomodels> produtoConsultado = this.repository.findByNomeProduto(nomeProduto);

        if (produtoConsultado.isPresent()) {
            model.addAttribute("produtos", Arrays.asList(produtoConsultado.get()));
            return "/produtos/produtos";
        }

        model.addAttribute("erro", "Produto não encontrado!");
        return "/produtos/produtos";
    }

}
