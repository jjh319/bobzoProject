package org.zerock.myapp.controller;


import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Log4j2
@NoArgsConstructor

@RequestMapping("/admin")
@Controller
public class AdminController {

    @GetMapping("/main")
    String main(){
        log.trace("main() invoked.");
        return "/admin/main";
    } // main

    @GetMapping("/userManagement")
    String userManagement(){
        log.trace("userManagement() invoked.");
        return "/admin/userManagement";
    } // userManagement

    @GetMapping("/QnAManagement")
    String qnAManagement(){
        log.trace("qnAManagement() invoked.");
        return "/admin/QnAManagement";
    } // qnAManagement


    @GetMapping("/ReportManagement")
    String reportManagement(){
        log.trace("reportManagement() invoked.");
        return "/admin/ReportManagement";
    } // reportManagement


    @GetMapping("/RecipeManagement")
    String recipeManagement(){
        log.trace("recipeManagement() invoked.");
        return "/admin/RecipeManagement";
    } // recipeManagement

} // end class
