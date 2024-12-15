package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        
        //이 리턴이, 아마도 스프링한테 리턴하는 것 같음
        return "home";
    }
}
