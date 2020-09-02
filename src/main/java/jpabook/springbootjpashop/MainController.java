package jpabook.springbootjpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("main")
    public String member(Model model){
        model.addAttribute("data", "Junseok");
        return "main";
    }
}
