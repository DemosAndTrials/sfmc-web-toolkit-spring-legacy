package sfmc.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sfmc.service.ApiService;

/**
 * API Controller
 */
@Controller
@RequestMapping("api")
public class ApiIndexController {

    @Autowired
    ApiService apiService;

    /**
     * Index page - Getting Started
     *
     * @return
     */
    @GetMapping(value = {"", "/", "/index"})
    public String index() {
        return "api/index";
    }

    /**
     * SDK index page
     *
     * @return
     */
    @GetMapping(value = "/sdk")
    public String sdk() {
        return "api/sdk";
    }

    /**
     * REST Index page
     *
     * @return
     */
    @GetMapping(value = "/rest")
    public String rest() {
        return "api/rest";
    }

    /**
     * SOAP Index page
     *
     * @return
     */
    @GetMapping(value = "/soap")
    public String soap() {
        return "api/soap";
    }

}
