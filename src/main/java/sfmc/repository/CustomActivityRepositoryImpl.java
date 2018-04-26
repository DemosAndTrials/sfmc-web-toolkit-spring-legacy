package sfmc.repository;

import sfmc.model.CustomActivity.CustomActivityConfig;
import sfmc.model.CustomActivity.CustomActivitySplit;
import sfmc.model.CustomActivity.CustomActivityStep;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
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
    public String getRestConfig(CustomActivityConfig config) {
        try {
            // create json
            JsonObject value = Json.createObjectBuilder()
                    .add("workflowApiVersion", "1.1")
                    // metaData - Object that provides meta-information about this custom activity to Journey Builder.
                    // Third party sources may pass any additional properties as desired. This object contains UI-only data.
                    .add("metaData", Json.createObjectBuilder()
                            .add("icon", config.getBigImageUrl())
                            .add("iconSmall", config.getSmallImageUrl())
                            .add("category", "message")
                            .add("isConfigured", config.getIsConfigured())
                            .add("configOnDrop", config.getConfigOnDrop()))
                    // type - String property representing the type of activity. This value must include one of the Marketing Cloud-provided types
                    .add("type", "REST")
                    // lang - Used to define i18n (internationalization) strings, such as the name and description as used within the application.
                    .add("lang", Json.createObjectBuilder()
                            .add("en-US", Json.createObjectBuilder()
                                    .add("name", config.getName())
                                    .add("description", config.getDescription())))
                    // arguments - Contains information sent to the activity upon each execution.
                    .add("arguments", Json.createObjectBuilder()
                            .add("execute", Json.createObjectBuilder()
                                    .add("inArguments", Json.createArrayBuilder())
                                    .add("url", config.getEndpointUrl() + "/execute")
                                    .add("verb", "POST")
                                    .add("body", "")
                                    .add("header", "")
                                    .add("format", "json")
                                    .add("useJwt", config.getUseJwt())
                                    .add("timeout", 10000)))
                    // configurationArguments - Contains information that relates to the configuration of the instance of this activity.
                    // All configuration arguments except publish are optional.
                    .add("configurationArguments", Json.createObjectBuilder()
                            .add("applicationExtensionKey", config.getKey())
                            .add("save", Json.createObjectBuilder()
                                    .add("url", config.getEndpointUrl() + "/save")
                                    .add("useJwt", config.getUseJwt()))
                            .add("publish", Json.createObjectBuilder()
                                    .add("url", config.getEndpointUrl() + "/publish")
                                    .add("useJwt", config.getUseJwt()))
                            .add("validate", Json.createObjectBuilder()
                                    .add("url", config.getEndpointUrl() + "/validate")
                                    .add("useJwt", config.getUseJwt()))
                            .add("stop", Json.createObjectBuilder()
                                    .add("url", config.getEndpointUrl() + "/stop")
                                    .add("useJwt", config.getUseJwt())))
                    // wizardSteps - Contains an array of objects that define the steps that the user may navigate through when configuring the custom activity.
                    // Each object should follow the format: { "label": "Step 1", "key": "step1", "active": true }
                    .add("wizardSteps", createStepsJsonNode(config))
                    // userInterfaces - Contains endpoints and UI configurations for the user interfaces for the activity
                    // (configuration modal, running mode hover, running mode details modal).
                    .add("userInterfaces", Json.createObjectBuilder()
                            .add("configModal", Json.createObjectBuilder()
                                    .add("height", config.getEditHeight())
                                    .add("width", config.getEditWidth())
                                    .add("url", config.getEditUrl() + "?numSteps=" + config.getSteps().size()))
                            .add("runningModal", Json.createObjectBuilder()
                                    .add("url", config.getEndpointUrl() + "/ui/modal"))
                            .add("runningHover", Json.createObjectBuilder()
                                    .add("url", config.getEndpointUrl() + "/ui/hover")))
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
     * TODO refactoring, remove hardcoded modals url
     *
     * @return
     */
    @Override
    public String getSplitConfig(CustomActivityConfig config) {
        try {
            // create json
            JsonObject value = Json.createObjectBuilder()
                    .add("workflowApiVersion", "1.1")
                    // metaData - Object that provides meta-information about this custom activity to Journey Builder.
                    // Third party sources may pass any additional properties as desired. This object contains UI-only data.
                    .add("metaData", Json.createObjectBuilder()
                            .add("icon", config.getBigImageUrl())
                            .add("iconSmall", config.getSmallImageUrl())
                            .add("category", "message")
                            .add("isConfigured", config.getIsConfigured())
                            .add("configOnDrop", config.getConfigOnDrop()))
                    // type - String property representing the type of activity. This value must include one of the Marketing Cloud-provided types
                    .add("type", "RESTDECISION")
                    // lang - Used to define i18n (internationalization) strings, such as the name and description as used within the application.
                    .add("lang", Json.createObjectBuilder()
                            .add("en-US", Json.createObjectBuilder()
                                    .add("name", config.getName())
                                    .add("description", config.getDescription())))
                    // arguments - Contains information sent to the activity upon each execution.
                    .add("arguments", Json.createObjectBuilder()
                            .add("execute", Json.createObjectBuilder()
                                    .add("inArguments", Json.createArrayBuilder())
                                    .add("url", config.getEndpointUrl() + "/execute")
                                    .add("verb", "POST")
                                    .add("body", "")
                                    .add("header", "")
                                    .add("format", "json")
                                    .add("useJwt", config.getUseJwt())
                                    .add("timeout", 10000)))
                    // configurationArguments - Contains information that relates to the configuration of the instance of this activity.
                    // All configuration arguments except publish are optional.
                    .add("configurationArguments", Json.createObjectBuilder()
                            .add("applicationExtensionKey", config.getKey())
                            .add("save", Json.createObjectBuilder()
                                    .add("url", config.getEndpointUrl() + "/save")
                                    .add("useJwt", config.getUseJwt()))
                            .add("publish", Json.createObjectBuilder()
                                    .add("url", config.getEndpointUrl() + "/publish")
                                    .add("useJwt", config.getUseJwt()))
                            .add("validate", Json.createObjectBuilder()
                                    .add("url", config.getEndpointUrl() + "/validate")
                                    .add("useJwt", config.getUseJwt()))
                            .add("stop", Json.createObjectBuilder()
                                    .add("url", config.getEndpointUrl() + "/stop")
                                    .add("useJwt", config.getUseJwt())))
                    // wizardSteps - Contains an array of objects that define the steps that the user may navigate through when configuring the custom activity.
                    // Each object should follow the format: { "label": "Step 1", "key": "step1", "active": true }
                    .add("wizardSteps", createStepsJsonNode(config))
                    // outcomes - Contains multiple possible outcomes to follow once the activity has executed
                    // Each default outcome must contain an arguments object, containing a branchResult field.
                    // Journey Builder will expect the response of the custom activity's Execute REST call
                    // to contain an object { branchResult: <value> }, where <value> matches the branchResult of one of the activity's outcomes.
                    .add("outcomes", createSplitJsonNode(config))
                    // userInterfaces - Contains endpoints and UI configurations for the user interfaces for the activity
                    // (configuration modal, running mode hover, running mode details modal).
                    .add("userInterfaces", Json.createObjectBuilder()
                            .add("runningModal", Json.createObjectBuilder()
                                    .add("url", config.getEndpointUrl() + "/ui/modal"))
                            .add("runningHover", Json.createObjectBuilder()
                                    .add("url", config.getEndpointUrl() + "/ui/hover")))
                    .add("edit", Json.createObjectBuilder()
                            .add("url", config.getEditUrl() + "?numSteps=" + config.getSteps().size())
                            .add("height", config.getEditHeight())
                            .add("width", config.getEditWidth()))
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
     * create json array for wizard steps
     * TODO dynamic creation
     *
     * @param config
     * @return
     */
    private JsonArrayBuilder createStepsJsonNode(CustomActivityConfig config) {
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (CustomActivityStep step : config.getSteps()) {
            arr.add(Json.createObjectBuilder()
                    .add("label", step.getLabel())
                    .add("key", step.getKey()));
        }
        return arr;
    }

    /**
     * create json array for splits
     *
     * @param config
     * @return
     */
    private JsonArrayBuilder createSplitJsonNode(CustomActivityConfig config) {
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (CustomActivitySplit split : config.getSplits()) {
            arr.add(Json.createObjectBuilder()
                    .add("arguments", Json.createObjectBuilder()
                            .add("branchResult", split.getValue()))
                    .add("metaData", Json.createObjectBuilder()
                            .add("label", split.getLabel())));
        }
        return arr;
    }

    /**
     * TODO do some logic for split decision
     *
     * @return
     */
    @Override
    public String getSplitResult() {
        // TODO add decision logic
        JsonObject value = Json.createObjectBuilder()
                .add("branchResult", "key_path_2")
                .build();
        String result = value.toString();
        return result;
    }
}
