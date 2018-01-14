package sfmc.model.CustomActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.List;
import java.util.Map;

public class CustomActivityExecuteArgs {
    private String activityObjectID;
    private String journeyId;
    private String activityId;
    private String definitionInstanceId;
    private String activityInstanceId;
    private String keyValue;
    private String mode;
    private List<Map<String, String>> inArguments;

    public String getActivityObjectID() {
        return activityObjectID;
    }

    public void setActivityObjectID(String activityObjectID) {
        this.activityObjectID = activityObjectID;
    }

    public String getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(String journeyId) {
        this.journeyId = journeyId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getDefinitionInstanceId() {
        return definitionInstanceId;
    }

    public void setDefinitionInstanceId(String definitionInstanceId) {
        this.definitionInstanceId = definitionInstanceId;
    }

    public String getActivityInstanceId() {
        return activityInstanceId;
    }

    public void setActivityInstanceId(String activityInstanceId) {
        this.activityInstanceId = activityInstanceId;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<Map<String, String>> getInArguments() {
        return inArguments;
    }

    public void setInArguments(List<Map<String, String>> inArguments) {
        this.inArguments = inArguments;
    }

    @Override
    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(this);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
