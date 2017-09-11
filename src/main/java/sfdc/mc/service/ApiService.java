package sfdc.mc.service;

import com.exacttarget.fuelsdk.ETDataExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sfdc.mc.repository.FuelSDKRepository;

import java.util.List;

/**
 * API Service
 */
@Service
public class ApiService {

    @Autowired
    FuelSDKRepository sdkRepository;

    public void GetDataExtensionData(String key) {
       sdkRepository.GetDataExtensionData(key);
    }

    public List<ETDataExtension> GetDataExtensionsDetails() {
        return sdkRepository.GetDataExtensionsDetails();
    }

    public ETDataExtension GetDataExtensionDetails(String key) {
       return sdkRepository.GetDataExtensionDetails(key);
    }
}
