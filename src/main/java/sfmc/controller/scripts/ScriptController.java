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
    public ModelAndView index() {
        return getScriptListModel();
    }

    /**
     * Create script example
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView scriptCreate() {
        ModelAndView modelAndView = new ModelAndView();
        ScriptItem script = new ScriptItem();
        modelAndView.addObject("script", script);
        modelAndView.setViewName("scripts/script-create");
        return modelAndView;
    }

    /**
     * Create script example
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView scriptCreate(@Valid @ModelAttribute("script") ScriptItem script, BindingResult bindingResult, Authentication auth) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("scripts/script-create");
        } else {
            // save script here
            User user = userService.findUserByEmail(auth.getName());
            if (user != null)
                script.setUserId(user.getId());

            ScriptItem s = scriptService.createScript(script);
            return new ModelAndView("redirect:/scripts/list");
        }
        return modelAndView;
    }

    /**
     * Edit script
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView scriptEdit(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("scripts/script-create");
        System.out.println("*** update config: " + id);
        ScriptItem script = scriptService.getScript(id);
        modelAndView.addObject("script", script != null ? script : new ScriptItem());
        return modelAndView;
    }

    /**
     * Delete script item using jquery
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/delete/{id}")
    public ResponseEntity deleteScript(@PathVariable String id) {
        return new ResponseEntity(scriptService.deleteScriptById(id), HttpStatus.OK);
    }

    /**
     * AMPscript page
     *
     * @return
     */
    @GetMapping(value = "/list")
    public ModelAndView scriptList() {
        return getScriptListModel();
    }

    private ModelAndView getScriptListModel() {
        Iterable<ScriptItem> items = scriptService.getScripts();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("scripts/script-list");
        modelAndView.addObject("scripts", items);
        return modelAndView;
    }

    /**
     * AMPscript docs page
     *
     * @return
     */
    @GetMapping(value = "/ampscript")
    public ModelAndView ampscript() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("scripts/ampscript");
        return modelAndView;
    }

    /**
     * Server-Side JavaScript (SSJS) docs Page
     *
     * @return
     */
    @GetMapping(value = "/ssjs")
    public String ssjs() {
        System.out.println("*******************************");

        return "scripts/ssjs";
    }

    /**
     * Guide Template Language (GTL) docs Page
     *
     * @return
     */
    @GetMapping(value = "/gtl")
    public String gtl() {
        System.out.println("*******************************");

        return "scripts/gtl";
    }

    // TODO check if needed
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

}
