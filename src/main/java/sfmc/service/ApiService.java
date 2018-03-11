package sfmc.service;

import com.exacttarget.fuelsdk.ETDataExtension;
import com.exacttarget.fuelsdk.ETDataExtensionRow;
import com.exacttarget.fuelsdk.ETFolder;
import com.exacttarget.fuelsdk.ETSdkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sfmc.repository.FuelSDKRepository;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.List;

/**
 * API Service
 */
@Service
public class ApiService {

    @Autowired
    FuelSDKRepository sdkRepository;

    public List<ETFolder> getDataExtensionFolders(){
        // TODO some subfolders retrieved with parent empty, bug?
        return sdkRepository.getFolders("dataextension");
    }

    public String getDataExtensionFoldersJson(){
        List<ETFolder> folders = sdkRepository.getFolders("dataextension");
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (ETFolder folder : folders) {
            arr.add(Json.createObjectBuilder()
                    .add("id", folder.getId())
                    .add("name", folder.getName()));
        }
        String result = arr.build().toString();
        return result;
    }

    public String getDataExtensionFolderJson(ETFolder folder) {
        JsonObjectBuilder obj = Json.createObjectBuilder()
                .add("id", folder.getId())
                .add("name", folder.getName());
        String result = obj.build().toString();
        return result;
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

    public ETFolder createDataExtensionFolder(ETFolder folder) {

        folder.setContentType("dataextension");
        folder.setIsActive(true);
        folder.setAllowChildren(true);
        folder.setIsEditable(true);
        // TODO: bug report , description is mandatory
        // TODO: no proper error message
        folder.setDescription(folder.getName());
        ETFolder result = sdkRepository.createDataExtensionFolder(folder);
        result.setName(folder.getName());
        return result;
    }

    public List<ETDataExtensionRow> testFilters() {
        try {
            return sdkRepository.testFilters();
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ETDataExtensionRow> testFilters1() {
        try {
            return sdkRepository.testFilters1();
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
        return null;
    }
}
