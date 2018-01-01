package sfmc.controller;

import com.exacttarget.fuelsdk.ETDataExtension;
import com.exacttarget.fuelsdk.ETDataExtensionRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sfmc.model.CustomActivityExecuteArgs;
import sfmc.service.BlackoutService;

/**
 * Blackout Split Controller
 */
@Controller
@RequestMapping("/bll")
public class BlackoutSplitController {

    @Autowired
    BlackoutService blackoutService;

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
            String result = blackoutService.checkBlackout(args);
            System.out.println("*** execute activity completed with result: " + result);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
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
    @RequestMapping(value = "save", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity save(@RequestBody String json) {
        System.out.println("*** save activity: " + json);
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
    @RequestMapping(value = "publish", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity publish(@RequestBody String json) {
        System.out.println("*** publish activity: " + json);
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
    @RequestMapping(value = "validate", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity validate(@RequestBody String json) {
        System.out.println("*** validate activity: " + json);
        // TODO validation
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * stop - Notification is sent to this endpoint when a user stops any active version of the interaction.
     * The notification will be for that particular version’s activity (optional).
     *
     * @return
     */
    @RequestMapping(value = "stop", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity stop(@RequestBody String json) {
        System.out.println("*** stop activity: " + json);
        return new ResponseEntity(HttpStatus.OK);
    }

    /*
     * UI
     */

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
            String config = blackoutService.buildSplitConfig(host);
            System.out.println("*** config.json: " + config);
            return new ResponseEntity(config, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Custom Activity UI
     * Endpoint for the UI displayed to marketers while configuring this activity.
     */
    @RequestMapping(value = {"ui", "ui/edit", "ui/config"})
    public String editModal(@RequestParam(value = "numSteps", defaultValue = "0") String numSteps, Model model) {
        model.addAttribute("data_extensions", blackoutService.getDataExtensionsDetails());
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
     * TODO display info about current holiday if any
     */
    @RequestMapping(value = "ui/hover")
    public String runningHover(Model model) {
        System.out.println("*** running hover ***");
        // TODO remove hardcoded key
        String key = "1B5E2C69-7B08-4E0D-BF21-F7A6821C6179";
        ETDataExtensionRow holiday = blackoutService.getHolidayRow(key);
        model.addAttribute("holiday", holiday);
        return "ca/blackout/runningHover";
    }

    /**
     * running modal
     * TODO display info about current holiday if any
     */
    @RequestMapping(value = "ui/modal")
    public String runningModal() {
        System.out.println("*** running modal ***");
        return "ca/blackout/runningModal";
    }
}
