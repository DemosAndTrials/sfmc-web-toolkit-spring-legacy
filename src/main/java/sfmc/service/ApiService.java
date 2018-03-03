package sfmc.service;

import com.exacttarget.fuelsdk.ETDataExtension;
import com.exacttarget.fuelsdk.ETDataExtensionRow;
import com.exacttarget.fuelsdk.ETFolder;
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

    public List<ETFolder> getDataExtensionFolders(){
        return sdkRepository.getFolders("dataextension");
    }

    public List<ETDataExtensionRow> getDataExtensionRecordsByKey(String key) {
        return sdkRepository.getDataExtensionRecordsByKey(key);
    }

    public List<ETDataExtension> getDataExtensionsDetails() {
        return sdkRepository.getDataExtensionsDetails();
    }

    public List<ETDataExtension> getDataExtensionsDetails(String folderId) {
        return sdkRepository.getDataExtensionsDetails(folderId);
    }

    public ETDataExtension getDataExtensionDetails(String id) {
        return sdkRepository.getDataExtensionById(id);
    }

    public boolean deleteDataExtension(String key) {

        return sdkRepository.deleteDataExtension(key);
    }

    public ETDataExtensionRow createDataExtensionRow(ETDataExtensionRow row) {
        return sdkRepository.createDataExtensionRow(row);
    }

    public ETDataExtensionRow updateDataExtensionRow(ETDataExtensionRow row) {
        return sdkRepository.updateDataExtensionRow(row);
    }

    public boolean deleteDataExtensionRow(ETDataExtension de, ETDataExtensionRow row) {
        return sdkRepository.deleteDataExtensionRow(de, row);
    }
}
