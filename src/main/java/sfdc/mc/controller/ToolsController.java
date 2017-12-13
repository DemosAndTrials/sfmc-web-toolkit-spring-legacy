package sfdc.mc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Tools Controller
 */
@Controller
@RequestMapping("tools")
public class ToolsController {

    /**
     * Index page - Getting Started
     *
     * @return
     */
    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "tools/index";
    }
}
