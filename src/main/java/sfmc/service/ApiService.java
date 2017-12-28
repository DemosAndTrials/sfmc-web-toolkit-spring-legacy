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

    public List<ETDataExtensionRow> getDataExtensionRecordsByKey(String key) {
        return sdkRepository.getDataExtensionRecordsByKey(key);
    }

    public List<ETDataExtension> getDataExtensionsDetails() {
        return sdkRepository.getDataExtensionsDetails();
    }

    public ETDataExtension getDataExtensionDetails(String id) {
        return sdkRepository.getDataExtensionDetails(id);
    }

    public ETDataExtensionRow create(ETDataExtensionRow row) {
        return sdkRepository.createDataExtensionRow(row);
    }

    public ETDataExtensionRow update(ETDataExtension de, ETDataExtensionRow row) {
        return sdkRepository.updateDataExtensionRow(de, row);
    }

    public boolean delete(ETDataExtension de, ETDataExtensionRow row) {
        return sdkRepository.deleteDataExtensionRow(de, row);
    }
}
