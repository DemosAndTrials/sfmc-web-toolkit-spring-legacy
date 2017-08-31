package sfdc.mc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import static java.lang.System.out;

/**
 * API Controller
 */
@Controller
@RequestMapping("api")
public class ApiController {

    /**
     * Index page - Getting Started
     *
     * @return
     */
    @GetMapping(value = "/index")
    public String index() {
        return "api/index";
    }

    /**
     * Index page - Getting Started
     *
     * @return
     */
    @GetMapping(value = "/rest")
    public String rest() {
        return "api/rest";
    }

    /**
     * Index page - Getting Started
     *
     * @return
     */
    @GetMapping(value = "/soap")
    public String soap() {
        return "api/soap";
    }

    @RequestMapping("/test")
    public ResponseEntity test(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new ResponseEntity(String.format("Hello, %s!", name), HttpStatus.OK);
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity post(@RequestBody String json) {
        out.println("************** " + json + " *****************");
        return new ResponseEntity("OK", HttpStatus.OK);
    }

}
