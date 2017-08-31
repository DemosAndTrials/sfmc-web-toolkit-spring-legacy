package sfdc.mc.model;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Custom Activity Config object
 */
@Entity
public class CustomActivityConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer Id;

    @NotEmpty
    @Column(length = 100)
    String Key;

    @Column(length = 25)
    String Type;

    @NotEmpty
    @Column(length = 100)
    String Name;

    String Description;

    String SmallImageUrl;

    String BigImageUrl;

    @NotEmpty
    String EditUrl;

    @NotNull
    Integer EditHeight;

    @NotNull
    Integer EditWidth;

    @NotEmpty
    String EndpointUrl;

    @ElementCollection
    @CollectionTable(name = "CustomActivityStep", joinColumns = {@JoinColumn(name = "config_id")})
    private List<CustomActivityStep> Steps = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "CustomActivitySplit", joinColumns = {@JoinColumn(name = "config_id")})
    private List<CustomActivitySplit> Splits = new ArrayList<>();

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

    public List<CustomActivityStep> getSteps() {
        return Steps;
    }

    public void setSteps(List<CustomActivityStep> steps) {
        Steps = steps;
    }

    public List<CustomActivitySplit> getSplits() {
        return Splits;
    }

    public void setSplits(List<CustomActivitySplit> splits) {
        Splits = splits;
    }

    public CustomActivityConfig() {
        this.setName("My Custom Activity");
        this.setKey(UUID.randomUUID().toString());
        this.setEditHeight(600);
        this.setEditWidth(800);
        this.getSteps().add(new CustomActivityStep("Step 1","step_1"));
    }

    @Override
    public String toString() {
        return "Config{" +
                "Id=" + Id +
                ", Key='" + Key + '\'' +
                ", Name='" + Name + '\'' +
                ", Type='" + Type + '\'' +
                ", Description='" + Description + '\'' +
                ", SmallImageUrl='" + SmallImageUrl + '\'' +
                ", BigImageUrl=" + BigImageUrl +
                ", NumSteps=" + Steps.size() +
                ", EditUrl='" + EditUrl + '\'' +
                ", EditHeight='" + EditHeight + '\'' +
                ", EditWidth='" + EditWidth + '\'' +
                ", EndpointUrl='" + EndpointUrl + '\'' +
                '}';
    }
}
