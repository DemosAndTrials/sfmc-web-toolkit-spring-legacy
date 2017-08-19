package sfdc.mc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sfdc.mc.util.ConfigConstants;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Custom Activity Controller
 */
@Controller
@RequestMapping("/ca")
public class CustomActivityController {

    /**
     * Custom Activity UI
     * Endpoint for the UI displayed to marketers while configuring this activity.
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
     * @param json
     * @return
     */
    @RequestMapping(value = "/execute", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity execute(@RequestBody String json) {
        System.out.println("*** execute: " + json);
        return new ResponseEntity("OK", HttpStatus.OK);
    }

    /**
     * save - Notification is sent to this endpoint when a user saves the interaction (optional).
     * @param json
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity save(@RequestBody String json) {
        System.out.println("*** save: " + json);
        return new ResponseEntity("OK",HttpStatus.OK);
    }

    /**
     * publish - Notification is sent to this endpoint when a user publishes the interaction.
     * @return
     */
    @RequestMapping(value = "/publish", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity publish() {
        System.out.println("*** publish ***");
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * validate - Notification is sent to this endpoint when a user performs some validation
     * as part of the publishing process (optional).
     * @return
     */
    @RequestMapping(value = "/validate", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity validate() {
        System.out.println("*** validate ***");
        // TODO validation
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * stop - Notification is sent to this endpoint when a user stops any active version of the interaction.
     * The notification will be for that particular version’s activity (optional).
     * @return
     */
    @RequestMapping(value = "/stop", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity stop() {
        System.out.println("*** stop ***");
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Creation of config.json
     * Every custom Journey Builder activity must include a config.json in the root of its endpoint.
     * https://developer.salesforce.com/docs/atlas.en-us.noversion.mc-app-development.meta/mc-app-development/creating-activities.htm
     * @return
     */
    @RequestMapping(value = "/config.json")
    public ResponseEntity getConfig() {

        String caName = System.getenv(ConfigConstants.CA_NAME) != null ? System.getenv(ConfigConstants.CA_NAME) : "Demo Custom Activity";
        String caEditUrl = System.getenv(ConfigConstants.CA_EDIT_URL) != null ? System.getenv(ConfigConstants.CA_EDIT_URL) : "edit.html";
        String caImage15 = System.getenv(ConfigConstants.CA_IMG_15) != null ? System.getenv(ConfigConstants.CA_IMG_15) : "https://s25.postimg.org/hxtt8fj2n/angry-bird-icon-15.png";
        String caImage40 = System.getenv(ConfigConstants.CA_IMG_40) != null ? System.getenv(ConfigConstants.CA_IMG_40) : "https://s25.postimg.org/u9wplx6xb/angry-bird-icon-40.png";
        String caNumSteps = System.getenv(ConfigConstants.CA_NUM_STEPS) != null ? System.getenv(ConfigConstants.CA_NUM_STEPS) : "1";
        String caEditHeight = System.getenv(ConfigConstants.CA_EDIT_HEIGHT) != null ? System.getenv(ConfigConstants.CA_EDIT_HEIGHT) : "600";
        String caEditWidth = System.getenv(ConfigConstants.CA_EDIT_WIDTH) != null ? System.getenv(ConfigConstants.CA_EDIT_WIDTH) : "800";
        String caEndPointUrl = System.getenv(ConfigConstants.CA_ENDPOINT_URL) != null ? System.getenv(ConfigConstants.CA_ENDPOINT_URL) : "index.html";
        String caKey = System.getenv(ConfigConstants.CA_KEY) != null ? System.getenv(ConfigConstants.CA_KEY) : "9ccde4db-7cc2-4aa9-9227-5bb10673ac6d";


        JsonObject value = Json.createObjectBuilder()
                .add("workflowApiVersion", "1.1")
                // metaData - Object that provides meta-information about this custom activity to Journey Builder.
                // Third party sources may pass any additional properties as desired. This object contains UI-only data.
                .add("metaData", Json.createObjectBuilder()
                        .add("icon", caImage40)
                        .add("iconSmall", caImage15)
                        .add("category", "message")
                        .add("isConfigured", true))
                // type - String property representing the type of activity. This value must include one of the Marketing Cloud-provided types
                .add("type", "REST")
                // lang - Used to define i18n (internationalization) strings, such as the name and description as used within the application.
                .add("lang", Json.createObjectBuilder()
                        .add("en-US", Json.createObjectBuilder()
                                .add("name", caName)
                                .add("description", caName)))
                // arguments - Contains information sent to the activity upon each execution.
                .add("arguments", Json.createObjectBuilder()
                        .add("execute", Json.createObjectBuilder()
                                .add("inArguments", Json.createArrayBuilder())
                                .add("url", caEditUrl)
                                .add("verb", "POST")
                                .add("body", "")
                                .add("header", "")
                                .add("format", "json")
                                .add("useJwt", false)
                                .add("timeout",10000)))
                // configurationArguments - Contains information that relates to the configuration of the instance of this activity.
                // All configuration arguments except publish are optional.
                .add("configurationArguments", Json.createObjectBuilder()
                        .add("applicationExtensionKey", caKey)
                        .add("save", Json.createObjectBuilder()
                                .add("url",  caEndPointUrl + "/save"))
                        .add("publish", Json.createObjectBuilder()
                                .add("url", caEndPointUrl + "/publish"))
                        .add("validate", Json.createObjectBuilder()
                                .add("url", caEndPointUrl + "/validate")))
                // wizardSteps - Contains an array of objects that define the steps that the user may navigate through when configuring the custom activity.
                // Each object should follow the format: { "label": "Step 1", "key": "step1", "active": true }
                .add("wizardSteps", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("label", "Step 1")
                                .add("key", "step1"))
                        .add(Json.createObjectBuilder()
                                .add("label", "Step 2")
                                .add("key", "step2")))
                // userInterfaces - Contains endpoints and UI configurations for the user interfaces for the activity
                // (configuration modal, running mode hover, running mode details modal).
                .add("userInterfaces", Json.createObjectBuilder()
                        .add("configModal", Json.createObjectBuilder()
                                .add("height",Integer.valueOf(caEditHeight))
                                .add("width",Integer.valueOf(caEditWidth))
                                .add("url", caEditUrl)))
                .build();
        String result = value.toString();
        System.out.println("*** config.json: " + result);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}