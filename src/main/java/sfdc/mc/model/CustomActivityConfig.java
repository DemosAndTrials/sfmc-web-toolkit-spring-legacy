package sfdc.mc.model;

import javax.persistence.*;

/**
 * Custom Activity Config object
 */
@Entity
@Table(schema = "sfmc")
public class CustomActivityConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer Id;

    @Column(length = 25)
    String Key;

    @Column(length = 25)
    String Type;

    @Column(length = 25)
    String Name;

    String Description;

    String SmallImageUrl;

    String BigImageUrl;

    Integer NumSteps;

    String EditUrl;

    Integer EditHeight;

    Integer EditWidth;

    String EndpointUrl;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSmallImageUrl() {
        return SmallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        SmallImageUrl = smallImageUrl;
    }

    public String getBigImageUrl() {
        return BigImageUrl;
    }

    public void setBigImageUrl(String bigImageUrl) {
        BigImageUrl = bigImageUrl;
    }

    public Integer getNumSteps() {
        return NumSteps;
    }

    public void setNumSteps(Integer numSteps) {
        NumSteps = numSteps;
    }

    public String getEditUrl() {
        return EditUrl;
    }

    public void setEditUrl(String editUrl) {
        EditUrl = editUrl;
    }

    public Integer getEditHeight() {
        return EditHeight;
    }

    public void setEditHeight(Integer editHeight) {
        EditHeight = editHeight;
    }

    public Integer getEditWidth() {
        return EditWidth;
    }

    public void setEditWidth(Integer editWidth) {
        EditWidth = editWidth;
    }

    public String getEndpointUrl() {
        return EndpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        EndpointUrl = endpointUrl;
    }

    @Override
    public String toString() {
        return "PHXClient{" +
                "Id=" + Id +
                ", Key='" + Key + '\'' +
                ", Name='" + Name + '\'' +
                ", Type='" + Type + '\'' +
                ", Description='" + Description + '\'' +
                ", SmallImageUrl='" + SmallImageUrl + '\'' +
                ", BigImageUrl=" + BigImageUrl +
                ", NumSteps=" + NumSteps +
                ", EditUrl='" + EditUrl + '\'' +
                ", EditHeight='" + EditHeight + '\'' +
                ", EditWidth='" + EditWidth + '\'' +
                ", EndpointUrl='" + EndpointUrl + '\'' +
                '}';
    }
}
