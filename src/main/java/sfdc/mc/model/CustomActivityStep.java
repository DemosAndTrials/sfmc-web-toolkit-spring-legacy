package sfdc.mc.model;

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

    public String toString() {
        StringBuffer aBuffer = new StringBuffer("Step - ");
        aBuffer.append(" label: ");
        aBuffer.append(Label);
        aBuffer.append(" vlaue: ");
        aBuffer.append(Key);
        return aBuffer.toString();
    }
}
