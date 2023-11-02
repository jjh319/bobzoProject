package org.zerock.myapp.controller;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.myapp.domain.UsersDTO;
import org.zerock.myapp.service.UsersService;

@Log4j2
@NoArgsConstructor

@Controller
@RequestMapping("/users")
public class UsersController {

    @Setter(onMethod_ = @Autowired)
    private UsersService usersService;

    @GetMapping("/signup")
    public void signup(){
        log.trace("signup() Invoked.");
    } // signup

    @PostMapping("/register")
    public String registerUser(UsersDTO usersDTO) {
        usersService.register_user(usersDTO);

        return "redirect:/main";
    } // registerUser


    @PostMapping("/checkDuplicate")
    public ResponseEntity<String> checkDuplicate(String userId) {
        log.trace("\t☆★ 중복체크 메소드 진입 ★☆");

        String result;
        if (usersService.existsByUserId(userId)) {
            result = "true"; // 사용 가능한 User ID
        } else {
            result = "false"; // 이미 사용 중인 User ID
        }
        return ResponseEntity.ok(result);
    }

} // end class
