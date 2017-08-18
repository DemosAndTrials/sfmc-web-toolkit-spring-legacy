package sfdc.mc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home Controller
 */
@Controller
public class HomeController {

    /**
     * Index page
     */
    @RequestMapping(value = "/")
    public String index() {
        System.out.println("*******************************");
        return "index";
    }
}
