package sfdc.mc.model;

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

    public String toString() {
        StringBuffer aBuffer = new StringBuffer("CustomActivitySplit ");
        aBuffer.append(" label: ");
        aBuffer.append(Label);
        aBuffer.append(" vlaue: ");
        aBuffer.append(Value);
        return aBuffer.toString();
    }
}
