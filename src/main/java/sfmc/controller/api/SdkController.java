package sfmc.controller.api;

import com.exacttarget.fuelsdk.ETDataExtension;
import com.exacttarget.fuelsdk.ETDataExtensionRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sfmc.service.ApiService;
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
     * SDK Data Extensions page
     *
     * @return
     */
    @GetMapping(value = "/de-list")
    public String deList(Model model) {
        model.addAttribute("data_extensions", apiService.getDataExtensionsDetails());
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
        List<ETDataExtensionRow> rows = apiService.getDataExtensionRecordsByKey(selectedDE.getKey());
        model.addAttribute("records", rows);
        return "api/sdk/de-details";
    }

    /**
     * create new record
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

        ETDataExtensionRow result = apiService.create(row);
        if (result != null) {
            return new ResponseEntity(data, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/row-update/{key}", headers = "Accept=application/json")
    public ResponseEntity updateRow(@PathVariable String key, @RequestBody Map<String, String> data) {
        // save
        ETDataExtensionRow row = new ETDataExtensionRow();
        row.setDataExtensionKey(key);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            row.setColumn(entry.getKey(), entry.getValue());
        }

        ETDataExtensionRow result = apiService.update(selectedDE, row);
        if (result != null) {
            return new ResponseEntity(data, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/row-delete/{key}")
    public ResponseEntity deleteRow(@PathVariable String key, @RequestBody Map<String, String> data) {
        // delete
        ETDataExtensionRow row = new ETDataExtensionRow();
        row.setDataExtensionKey(key);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            row.setColumn(entry.getKey(), entry.getValue());
        }
        boolean res = apiService.delete(selectedDE, row);
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
}
