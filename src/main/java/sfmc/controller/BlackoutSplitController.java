package sfmc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sfmc.model.CustomActivityExecuteArgs;
import sfmc.service.BlackoutService;
import javax.json.Json;
import javax.json.JsonObject;

/**
 * Blackout Split Controller
 */
@Controller
@RequestMapping("/bll")
public class BlackoutSplitController {

    @Autowired
    BlackoutService blackoutService;

    /**
     * Creation of config.json
     * Every custom Journey Builder activity must include a config.json in the root of its endpoint.
     * https://developer.salesforce.com/docs/atlas.en-us.noversion.mc-app-development.meta/mc-app-development/creating-activities.htm
     *
     * @return
     */
    @RequestMapping(value = "config.json")
    public ResponseEntity getConfig() {
        try {
            ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
            String host = builder.replacePath("").build().toUriString() + "/bll";

            // create json
            JsonObject value = Json.createObjectBuilder()
                    .add("workflowApiVersion", "1.1")
                    // metaData - Object that provides meta-information about this custom activity to Journey Builder.
                    // Third party sources may pass any additional properties as desired. This object contains UI-only data.
                    .add("metaData", Json.createObjectBuilder()
                            .add("icon", "https://s25.postimg.org/ow9gi9l5b/angry-bird-blue-icon-40.png")
                            .add("iconSmall", "https://s25.postimg.org/oks05i4pb/angry-bird-blue-icon-15.png")
                            .add("category", "message")
                            .add("isConfigured", true))
                    // type - String property representing the type of activity. This value must include one of the Marketing Cloud-provided types
                    .add("type", "REST")
                    // lang - Used to define i18n (internationalization) strings, such as the name and description as used within the application.
                    .add("lang", Json.createObjectBuilder()
                            .add("en-US", Json.createObjectBuilder()
                                    .add("name", "Blackout Split")
                                    .add("description", "Blackout Split")))
                    // arguments - Contains information sent to the activity upon each execution.
                    .add("arguments", Json.createObjectBuilder()
                            .add("execute", Json.createObjectBuilder()
                                    .add("inArguments", Json.createArrayBuilder())
                                    .add("url", host + "/execute")
                                    .add("verb", "POST")
                                    .add("body", "")
                                    .add("header", "")
                                    .add("format", "json")
                                    .add("useJwt", false)
                                    .add("timeout", 10000)))
                    // configurationArguments - Contains information that relates to the configuration of the instance of this activity.
                    // All configuration arguments except publish are optional.
                    .add("configurationArguments", Json.createObjectBuilder()
                            .add("applicationExtensionKey", "423425bd-eb3a-4af2-8877-9bc0f842d27e")
                            .add("save", Json.createObjectBuilder()
                                    .add("url", host + "/save"))
                            .add("publish", Json.createObjectBuilder()
                                    .add("url", host + "/publish"))
                            .add("validate", Json.createObjectBuilder()
                                    .add("url", host + "/validate"))
                            .add("stop", Json.createObjectBuilder()
                                    .add("url", host + "/stop")))
                    // wizardSteps - Contains an array of objects that define the steps that the user may navigate through when configuring the custom activity.
                    // Each object should follow the format: { "label": "Step 1", "key": "step1", "active": true }
                    .add("wizardSteps", Json.createArrayBuilder()
                            .add(Json.createObjectBuilder()
                                    .add("label", "Step 1")
                                    .add("key", "step_1")))
                    // outcomes - Contains multiple possible outcomes to follow once the activity has executed
                    // Each default outcome must contain an arguments object, containing a branchResult field.
                    // Journey Builder will expect the response of the custom activity's Execute REST call
                    // to contain an object { branchResult: <value> }, where <value> matches the branchResult of one of the activity's outcomes.
                    .add("outcomes", Json.createArrayBuilder()
                            .add(Json.createObjectBuilder()
                                    .add("arguments", Json.createObjectBuilder()
                                            .add("branchResult", "key_path_1"))
                                    .add("metaData", Json.createObjectBuilder()
                                            .add("label", "Path 1")))
                            .add(Json.createObjectBuilder()
                                    .add("arguments", Json.createObjectBuilder()
                                            .add("branchResult", "key_path_2"))
                                    .add("metaData", Json.createObjectBuilder()
                                            .add("label", "Path 2")))
                    )
                    // userInterfaces - Contains endpoints and UI configurations for the user interfaces for the activity
                    // (configuration modal, running mode hover, running mode details modal).
                    .add("userInterfaces", Json.createObjectBuilder()
                            .add("configModal", Json.createObjectBuilder()
                                    .add("height", 600)
                                    .add("width", 800)
                                    .add("url", host + "/ui/edit" + "?numSteps=" + 1))
                            .add("runningModal", Json.createObjectBuilder()
                                    .add("url", host + "/ui/modal"))
                            .add("runningHover", Json.createObjectBuilder()
                                    .add("url", host + "/ui/hover")))
                    .build();

            String result = value.toString();
            System.out.println("*** config.json: " + result);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * execute - The API calls this method for each contact processed by the journey.
     * <p>
     * example of request's body:
     * {
     * "inArguments":
     * [
     * {"source_de": "ad59f02e-f93b-e711-af11-78e3b50b7f0c"},
     * {"destination_de": "7c08723b-f93b-e711-af11-78e3b50b7f0c"},
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
     * @param args
     * @return
     */
    @RequestMapping(value = "execute", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity execute(@RequestBody CustomActivityExecuteArgs args) {
        System.out.println("*** execute activity with payload: " + args);
        try {
            String result = blackoutService.CheckBlackout(args) ? "key_path_2" : "key_path_2";//TODO ??
            System.out.println("*** execute activity with result: " + result);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity save(@RequestBody String json) {
        System.out.println("*** save activity: " + json);
        return new ResponseEntity("OK", HttpStatus.OK);
    }

    @RequestMapping(value = "publish", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity publish(@RequestBody String json) {
        System.out.println("*** publish activity: " + json);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "validate", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity validate(@RequestBody String json) {
        System.out.println("*** validate activity: " + json);
        // TODO validation
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "stop", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity stop(@RequestBody String json) {
        System.out.println("*** stop activity: " + json);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*
     * UI
     */

    /**
     * Custom Activity UI
     * Endpoint for the UI displayed to marketers while configuring this activity.
     */
    @RequestMapping(value = {"ui", "ui/edit", "ui/config"})
    public String editModal(@RequestParam(value = "numSteps", defaultValue = "0") String numSteps, Model model) {
        model.addAttribute("data_extensions", blackoutService.GetDataExtensionsDetails());
        System.out.println("*** UI Configuration with " + numSteps + " steps ***");
        return "ca/blackout/editModal";
    }

    @RequestMapping(value = {"ui", "ui/save"}, method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity saveModal(@RequestBody String json) {
        System.out.println("*** save activity: " + json);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * running hover
     */
    @RequestMapping(value = "ui/hover")
    public String runningHover() {
        System.out.println("*** running hover ***");
        return "ca/blackout/runningHover";
    }

    /**
     * running modal
     */
    @RequestMapping(value = "ui/modal")
    public String runningModal() {
        System.out.println("*** running modal ***");
        return "ca/blackout/runningModal";
    }
}
