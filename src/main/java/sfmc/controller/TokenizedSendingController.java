package sfmc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sfmc.model.TokenizedSending.Token;
import sfmc.model.TokenizedSending.TokensResolveInput;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

@Controller
@RequestMapping("/tokens")
public class TokenizedSendingController {

    /**
     * Resolve Token
     * https://help.marketingcloud.com/en/documentation/marketing_cloud/marketing_cloud_security_and_encryption_products/tokenized_sending/creating_your_resolve_token_api___resolve_token_api_specification/
     *
     * @return
     */
    @RequestMapping(value = "/resolve", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity resolveTokens(@RequestBody TokensResolveInput input) {
        System.out.println("*** input: " + input);
        String result = createTokens(input);
        System.out.println("*** result: " + result);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Get Token
     * https://help.marketingcloud.com/en/documentation/marketing_cloud/marketing_cloud_security_and_encryption_products/tokenized_sending/creating_your_get_token_api___get_token_api_specification/
     *
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity getTokens(@RequestBody String input) {
        System.out.println("*** input: " + input);
        String result = "";
        System.out.println("*** result: " + result);
        return new ResponseEntity(result, HttpStatus.OK);
    }


    private String createTokens(TokensResolveInput input) {
        // create json
        JsonObject value = Json.createObjectBuilder()
                .add("resolvedTokens", createResolvedTokens(input.getTokens()))
                .build();
        return value.toString();
    }


    private JsonArrayBuilder createResolvedTokens(List<Token> tokens) {
        javax.json.JsonArrayBuilder arr = Json.createArrayBuilder();
        for (Token token : tokens) {
            //
            arr.add(Json.createObjectBuilder()
                    .add("tokenRequestId", token.getTokenRequestId())
                    .add("tokenValue", resolveToken(token.getToken())));
        }
        return arr;
    }

    private String resolveToken(String token) {
        // TODO resolve token logic here.....
        if(token.equals("t1234@example.com"))
            return "user1@exacttarget.com";
        if(token.equals("t5678@example.com"))
            return "user2@gmail.com";
        return token;
    }
}
