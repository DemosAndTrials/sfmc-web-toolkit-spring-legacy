package sfdc.mc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sfdc.mc.model.CustomActivityConfig;
import sfdc.mc.service.CustomActivityService;
import sfdc.mc.util.ConfigConstants;

import javax.validation.Valid;

/**
 * Custom Activity Controller
 */
@Controller
@RequestMapping("/ca")
public class CustomActivityController {

    @Autowired
    CustomActivityService customActivityService;

    /**
     * Custom Activity UI
     * Endpoint for the UI displayed to marketers while configuring this activity.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "")
    public String index(Model model) {

        String caNumSteps = System.getenv(ConfigConstants.CA_NUM_STEPS) != null ? System.getenv(ConfigConstants.CA_NUM_STEPS) : "1";
        System.out.println("*** Number of steps: " + caNumSteps);
        model.addAttribute("numSteps", caNumSteps);
        return "ca/index";
    }

    /**
     * execute - The API calls this method for each contact processed by the journey.
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "{type}/execute", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity execute(@PathVariable String type, @RequestBody String json) {
        System.out.println("*** type: " + type + " *** execute: " + json);
        String result = null;
        try {
            result = customActivityService.executeActivity(type);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * save - Notification is sent to this endpoint when a user saves the interaction (optional).
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "{type}/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity save(@PathVariable String type, @RequestBody String json) {
        System.out.println("*** type: " + type + " *** save: " + json);
        return new ResponseEntity("OK", HttpStatus.OK);
    }

    /**
     * publish - Notification is sent to this endpoint when a user publishes the interaction.
     *
     * @return
     */
    @RequestMapping(value = "{type}/publish", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity publish(@PathVariable String type, @RequestBody String json) {
        System.out.println("*** type: " + type + "  *** publish: " + json);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * validate - Notification is sent to this endpoint when a user performs some validation
     * as part of the publishing process (optional).
     *
     * @return
     */
    @RequestMapping(value = "{type}/validate", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity validate(@PathVariable String type, @RequestBody String json) {
        System.out.println("*** type: " + type + "  *** validate: " + json);
        // TODO validation
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * stop - Notification is sent to this endpoint when a user stops any active version of the interaction.
     * The notification will be for that particular version’s activity (optional).
     *
     * @return
     */
    @RequestMapping(value = "{type}/stop", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity stop(@PathVariable String type, @RequestBody String json) {
        System.out.println("*** type: " + type + "  *** stop: " + json);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Creation of config.json
     * Every custom Journey Builder activity must include a config.json in the root of its endpoint.
     * https://developer.salesforce.com/docs/atlas.en-us.noversion.mc-app-development.meta/mc-app-development/creating-activities.htm
     *
     * @return
     */
    @RequestMapping(value = "/{id}/config.json")
    public ResponseEntity getConfig(@PathVariable String id) {
        try {
            String result = customActivityService.getConfigByType(id);
            System.out.println("*** config.json: " + result);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /*
    * Config CRUD operations
    */

    @GetMapping(value = "/create")
    public String createConfig(Model model) {
        System.out.println("*** create config ***");
        model.addAttribute("config", new CustomActivityConfig());
        return "ca/create";
    }

    /**
     * Create config or return back to the list
     * Notice how the BindingResult has to be immediately after the object I have annotated with @Valid.
     * @param config
     * @param action
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping(value = "/create")
    public String createConfig(@RequestParam(required = false) String action, @Valid @ModelAttribute("config") CustomActivityConfig config, BindingResult bindingResult, Model model) {
        // save
        if( action.equals("save") ){
            if (bindingResult.hasErrors()) {
                model.addAttribute("config", config);
                return "ca/create";
            }
            CustomActivityConfig res = customActivityService.createConfig(config);
        }
        // cancel
        else if( action.equals("cancel") ){
            //handle renew
        }
        return "redirect:/ca/list";
    }

    @GetMapping(value = "/create/{id}")
    public String createConfig(@PathVariable String id, Model model) {
        System.out.println("*** update config: " + id);
        CustomActivityConfig config = customActivityService.getConfigById(id);
        model.addAttribute("config", config != null ? config : new CustomActivityConfig());
        return "ca/create";
    }

    @GetMapping(value = "/list")
    public String listConfig(Model model) {
        System.out.println("*** list configs ***");
        Iterable<CustomActivityConfig> list = customActivityService.getConfigs();
        model.addAttribute("configs", list);
        return "ca/list";
    }

    /**
     * Delete config
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/delete/{id}")
    public String deleteConfig(@PathVariable String id) {
        System.out.println("*** delete config: " + id);
        customActivityService.deleteConfigById(id);
        return "redirect:/ca/list";
    }

}