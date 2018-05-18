package sfmc.controller.scripts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sfmc.model.Authentication.User;
import sfmc.model.Scripts.ScriptItem;
import sfmc.service.ScriptService;
import sfmc.service.UserService;
import javax.validation.Valid;

@Controller
@RequestMapping("scripts")
public class ScriptController {

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private UserService userService;

    /**
     * Index page - Getting Started
     *
     * @return
     */
    @GetMapping(value = {"", "/", "/index"})
    public String index() {
        return "scripts/index";
    }

    /**
     * AMPscript page
     *
     * @return
     */
    @GetMapping(value = "/ampscript")
    public ModelAndView ampscript() {
        Iterable<ScriptItem> items = scriptService.getScripts();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("scripts/ampscript-list");
        modelAndView.addObject("scripts", items);
        return modelAndView;
    }

    @RequestMapping(value = "/ampscript/create", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        ScriptItem script = new ScriptItem();
        modelAndView.addObject("script", script);
        modelAndView.setViewName("scripts/ampscript-create");
        return modelAndView;
    }

    @RequestMapping(value = "/ampscript/create", method = RequestMethod.POST)
    public ModelAndView createNewScript(@Valid @ModelAttribute("script") ScriptItem script, BindingResult bindingResult, Authentication auth) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("scripts/ampscript-create");
        } else {
            // save here
            User user = userService.findUserByEmail(auth.getName());
            if (user != null)
                script.setUserId(user.getId());
            script.setType(0);
            ScriptItem s = scriptService.createScript(script);
            return new ModelAndView("redirect:/scripts/ampscript");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/ampscript/getScript/{id}")
    public ResponseEntity getScript(@PathVariable String id) {
        try {
            ScriptItem result = scriptService.getScript(id);
            return new ResponseEntity(result.getContent(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * delete ampscript item using jquery
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/ampscript/delete/{id}")
    public ResponseEntity deleteConfig(@PathVariable String id) {
        return new ResponseEntity(scriptService.deleteScriptById(id), HttpStatus.OK);
    }

    /**
     * Server-Side JavaScript (SSJS) Page
     *
     * @return
     */
    @GetMapping(value = "/ssjs")
    public String ssjs() {
        System.out.println("*******************************");

        return "scripts/ssjs";
    }

    /**
     * Guide Template Language (GTL) Page
     *
     * @return
     */
    @GetMapping(value = "/gtl")
    public String gtl() {
        System.out.println("*******************************");

        return "scripts/gtl";
    }

}
