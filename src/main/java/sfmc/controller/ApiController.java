package sfmc.controller;

import com.exacttarget.fuelsdk.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sfmc.service.ApiService;

import java.util.List;
import java.util.Map;

import static java.lang.System.out;

/**
 * API Controller
 */
@Controller
@RequestMapping("api")
public class ApiController {

    @Autowired
    ApiService apiService;

    /**
     * Index page - Getting Started
     *
     * @return
     */
    @GetMapping(value = {"/", "/index"})
    public String index() {
        apiService.GetDataExtensionsDetails();
        return "api/index";
    }

    /**
     * SDK index page
     *
     * @return
     */
    @GetMapping(value = "/sdk")
    public String sdk() {
        return "api/sdk";
    }

    /**
     * SDK Data Extensions page
     *
     * @return
     */
    @GetMapping(value = "/sdk/de-list")
    public String deList(Model model) {
        model.addAttribute("data_extensions", apiService.GetDataExtensionsDetails());
        return "api/sdk/de-list";
    }

    /**
     * Data Extension details
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "/sdk/de-details/{id}")
    public String deDetails(@PathVariable String id, Model model) {
        System.out.println("*** de details: " + id + " ***");
        ETDataExtension ext = apiService.GetDataExtensionDetails(id);
        model.addAttribute("ext", ext);
        List<ETDataExtensionRow> rows = apiService.GetDataExtensionRecordsByKey(ext.getKey());
        model.addAttribute("records", rows);
        return "api/sdk/de-details";
    }

    /**
     * TODO FOR TESTING PURPOSE ONLY
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/sdk/test")
    public String deTest(Model model) {
        System.out.println("*** test ***");
        return "api/poc/test";
    }

    /**
     * Create new record
     * @param key
     * @param data
     * @return
     */
    @PostMapping(value = "/sdk/create/{key}", headers = "Accept=application/json")
    public ResponseEntity createRow(@PathVariable String key, @RequestBody Map<String, String> data) {
        // save
        ETDataExtensionRow row = new ETDataExtensionRow();
        row.setDataExtensionKey(key);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            row.setColumn(entry.getKey(), entry.getValue());
        }

        ETDataExtensionRow result = apiService.Create(row);
        if (result != null)
        {
            return new ResponseEntity(data, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/sdk/delete")
    public ResponseEntity deleteRow(@PathVariable String key, @RequestBody Map<String, String> data) {
        // delete
          ETDataExtensionRow row = new ETDataExtensionRow();
        row.setDataExtensionKey(key);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            row.setColumn(entry.getKey(), entry.getValue());
        }
        // TODO not working!!!
        //apiService.Delete(row);
        return new ResponseEntity(true, HttpStatus.OK);
    }


    /**
     * Index page - Getting Started
     *
     * @return
     */
    @GetMapping(value = "/rest")
    public String rest() {
        return "api/rest";
    }

    /**
     * Index page - Getting Started
     *
     * @return
     */
    @GetMapping(value = "/soap")
    public String soap() {
        return "api/soap";
    }

    @RequestMapping("/test")
    public ResponseEntity test(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new ResponseEntity(String.format("Hello, %s!", name), HttpStatus.OK);
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity post(@RequestBody String json) {
        out.println("************** " + json + " *****************");
        return new ResponseEntity("OK", HttpStatus.OK);
    }

}
