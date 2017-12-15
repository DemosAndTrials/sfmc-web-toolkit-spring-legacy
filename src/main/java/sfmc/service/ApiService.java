package sfmc.service;

import com.exacttarget.fuelsdk.ETDataExtension;
import com.exacttarget.fuelsdk.ETDataExtensionRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sfmc.repository.FuelSDKRepository;

import java.util.List;

/**
 * API Service
 */
@Service
public class ApiService {

    @Autowired
    FuelSDKRepository sdkRepository;

    public List<ETDataExtensionRow> GetDataExtensionRecordsByKey(String key) {
        return sdkRepository.GetDataExtensionRecordsByKey(key);
    }

    public List<ETDataExtension> GetDataExtensionsDetails() {
        return sdkRepository.GetDataExtensionsDetails();
    }

    public ETDataExtension GetDataExtensionDetails(String id) {
       return sdkRepository.GetDataExtensionDetails(id);
    }

    public ETDataExtensionRow Create(ETDataExtensionRow row){
        return sdkRepository.CreateDataExtensionRow(row);
    }

    public void Delete(ETDataExtensionRow row){
        sdkRepository.DeleteDataExtensionRow(row);
    }
}
