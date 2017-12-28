package sfmc.service;

import com.exacttarget.fuelsdk.ETDataExtension;
import com.exacttarget.fuelsdk.ETDataExtensionRow;
import com.exacttarget.fuelsdk.ETSdkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sfmc.model.CustomActivityExecuteArgs;
import sfmc.repository.FuelSDKRepository;

import javax.json.Json;
import javax.json.JsonObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Blackout Custom Activity Service
 */
@Service
public class BlackoutService {

    @Autowired
    FuelSDKRepository sdkRepository;

    /**
     * @param args
     * @return
     */
    public String checkBlackout(CustomActivityExecuteArgs args) {
        // get local time
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Israel"));
        System.out.println("*** current time: " + df.format(date));

        // check if there is weekend or holiday
        String sourceKey = args.getInArguments().get(0).get("source_de");
        String destinationKey = args.getInArguments().get(1).get("destination_de");

        try {
            // check if today is holiday or weekend
            ETDataExtensionRow holiday = sdkRepository.getDataExtensionRecord(sourceKey, df.format(date));
            if (holiday != null) {
                // update wait attribute
                ETDataExtensionRow contact = sdkRepository.getDataExtensionRowByEmail(destinationKey, args.getKeyValue());
                if (contact != null) {
                    contact.setDataExtensionKey(destinationKey);
                    contact.setColumn("WaitDate", holiday.getColumn("EndDate"));
                    ETDataExtensionRow result = sdkRepository.updateDataExtensionRow(contact);
                    if (result != null)
                        System.out.println("*** updated contact: " + result);
                    else
                        System.out.println("*** updated contact failed ***");
                    return buildSplitResult("key_path_2");
                }
            }
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
        // update wait by attribute field
        return buildSplitResult("key_path_1");
    }

    public List<ETDataExtension> getDataExtensionsDetails() {
        return sdkRepository.getDataExtensionsDetails();
    }

    private String buildSplitResult(String path) {
        // TODO add decision logic
        JsonObject value = Json.createObjectBuilder()
                .add("branchResult", path)
                .build();
        String result = value.toString();
        return result;
    }

    public String buildSplitConfig(String host) {
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
                                        .add("label", "Path 2"))))
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

        return value.toString();
    }
}
