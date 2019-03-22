package com.fgt.console.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 执笔
 */
@Controller
@RequestMapping
public class IndexController {

    @RequestMapping
    public String index(Model model) {
        return "redirect:console/index";
    }
}
