package sfdc.mc.model;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.Embeddable;

/**
 * Custom Activity Split object
 */
@Embeddable
public class CustomActivitySplit {

    @NotEmpty
    String Label;

    @NotEmpty
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

    public String toString() {
        StringBuffer aBuffer = new StringBuffer("CustomActivitySplit ");
        aBuffer.append(" label: ");
        aBuffer.append(Label);
        aBuffer.append(" vlaue: ");
        aBuffer.append(Value);
        return aBuffer.toString();
    }
}
