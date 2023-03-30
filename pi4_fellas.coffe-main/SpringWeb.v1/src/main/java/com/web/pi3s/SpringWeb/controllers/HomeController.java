package com.web.pi3s.SpringWeb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



 @Controller
 public class HomeController {

    @RequestMapping("/")
    public String index(Model model){
		model.addAttribute("Seja ", "del");
		return "/home/index";
	}



    
  

    @PostMapping("/login")
    public String index(){
 

        return "redirect:/despensa/menuDespensa";
    }


 }
 


