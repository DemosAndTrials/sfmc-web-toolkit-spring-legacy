package sfmc.controller.api;

import com.exacttarget.fuelsdk.ETDataExtension;
import com.exacttarget.fuelsdk.ETDataExtensionRow;
import com.exacttarget.fuelsdk.ETFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sfmc.service.ApiService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * SDK Controller
 */
@Controller
@RequestMapping("api/sdk")
public class SdkController {

    @Autowired
    ApiService apiService;

    ETDataExtension selectedDE;

    /**
     * Data extensions general page
     * - Folders and list of data extensions
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/de")
    public String deFolders(Model model) {
        // get folders
        List<ETFolder> folders = apiService.getDataExtensionFolders();
        model.addAttribute("data_folders", folders);
        // get data extensions
        ETFolder parent = folders.stream()
                .filter((animal) -> animal.getParentFolderKey() == null)
                .findFirst().orElse(new ETFolder());
        model.addAttribute("data_extensions", apiService.getDataExtensionsDetails(parent.getId()));
        model.addAttribute("selectedFolderId", parent.getId());
        model.addAttribute("parentFolderId", "");
        return "api/sdk/de-folders";
    }

    /**
     * Get data extensions for specific folder
     *
     * @param id    - category id
     * @param model
     * @return
     */
    @GetMapping(value = "/de-folder/{id}")
    public String deFolder(@PathVariable String id, Model model) {
        System.out.println("*** de folder: " + id + " ***");
        List<ETFolder> folders = apiService.getDataExtensionFolders();
        model.addAttribute("data_folders", folders);
        model.addAttribute("data_extensions", apiService.getDataExtensionsDetails(id));
        model.addAttribute("selectedFolderId", id);
        model.addAttribute("parentFolderId", "");
        return "api/sdk/de-folders";
    }

    /**
     * Create data extension folder
     *
     * @param folder
     * @return
     */
    @PostMapping(value = "/de-folder/", headers = "Accept=application/json")
    public ResponseEntity createFolder(@RequestBody ETFolder folder) {

        System.out.println("*** de folder: " + folder + " ***");
        ETFolder result = apiService.createDataExtensionFolder(folder);
        if (result != null) {
            String json = apiService.getDataExtensionFolderJson(result);//apiService.getDataExtensionFoldersJson();
            System.out.println("*** json: " + json + " ***");
            return new ResponseEntity(json, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * SDK Data Extensions page
     * TODO not in use
     *
     * @return
     */
    @GetMapping(value = "/de-list")
    public String deList(Model model) {
        model.addAttribute("data_extensions", apiService.getDataExtensionsDetails());
        return "api/sdk/de-list";
    }

    /**
     * TODO not in use
     *
     * @param folderId
     * @param model
     * @return
     */
    @GetMapping(value = "/de-list/{folderId}")
    public String deListByFolder(@PathVariable String folderId, Model model) {
        model.addAttribute("data_extensions", apiService.getDataExtensionsDetails(folderId));
        return "api/sdk/de-list";
    }

    /**
     * Data Extension details
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "/de-details/{id}")
    public String deDetails(@PathVariable String id, Model model) {
        System.out.println("*** de details: " + id + " ***");
        selectedDE = apiService.getDataExtensionDetails(id);
        model.addAttribute("ext", selectedDE);
        model.addAttribute("canDeleteRow", apiService.isPrimaryKeyExist(selectedDE));
        List<ETDataExtensionRow> rows = apiService.getDataExtensionRecordsByKey(selectedDE.getKey());
        model.addAttribute("records", rows);
        return "api/sdk/de-details";
    }

    /**
     * Create new row
     *
     * @param key
     * @param data
     * @return
     */
    @PostMapping(value = "/row-create/{key}", headers = "Accept=application/json")
    public ResponseEntity createRow(@PathVariable String key, @RequestBody Map<String, String> data) {
        // save
        ETDataExtensionRow row = new ETDataExtensionRow();
        row.setDataExtensionKey(key);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            row.setColumn(entry.getKey(), entry.getValue());
        }

        ETDataExtensionRow result = apiService.createDataExtensionRow(row);
        if (result != null) {
            return new ResponseEntity(data, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Update data extension row
     *
     * @param key
     * @param data
     * @return
     */
    @PostMapping(value = "/row-update/{key}", headers = "Accept=application/json")
    public ResponseEntity updateRow(@PathVariable String key, @RequestBody Map<String, String> data) {
        // save
        ETDataExtensionRow row = new ETDataExtensionRow();
        row.setDataExtensionKey(key);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            row.setColumn(entry.getKey(), entry.getValue());
        }

        ETDataExtensionRow result = apiService.updateDataExtensionRow(row);
        if (result != null) {
            return new ResponseEntity(data, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Delete data extension row
     *
     * @param key
     * @param data
     * @return
     */
    @PostMapping(value = "/row-delete/{key}")
    public ResponseEntity deleteRow(@PathVariable String key, @RequestBody Map<String, String> data) {
        // delete
        ETDataExtensionRow row = new ETDataExtensionRow();
        row.setDataExtensionKey(key);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            row.setColumn(entry.getKey(), entry.getValue());
        }
        if (apiService.deleteDataExtensionRow(selectedDE, row))
            return new ResponseEntity(true, HttpStatus.OK);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Create Data Extensions
     *
     * @return
     */
    @GetMapping(value = "/de-create/{id}")
    public String deCreate(@PathVariable String id, Model model) {
        model.addAttribute("folderId", id);
        ETDataExtension de = new ETDataExtension();
        de.addColumn("");
        //de.addColumn("", ETDataExtensionColumn.Type.EMAIL_ADDRESS);
        model.addAttribute("de", de);
        return "api/sdk/de-create";
    }

    /**
     * Create Data Extensions
     *
     * @return
     */
    @PostMapping(value = "/de-create/{folderId}")
    public String deCreate(@PathVariable String folderId, @RequestParam(required = false) String action, @Valid @ModelAttribute("de") ETDataExtension de, BindingResult bindingResult, Model model) {
        System.out.println("*** de folder: " + folderId + " ***");
        //de.setFolderId(Integer.parseInt(id));
        if (action.equals("save")) {
            if (de.getName().isEmpty()) {
                FieldError error = new FieldError("de", "Name", "may not be empty");
                bindingResult.addError(error);
            }

            if (bindingResult.hasErrors()) {
                return "api/sdk/de-create";
            } else {
                ETDataExtension result = apiService.createDataExtension(de);
                if (result != null)
                    return "redirect:/api/sdk/de-details/" + result.getId();
            }

        }
        return "api/sdk/de-create";
    }

    /**
     * Delete Data Extensions
     *
     * @param key
     * @return
     */
    @PostMapping(value = "/de-delete/{key}")
    public ResponseEntity deleteDe(@PathVariable String key) {
        // delete
        boolean res = apiService.deleteDataExtension(key);
        return new ResponseEntity(res, HttpStatus.OK);
    }


    /**
     * TODO FOR TESTING PURPOSE ONLY
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/test")
    public String deTest(Model model) {
        System.out.println("*** test ***");
        return "api/poc/test";
    }

    @GetMapping(value = "/filter")
    public String deTest1(Model model) {
        System.out.println("*** test filters ***");
        List<ETDataExtensionRow> rows = apiService.testFilters();
        return "api/poc/test";
    }

    @GetMapping(value = "/filter2")
    public String deTest2(Model model) {
        System.out.println("*** test filters2 ***");
        List<ETDataExtensionRow> rows = apiService.testFilters1();
        return "api/poc/test";
    }
}
