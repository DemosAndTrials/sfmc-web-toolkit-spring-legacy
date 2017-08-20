package sfdc.mc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sfdc.mc.model.ConfigType;
import sfdc.mc.model.CustomActivityConfig;
import sfdc.mc.repository.CustomActivityRepository;

import java.util.List;

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
                        return customActivityRepository.getRestConfig();
                    case RESTDECISION:
                        return customActivityRepository.getSplitConfig();
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
        CustomActivityConfig savedConfig = customActivityRepository.save(config);
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

}
