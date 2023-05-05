package com.web.pi3s.SpringWeb.controllers;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import com.web.pi3s.SpringWeb.models.Imagenmodels;
import com.web.pi3s.SpringWeb.models.Produtomodels;
import com.web.pi3s.SpringWeb.repositorio.Imagenrespo;
import com.web.pi3s.SpringWeb.repositorio.Produtorespo;

@Controller
public class ProdutoController {

    @Autowired
    Produtorespo produtorespo;
    @Autowired
    Imagenrespo imagenrespo;

    @GetMapping("/ListarProdutos")
    public ModelAndView listaProduto(ModelAndView modelAndView) {
        List<Produtomodels> produtosOrdenados = this.produtorespo.ordernar();
        // Produtomodels produtos = new Produtomodels();
        // Collections.sort(produtosOrdenados, Collections.reverseOrder());
        modelAndView.setViewName("produtos/produtos");
        // modelAndView.addObject("produtos", Collections.sort(produtosOrdenados,
        // Collections.reverseOrder()));
        modelAndView.addObject("produtos", produtosOrdenados);

        return modelAndView;

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ListarProdutosEstoquista")
    public ModelAndView listaProdutoEstoquista(ModelAndView modelAndView) {
        List<Produtomodels> produtosOrdenados = this.produtorespo.ordernar();
        // Produtomodels produtos = new Produtomodels();
        // Collections.sort(produtosOrdenados, Collections.reverseOrder());
        modelAndView.setViewName("produtos/produtosEstoquista");
        // modelAndView.addObject("produtos", Collections.sort(produtosOrdenados,
        // Collections.reverseOrder()));
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
    public ModelAndView cadastro(@ModelAttribute("produto") Produtomodels produtos, Imagenmodels novaImagem,
            @RequestParam("imagens") MultipartFile[] imagens) throws IOException {

       // List<byte[]> imagensBytes = new ArrayList<>();

       for (int i = 0; i < imagens.length; i++) {
           
            novaImagem.setNomeImagem(imagens[i].getOriginalFilename());
           
            novaImagem.setConteudoImagem(imagens[i].getBytes());
            novaImagem.setProduto(produtos);
            produtos.setImagem(imagens[i].getBytes());
          
         
        }
       
        produtorespo.save(produtos);
        imagenrespo.save(novaImagem);
     

        ModelAndView mv = new ModelAndView("produtos/produtos");
        mv.addObject("produtos", produtos);

       
        return listaProduto(mv);

    }

    @GetMapping("/product/{productId}")
public String showProductPage(Model model, @PathVariable Integer productId) {
    Optional<Imagenmodels> imageList =  imagenrespo.findById(productId);
    model.addAttribute("imageList", imageList);

    return "/mostrar/teste";
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

        salvarProduto(model, productId, user);

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
        Produtomodels u = produtorespo.findByid(userID);

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
