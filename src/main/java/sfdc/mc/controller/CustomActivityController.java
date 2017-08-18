package sfdc.mc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public static Logger logger = LoggerFactory.getLogger(CustomActivityController.class);

    /**
     * Custom Activity UI
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
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/execute", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity execute(@RequestBody String json) {
        if (logger.isDebugEnabled())
            logger.debug("json: " + json);
        return new ResponseEntity("OK", HttpStatus.OK);
    }

    /**
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity save(@RequestBody String json) {
        if (logger.isDebugEnabled())
            logger.debug("save:");
        return new ResponseEntity("OK",HttpStatus.OK);
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/publish", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity publish() {
        if (logger.isDebugEnabled())
            logger.debug("publish:");
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/validate", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity validate() {
        // TODO validation
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/stop", method = RequestMethod.POST, headers = "Accept=application/json")
    public String stop() {
        if (logger.isDebugEnabled())
            logger.debug("stop:");
        return "ca";
    }

    /**
     *
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
                .add("metaData", Json.createObjectBuilder()
                        .add("icon", caImage40)
                        .add("iconSmall", caImage15)
                        .add("category", "message")
                        .add("isConfigured", true))
                .add("type", "REST")
                .add("lang", Json.createObjectBuilder()
                        .add("en-US", Json.createObjectBuilder()
                                .add("name", caName)
                                .add("description", caName)))
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
                .add("configurationArguments", Json.createObjectBuilder()
                        .add("applicationExtensionKey", caKey)
                        .add("save", Json.createObjectBuilder()
                                .add("url",  caEndPointUrl + "/save"))
                        .add("publish", Json.createObjectBuilder()
                                .add("url", caEndPointUrl + "/publish"))
                        .add("validate", Json.createObjectBuilder()
                                .add("url", caEndPointUrl + "/validate")))
                .add("wizardSteps", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("label", "Step 1")
                                .add("key", "step1"))
                        .add(Json.createObjectBuilder()
                                .add("label", "Step 2")
                                .add("key", "step2")))
                .add("edit", Json.createObjectBuilder()
                        .add("url", caEditUrl)
                        .add("height",Integer.valueOf(caEditHeight))
                        .add("width", Integer.valueOf(caEditWidth)))
                .build();
        String result = value.toString();
        System.out.println("*** config.json: " + result);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}