package sfmc.controller.customActivity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sfmc.model.CustomActivity.CustomActivityConfig;
import sfmc.service.CustomActivityService;
import javax.validation.Valid;

/**
 * Custom Activity Controller
 *
 * - jwt emcoding
 */
@Controller
@RequestMapping("/ca")
public class CustomActivityController {

    @Autowired
    CustomActivityService customActivityService;

     /*
    * Config UI operations
    */

    /**
     * Custom Activity UI
     * Endpoint for the UI displayed to marketers while configuring this activity.
     *
     * @return
     */
    @RequestMapping(value = {"{id}/ui", "{id}/ui/edit", "{id}/ui/config"})
    public String editModal(@PathVariable String id, @RequestParam(value = "numSteps", defaultValue = "0") String numSteps) {
        System.out.println("*** UI for: " + id + " with " + numSteps + " steps ***");
        return "ca/ui/editModal";
    }

    /**
     * running hover
     *
     * @return
     */
    @RequestMapping(value = "{id}/ui/hover")
    public String runningHover(@PathVariable String id, Model model) {
        System.out.println("*** running hover: " + id + " ***");
        CustomActivityConfig config = customActivityService.getConfigById(id);
        model.addAttribute("config", config != null ? config : new CustomActivityConfig());
        return "ca/ui/runningHover";
    }

    /**
     * running modal
     *
     * @return
     */
    @RequestMapping(value = "{id}/ui/modal")
    public String runningModal(@PathVariable String id, Model model) {
        System.out.println("*** running modal: " + id + " ***");
        CustomActivityConfig config = customActivityService.getConfigById(id);
        model.addAttribute("config", config != null ? config : new CustomActivityConfig());
        return "ca/ui/runningModal";
    }

     /*
    * Config Endpoints
    */

    /**
     * execute - The API calls this method for each contact processed by the journey.
     * example of request's body:
     * {
     * "inArguments":
     * [
     * {"input-id-01": ""},
     * "input-id-02": ""{},
     * {"select-01": "Option One"}
     * "activityObjectID": "5d88fd34-ba45-42ef-abda-d4a1f5830171",
     * ],
     * "journeyId": "8baa72eb-cc91-4c19-b053-e869a5bd5e42",
     * "activityId": "5d88fd34-ba45-42ef-abda-d4a1f5830171",
     * "definitionInstanceId": "45e3ec68-7b70-4e0d-9c13-218344f90fcb",
     * "activityInstanceId": "b8f5640b-ecbe-411e-a143-29d21a89159a",
     * "keyValue": "contact_emai@mail.com",
     * "mode": 0
     * }
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "{id}/execute", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity execute(@PathVariable String id, @RequestBody String json) {
        System.out.println("*** execute activity: " + id + "   data: " + json);
        customActivityService.jwtDecode(json);
        String result = null;
        try {
            result = customActivityService.executeActivity(id);
            System.out.println("*** execute activity: " + id + "  result: " + result);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
    }

    /**
     * save - Notification is sent to this endpoint when a user saves the interaction (optional).
     * called on journey activation
     * example of request's body:
     * {
     * "activityObjectID": "770222dc-a0ca-4024-836a-9644dd26f848",
     * "interactionId": "2fa18520-ec47-4db1-a5ec-6cbedae20762",
     * "originalDefinitionId": "c0b22824-c4db-441b-a8e2-7aea9bfca439",
     * "interactionKey": "6384e00d-a5cf-e993-e307-26079ad8554d",
     * "interactionVersion": "2"
     * }
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "{id}/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity save(@PathVariable String id, @RequestBody String json) {
        System.out.println("*** save activity: " + id + "  data: " + json);
        customActivityService.jwtDecode(json);
        return new ResponseEntity("OK", HttpStatus.OK);
    }

    /**
     * publish - Notification is sent to this endpoint when a user publishes the interaction.
     * called on journey activation
     * example of request's body:
     * {
     * "activityObjectID": "770222dc-a0ca-4024-836a-9644dd26f848",
     * "interactionId": "2fa18520-ec47-4db1-a5ec-6cbedae20762",
     * "originalDefinitionId": "c0b22824-c4db-441b-a8e2-7aea9bfca439",
     * "interactionKey": "6384e00d-a5cf-e993-e307-26079ad8554d",
     * "interactionVersion": "2",
     * "isPublished": true
     * }
     *
     * @return
     */
    @RequestMapping(value = "{id}/publish", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity publish(@PathVariable String id, @RequestBody String json) {
        System.out.println("*** publish activity: " + id + "  data: " + json);
        customActivityService.jwtDecode(json);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * validate - Notification is sent to this endpoint when a user performs some validation
     * as part of the publishing process (optional).
     * example of request's body:
     * {
     * "activityObjectID": "770222dc-a0ca-4024-836a-9644dd26f848",
     * "interactionId": "2fa18520-ec47-4db1-a5ec-6cbedae20762",
     * "originalDefinitionId": "c0b22824-c4db-441b-a8e2-7aea9bfca439",
     * "interactionKey": "6384e00d-a5cf-e993-e307-26079ad8554d",
     * "interactionVersion": "2"
     * }
     *
     * @return
     */
    @RequestMapping(value = "{id}/validate", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity validate(@PathVariable String id, @RequestBody String json) {
        System.out.println("*** validate activity: " + id + "  data: " + json);
        customActivityService.jwtDecode(json);
        // TODO validation
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * stop - Notification is sent to this endpoint when a user stops any active version of the interaction.
     * The notification will be for that particular version’s activity (optional).
     *
     * @return
     */
    @RequestMapping(value = "{id}/stop", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity stop(@PathVariable String id, @RequestBody String json) {
        System.out.println("*** stop activity: " + id + "  data: " + json);
        customActivityService.jwtDecode(json);
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

    /**
     * Index page - Getting Started
     *
     * @return
     */
    @GetMapping(value = {"", "/", "/index"})
    public String indexConfig() {
        return "ca/index";
    }

    /**
     * Setup page - HowTo
     *
     * @return
     */
    @GetMapping(value = "/setup")
    public String setupConfig() {
        return "ca/setup";
    }

    /**
     * List of configs
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/list")
    public String listConfig(Model model) {
        System.out.println("*** list configs ***");
        Iterable<CustomActivityConfig> list = customActivityService.getConfigs();
        model.addAttribute("configs", list);
        return "ca/list";
    }

    /**
     * create config
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/create")
    public String createConfig(Model model) {
        System.out.println("*** create config ***");
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        String host = builder.replacePath("").build().toUriString();
        model.addAttribute("config", new CustomActivityConfig(host));
        return "ca/create";
    }

    /**
     * Edit config
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "/create/{id}")
    public String editConfig(@PathVariable String id, Model model) {
        System.out.println("*** update config: " + id);
        CustomActivityConfig config = customActivityService.getConfigById(id);
        model.addAttribute("config", config != null ? config : new CustomActivityConfig());
        return "ca/create";
    }

    /**
     * create config or return back to the list
     * Notice how the BindingResult has to be immediately after the object I have annotated with @Valid.
     *
     * @param config
     * @param action
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping(value = "/create")
    public String createConfig(@RequestParam(required = false) String action, @Valid @ModelAttribute("config") CustomActivityConfig config, BindingResult bindingResult, Model model) {
        // save
        if (action.equals("save")) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("config", config);
                // set error for splits if any
                long erSplits = config.getSplits().stream().filter(s -> !s.isValid()).count();
                if (erSplits != 0) {
                    FieldError error = new FieldError("config", "splits", "may not be empty");
                    bindingResult.addError(error);
                }
                // set error for steps if any
                long erSteps = config.getSteps().stream().filter(s -> !s.isValid()).count();
                if (erSteps != 0) {
                    FieldError error = new FieldError("config", "steps", "may not be empty");
                    bindingResult.addError(error);
                }
                return "ca/create";
            }
            // clear splits for REST type if any
            if (config.getType().equals("REST")) {
                config.getSplits().clear();
            }
            // - should not be steps with empty fields
            if (config.getSteps().stream().filter(s -> !s.isValid()).count() != 0 || config.getSteps().size() == 0) {
                FieldError error = new FieldError("config", "steps", "may not be empty");
                bindingResult.addError(error);
            }
            // RESTDECISION validation
            // - should not be splits with empty fields
            if (config.getType().equals("RESTDECISION")
                    && (config.getSplits().stream().filter(s -> !s.isValid()).count() != 0 || config.getSplits().size() == 0)) {
                FieldError error = new FieldError("config", "splits", "may not be empty");
                bindingResult.addError(error);
            }
            if (bindingResult.hasErrors())
                return "ca/create";

            //config.getSplits().add(new CustomActivitySplit("LABEL FOR PATH 1", "path_1_key"));
            //config.getSplits().add(new CustomActivitySplit("LABEL FOR PATH 2", "path_2_key"));
            CustomActivityConfig res = customActivityService.createConfig(config);
        }
        // cancel
        else if (action.equals("cancel")) {
            //handle renew
        }
        return "redirect:/ca/list";
    }

    /**
     * delete config using jquery
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/delete/{id}")
    public ResponseEntity deleteConfig(@PathVariable String id) {
        return new ResponseEntity(customActivityService.deleteConfigById(id), HttpStatus.OK);
    }

}