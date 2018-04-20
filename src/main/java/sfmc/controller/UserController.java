package sfmc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sfmc.model.Authentication.ApiIntegrationSet;
import sfmc.model.Authentication.User;
import sfmc.service.UserService;
import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/settings"}, method = RequestMethod.GET)
    public ModelAndView settingsIndex(Authentication auth) {
        System.out.println("*** user: " + auth.getName());
        User user = userService.findUserByEmail(auth.getName());
        ApiIntegrationSet apiSet = userService.findApiSetByUserId(user.getId());
        if (apiSet == null) {
            apiSet = new ApiIntegrationSet();
            apiSet.setUserId(user.getId());
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.addObject("apiSet", apiSet);
        modelAndView.setViewName("user/settings");
        return modelAndView;
    }

    @RequestMapping(value = {"/settings"}, method = RequestMethod.POST)
    public ModelAndView settingsUpdate(@Valid User user, BindingResult bindingResult) {
        //System.out.println("*** user: " + auth.getName());
        ModelAndView modelAndView = new ModelAndView();
        if (!bindingResult.hasErrors()) {
            userService.saveUser(user);
        }
        ApiIntegrationSet apiSet = userService.findApiSetByUserId(user.getId());
        if (apiSet == null) {
            apiSet = new ApiIntegrationSet();
            apiSet.setUserId(user.getId());
        }
        modelAndView.addObject("apiSet", apiSet);
        modelAndView.addObject("user",user);
        modelAndView.setViewName("user/settings");
        return modelAndView;
    }

    @RequestMapping(value = {"/api"}, method = RequestMethod.POST)
    public ModelAndView apiSave(@Valid ApiIntegrationSet api, BindingResult bindingResult, Authentication auth) {
        //System.out.println("*** user: " + auth.getName());
        ModelAndView modelAndView = new ModelAndView();

        if (!bindingResult.hasErrors()) {
            api = userService.save(api);
        }
        modelAndView.addObject("apiSet", api);
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/settings");
        return modelAndView;
    }

}
