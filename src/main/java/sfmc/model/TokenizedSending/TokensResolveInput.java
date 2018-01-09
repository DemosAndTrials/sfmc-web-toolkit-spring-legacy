package sfmc.model.TokenizedSending;

import java.util.List;

public class TokensResolveInput {
    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public String getSendKey() {
        return sendKey;
    }

    public void setSendKey(String sendKey) {
        this.sendKey = sendKey;
    }

    private List<Token> tokens;
    private String sendKey;
}
