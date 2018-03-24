package sfmc.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Calendar;

/**
 * Demo Controller
 */
@Controller
@RequestMapping("demo")
public class DemoController {

    /**
     * Index page - Getting Started
     *
     * @return
     */
    @RequestMapping(value = {"" ,"/", "/index"})
    public String index() {
        return "demo/index";
    }

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "demo/greeting";
    }

    @RequestMapping("/bootstrap")
    public String bootstrap(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        model.addAttribute("year", year);

        return "demo/bootstrap";
    }

    @RequestMapping(value = "/lightning")
    public String lightning() {
        System.out.println("*******************************");

        return "demo/lightning";
    }

}