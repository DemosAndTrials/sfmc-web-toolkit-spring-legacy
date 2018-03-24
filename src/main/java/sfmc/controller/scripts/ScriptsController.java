package sfmc.controller.scripts;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Scripts Controller
 */
@Controller
@RequestMapping("scripts")
public class ScriptsController {

    /**
     * Index page - Getting Started
     *
     * @return
     */
    @RequestMapping(value = {"" ,"/", "/index"})
    public String index() {
        return "scripts/index";
    }

    /**
     * AMPscript page
     *
     * @return
     */
    @RequestMapping(value = "/ampscript")
    public String ampscript() {
        System.out.println("*******************************");

        return "scripts/ampscript";
    }

    /**
     * Server-Side JavaScript (SSJS) Page
     *
     * @return
     */
    @RequestMapping(value = "/ssjs")
    public String ssjs() {
        System.out.println("*******************************");

        return "scripts/ssjs";
    }

    /**
     * Guide Template Language (GTL) Page
     *
     * @return
     */
    @RequestMapping(value = "/gtl")
    public String gtl() {
        System.out.println("*******************************");

        return "scripts/gtl";
    }
}