package com.deloitte.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/")
public class IndexController {

    @Value("${uiVersion}")
    String uiVersion;

    @RequestMapping (method = GET)
    public String indexPage(Model model){
        model.addAttribute("uiVersion", uiVersion);
        return "index";
    }
}
