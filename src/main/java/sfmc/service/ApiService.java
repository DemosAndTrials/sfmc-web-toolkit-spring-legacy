package sfmc.service;

import com.exacttarget.fuelsdk.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sfmc.model.Authentication.ApiIntegrationSet;
import sfmc.model.Authentication.User;
import sfmc.repository.ApiIntegrationSetRepository;
import sfmc.repository.FuelSDKRepository;
import sfmc.repository.UserRepository;

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

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Qualifier("apiIntegrationSetRepository")
    @Autowired
    private ApiIntegrationSetRepository apiIntegrationSetRepository;

    public List<ETFolder> getDataExtensionFolders() {
        initSDK();
        // TODO some subfolders retrieved with parent empty, bug?
        return sdkRepository.getFolders("dataextension");
    }

    private void initSDK() {
        if (!sdkRepository.isInitiated()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findByEmail(auth.getName());
            ApiIntegrationSet apiSet = apiIntegrationSetRepository.findByUserId(user.getId());
            if (apiSet != null) {
                sdkRepository.initSDKClient(apiSet.getClientId(), apiSet.getClientSecret());
                System.out.println("*** ClientId: " + apiSet.getClientId() + " ***");
                System.out.println("*** ClientSecret: " + apiSet.getClientSecret() + " ***");
            }
        }
    }

    public String getDataExtensionFoldersJson() {
        initSDK();
        //
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
        initSDK();
        //
        return sdkRepository.getDataExtensionRecordsByKey(key);
    }

    public List<ETDataExtension> getDataExtensionsDetails() {
        initSDK();
        //
        return sdkRepository.getDataExtensionsDetails();
    }

    public List<ETDataExtension> getDataExtensionsDetails(String folderId) {
        initSDK();
        //
        return sdkRepository.getDataExtensionsDetails(folderId);
    }

    public ETDataExtension getDataExtensionDetails(String id) {
        initSDK();
        //
        return sdkRepository.getDataExtensionById(id);
    }

    public Boolean isPrimaryKeyExist(ETDataExtension de) {
        ETDataExtensionColumn res = de.getColumns().stream().filter((col) -> col.getIsPrimaryKey() == true).findFirst().orElse(null);
        return res != null;
    }

    public boolean deleteDataExtension(String key) {
        initSDK();
        //
        return sdkRepository.deleteDataExtension(key);
    }

    public ETDataExtensionRow createDataExtensionRow(ETDataExtensionRow row) {
        initSDK();
        //
        return sdkRepository.createDataExtensionRow(row);
    }

    public ETDataExtensionRow updateDataExtensionRow(ETDataExtensionRow row) {
        initSDK();
        //
        return sdkRepository.updateDataExtensionRow(row);
    }

    public boolean deleteDataExtensionRow(ETDataExtension de, ETDataExtensionRow row) {
        initSDK();
        //
        return sdkRepository.deleteDataExtensionRow(de, row);
    }

    public ETFolder createDataExtensionFolder(ETFolder folder) {
        initSDK();
        //
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

    public ETDataExtension createDataExtension(ETDataExtension de) {
        initSDK();
        //
        return sdkRepository.createDataExtension(de);
    }

    public List<ETDataExtensionRow> testFilters() {
        initSDK();
        //
        try {
            return sdkRepository.testFilters();
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ETDataExtensionRow> testFilters1() {
        initSDK();
        //
        try {
            return sdkRepository.testFilters1();
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
        return null;
    }
}
