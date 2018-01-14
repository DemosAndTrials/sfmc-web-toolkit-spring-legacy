package sfmc.model.CustomActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Custom Activity Wizard Step object
 */
@Embeddable
public class CustomActivityStep {

    @Column(length = 50)
    String Label;

    @Column(length = 50)
    String Key;

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public CustomActivityStep() {
    }

    public CustomActivityStep(String label, String value) {
        this.Label = label;
        this.Key = value;
    }

    public boolean isValid(){
        if(Label.isEmpty() || Key.isEmpty())
            return false;
        return true;
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
