package sfdc.mc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sfdc.mc.model.ConfigType;
import sfdc.mc.model.CustomActivityConfig;
import sfdc.mc.repository.CustomActivityRepository;
import sfdc.mc.util.Helpers;

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
     * @param type
     * @return
     */
    public String executeActivity(String type) throws Exception {
        try {
            ConfigType cType = ConfigType.valueOf(type.toUpperCase());
            switch (cType) {
                case REST:
                    return "OK";
                case RESTDECISION:
                    return customActivityRepository.getSplitResult();
                default:
                    throw new Exception("Unknown type: " + type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("Unknown type: " + type);
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
        String endpoint = config.getEndpointUrl().trim();
        if (endpoint.endsWith("/"))
            endpoint = Helpers.removeLastChar(config.getEndpointUrl());
        config.setEndpointUrl(endpoint);
        CustomActivityConfig savedConfig = customActivityRepository.save(config);
        // update with id
        if (isNew) {
            Integer id = savedConfig.getId();
            savedConfig.setEndpointUrl(savedConfig.getEndpointUrl() + "/" + id.toString());
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
        return customActivityRepository.findOne(id);
    }

    /**
     * Delete config
     *
     * @param idStr
     * @return
     */
    public boolean deleteConfigById(String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            customActivityRepository.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
