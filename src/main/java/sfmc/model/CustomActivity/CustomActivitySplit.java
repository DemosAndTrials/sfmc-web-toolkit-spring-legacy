package sfmc.model.CustomActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Custom Activity Split object
 */
@Embeddable
public class CustomActivitySplit {

    @Column(length = 50)
    String Label;

    @Column(length = 50)
    String Value;

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public CustomActivitySplit() {
    }

    public CustomActivitySplit(String label, String value) {
        this.Label = label;
        this.Value = value;
    }

    public boolean isValid(){
        if(Label.isEmpty() || Value.isEmpty())
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
