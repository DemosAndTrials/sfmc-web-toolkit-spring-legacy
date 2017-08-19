package sfdc.mc.repository;

import sfdc.mc.util.ConfigConstants;

import javax.json.Json;
import javax.json.JsonObject;

public class CustomActivityRepositoryImpl implements CustomActivityRepositoryCustom {

    /**
     * Get default content for config.json
     * Every custom Journey Builder activity must include a config.json in the root of its endpoint.
     * https://developer.salesforce.com/docs/atlas.en-us.noversion.mc-app-development.meta/mc-app-development/creating-activities.htm
     * TODO refactoring
     *
     * @return
     */
    @Override
    public String getRestConfig() {
        try {

            // get heroku config variables
            String caName = System.getenv(ConfigConstants.CA_NAME) != null ? System.getenv(ConfigConstants.CA_NAME) : "Demo Custom Activity";
            String caEditUrl = System.getenv(ConfigConstants.CA_EDIT_URL) != null ? System.getenv(ConfigConstants.CA_EDIT_URL) : "edit.html";
            String caImage15 = System.getenv(ConfigConstants.CA_IMG_15) != null ? System.getenv(ConfigConstants.CA_IMG_15) : "https://s25.postimg.org/hxtt8fj2n/angry-bird-icon-15.png";
            String caImage40 = System.getenv(ConfigConstants.CA_IMG_40) != null ? System.getenv(ConfigConstants.CA_IMG_40) : "https://s25.postimg.org/u9wplx6xb/angry-bird-icon-40.png";
            String caNumSteps = System.getenv(ConfigConstants.CA_NUM_STEPS) != null ? System.getenv(ConfigConstants.CA_NUM_STEPS) : "1";
            String caEditHeight = System.getenv(ConfigConstants.CA_EDIT_HEIGHT) != null ? System.getenv(ConfigConstants.CA_EDIT_HEIGHT) : "600";
            String caEditWidth = System.getenv(ConfigConstants.CA_EDIT_WIDTH) != null ? System.getenv(ConfigConstants.CA_EDIT_WIDTH) : "800";
            String caEndPointUrl = System.getenv(ConfigConstants.CA_ENDPOINT_URL) != null ? System.getenv(ConfigConstants.CA_ENDPOINT_URL) : "index.html";
            String caKey = System.getenv(ConfigConstants.CA_KEY) != null ? System.getenv(ConfigConstants.CA_KEY) : "9ccde4db-7cc2-4aa9-9227-5bb10673ac6d";

            // create json
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
                                    .add("timeout", 10000)))
                    // configurationArguments - Contains information that relates to the configuration of the instance of this activity.
                    // All configuration arguments except publish are optional.
                    .add("configurationArguments", Json.createObjectBuilder()
                            .add("applicationExtensionKey", caKey)
                            .add("save", Json.createObjectBuilder()
                                    .add("url", caEndPointUrl + "/save"))
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
                                    .add("height", Integer.valueOf(caEditHeight))
                                    .add("width", Integer.valueOf(caEditWidth))
                                    .add("url", caEditUrl)))
                    .build();

            String result = value.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // something wrong
        return "";
    }

    /**
     * Get default content for config.json
     * Every custom Journey Builder activity must include a config.json in the root of its endpoint.
     * https://developer.salesforce.com/docs/atlas.en-us.noversion.mc-app-development.meta/mc-app-development/creating-activities.htm
     * TODO refactoring
     *
     * @return
     */
    @Override
    public String getSplitConfig() {
        try {

            // get heroku config variables
            String caName = System.getenv(ConfigConstants.CA_NAME) != null ? System.getenv(ConfigConstants.CA_NAME) : "Demo Custom Split Activity";
            String caEditUrl = System.getenv(ConfigConstants.CA_EDIT_URL) != null ? System.getenv(ConfigConstants.CA_EDIT_URL) : "edit.html";
            String caImage15 = System.getenv(ConfigConstants.CA_IMG_15) != null ? System.getenv(ConfigConstants.CA_IMG_15) : "https://s25.postimg.org/7ptejywr3/angry-bird-yellow-icon-15.png";
            String caImage40 = System.getenv(ConfigConstants.CA_IMG_40) != null ? System.getenv(ConfigConstants.CA_IMG_40) : "https://s25.postimg.org/5mizcawy7/angry-bird-yellow-icon-40.png";
            String caNumSteps = System.getenv(ConfigConstants.CA_NUM_STEPS) != null ? System.getenv(ConfigConstants.CA_NUM_STEPS) : "1";
            String caEditHeight = System.getenv(ConfigConstants.CA_EDIT_HEIGHT) != null ? System.getenv(ConfigConstants.CA_EDIT_HEIGHT) : "600";
            String caEditWidth = System.getenv(ConfigConstants.CA_EDIT_WIDTH) != null ? System.getenv(ConfigConstants.CA_EDIT_WIDTH) : "800";
            String caEndPointUrl = System.getenv(ConfigConstants.CA_ENDPOINT_URL) != null ? System.getenv(ConfigConstants.CA_ENDPOINT_URL) : "index.html";
            String caKey = System.getenv(ConfigConstants.CA_KEY) != null ? System.getenv(ConfigConstants.CA_KEY) : "f763f8e7-1c1e-481e-817d-ae1c4761ec62";

            // create json
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
                    .add("type", "RESTDECISION")
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
                                    .add("timeout", 10000)))
                    // configurationArguments - Contains information that relates to the configuration of the instance of this activity.
                    // All configuration arguments except publish are optional.
                    .add("configurationArguments", Json.createObjectBuilder()
                            .add("applicationExtensionKey", caKey)
                            .add("save", Json.createObjectBuilder()
                                    .add("url", caEndPointUrl + "/save"))
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
                            /*.add(Json.createObjectBuilder()
                                    .add("label", "Step 2")
                                    .add("key", "step2"))*/)
                    // outcomes - Contains multiple possible outcomes to follow once the activity has executed
                    // Each default outcome must contain an arguments object, containing a branchResult field.
                    // Journey Builder will expect the response of the custom activity's Execute REST call
                    // to contain an object { branchResult: <value> }, where <value> matches the branchResult of one of the activity's outcomes.
                    .add("outcomes", Json.createArrayBuilder()
                            .add(Json.createObjectBuilder()
                                    .add("arguments", Json.createObjectBuilder()
                                            .add("branchResult", "path_1_key"))
                                    .add("metaData", Json.createObjectBuilder()
                                            .add("label", "LABEL FOR PATH 1")))
                            .add(Json.createObjectBuilder()
                                    .add("arguments", Json.createObjectBuilder()
                                            .add("branchResult", "path_2_key"))
                                    .add("metaData", Json.createObjectBuilder()
                                            .add("label", "LABEL FOR PATH 2"))))

                    // userInterfaces - Contains endpoints and UI configurations for the user interfaces for the activity
                    // (configuration modal, running mode hover, running mode details modal).
                    .add("userInterfaces", Json.createObjectBuilder()
                            .add("configModal", Json.createObjectBuilder()
                                    .add("height", Integer.valueOf(caEditHeight))
                                    .add("width", Integer.valueOf(caEditWidth))
                                    .add("url", caEditUrl))
                            .add("runningModal", Json.createObjectBuilder()
                                    .add("url", "runningModal.html"))
                            .add("runningHover", Json.createObjectBuilder()
                                    .add("url", "runningHover.html")))
                    .build();

            String result = value.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // something wrong
        return "";
    }

    /**
     *
     * @return
     */
    @Override
    public String getSplitResult() {
        // TODO add decision logic
        JsonObject value = Json.createObjectBuilder()
                .add("branchResult", "path_2_key")
                .build();
        String result = value.toString();
        return result;
    }
}
