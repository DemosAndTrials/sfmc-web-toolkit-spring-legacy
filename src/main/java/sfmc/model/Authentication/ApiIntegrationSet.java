package sfmc.model.Authentication;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;

@Entity
public class ApiIntegrationSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="user_id")
    private int userId;

    @Column(name = "client_id")
    @NotEmpty(message = "*Please provide clientId")
    private String clientId;

    @Column(name = "client_secret")
    @NotEmpty(message = "*Please provide clientSecret")
    private String clientSecret;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
