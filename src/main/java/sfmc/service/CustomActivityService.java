package sfmc.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sfmc.model.CustomActivity.ConfigType;
import sfmc.model.CustomActivity.CustomActivityConfig;
import sfmc.repository.CustomActivityRepository;
import java.io.UnsupportedEncodingException;

/**
 * Custom Activity Service
 */
@Service
public class CustomActivityService {

    @Autowired
    CustomActivityRepository customActivityRepository;

    /**
     * @param id
     * @return
     * @throws Exception
     */
    public String getConfigByType(String id) throws Exception {
        try {

            CustomActivityConfig config = getConfigById(id);
            if (config != null) {

                ConfigType cType = ConfigType.valueOf(config.getType().toUpperCase());
                switch (cType) {
                    case REST:
                        return customActivityRepository.getRestConfig(config);
                    case RESTDECISION:
                        return customActivityRepository.getSplitConfig(config);
                    default:
                        throw new Exception("Unknown type: " + config.getType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("Unknown config: " + id);
    }

    /**
     * @param id
     * @return
     */
    public String executeActivity(String id, String body) throws Exception {
        try {
            CustomActivityConfig config = getConfigById(id);
            System.out.println("*** execute activity: " + config.toString());

            if(config.getUseJwt()){
                jwtDecode(body);
            }

            ConfigType cType = ConfigType.valueOf(config.getType().toUpperCase());
            switch (cType) {
                case REST:
                    return "{\"result\":\"OK\"}";
                case RESTDECISION:
                    return customActivityRepository.getSplitResult();
                default:
                    throw new Exception("Unknown type: " + config.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("Unknown config: " + id);
    }

    /**
     *
     * @param id
     * @param body
     */
    public void configurationActivity(String id, String body){
        try {
        CustomActivityConfig config = getConfigById(id);
        System.out.println("*** activity: " + config.toString());

        if(config.getUseJwt()){
            jwtDecode(body);
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Decode Jwt Token
     *
     * @return
     */
    private String jwtDecode(String token) {
        String key = System.getenv("JWT_SECRET");
        try {
            Algorithm algorithm = Algorithm.HMAC256(key);

            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);

            Base64 base64Url = new Base64(true);
            // JWT Header
            String header = new String(base64Url.decode(jwt.getHeader()));
            // JWT Body
            String body = new String(base64Url.decode(jwt.getPayload()));

            System.out.println("*** jwt header: " + header);
            System.out.println("*** jwt body: " + body);
            return body;
        } catch (UnsupportedEncodingException e) {
            //UTF-8 encoding not supported
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            //Invalid signature/claims
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save config into db
     *
     * @param config
     * @return config
     */
    public CustomActivityConfig createConfig(CustomActivityConfig config) {
        // additional validation
        // checks and corrects endpoint url
        Boolean isNew = config.getId() == null;

//        String endpoint = config.getEndpointUrl().trim();
//        if (endpoint.endsWith("/"))
//            endpoint = Helpers.removeLastChar(config.getEndpointUrl());
//        config.setEndpointUrl(endpoint);

        // add number of step to ui url
//        String editUrl = config.getEditUrl().trim();
//        int idx = editUrl.indexOf("?numSteps=");
//        if (idx != -1)
//            editUrl = editUrl.substring(0, idx);
//
//        if (!editUrl.contains("numSteps"))
//            editUrl += "?numSteps=" + config.getSteps().size();
//        config.setEditUrl(editUrl);

        // save
        CustomActivityConfig savedConfig = customActivityRepository.save(config);
        // update with id
        if (isNew) {
            Integer id = savedConfig.getId();
            savedConfig.setEndpointUrl(savedConfig.getEndpointUrl() + "/" + id.toString());
            if (savedConfig.getEditUrl().contains("ca/ui"))
                savedConfig.setEditUrl(savedConfig.getEndpointUrl() + "/ui/edit");

            savedConfig = customActivityRepository.save(savedConfig);
        }
        return savedConfig;
    }

    /**
     * Get all configs
     *
     * @return
     */
    public Iterable<CustomActivityConfig> getConfigs() {
        Iterable<CustomActivityConfig> list = customActivityRepository.findAll();
        return list;
    }

    /**
     * Get config by id
     *
     * @param idStr
     * @return
     */
    public CustomActivityConfig getConfigById(String idStr) {
        int id = Integer.parseInt(idStr);
        return customActivityRepository.findById(id).orElseGet(null);
    }

    /**
     * delete config
     *
     * @param idStr
     * @return
     */
    public boolean deleteConfigById(String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            customActivityRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
