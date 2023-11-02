package org.zerock.myapp.controller;


import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.zerock.myapp.service.CategoriesService;


@Log4j2
@NoArgsConstructor


@Controller
public class MainController {

    @Setter(onMethod_ = @Autowired)
    private CategoriesService categoriesService;

    @GetMapping("/main")
    String main(){
        log.trace("main() invoked.");

        return "main";
    } // main


    @PostMapping("/main")
    void loginSuccess(){
        log.trace("loginSuccess({}) Invoked.");

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
    } // loginSuccess

    @GetMapping("/main2")
    void main2(){
        log.trace("main2() invoked.");

    } // main2

//    ====================================================================================================
//              여기서 Category 매핑합니다. 카테고리 초기화됐으면 실행시 이거 한번 URI 호출 하면 카테고리에 값이 들어갑니다.!
////
//    @GetMapping("/asjdlkasjdlk")
//    void createCa(){
//        log.trace("createCa() Invoked.");
//
//        this.categoriesService.createCategories();
//    } // createCa
//


//    ====================================================================================================

} // end class
