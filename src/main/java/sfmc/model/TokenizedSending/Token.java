package sfmc.model.TokenizedSending;

public class Token {

    private String tokenRequestId;
    private String token;
    private String subscriberKey;

    public String getTokenRequestId() {
        return tokenRequestId;
    }

    public void setTokenRequestId(String tokenRequestId) {
        this.tokenRequestId = tokenRequestId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSubscriberKey() {
        return subscriberKey;
    }

    public void setSubscriberKey(String subscriberKey) {
        this.subscriberKey = subscriberKey;
    }
}
