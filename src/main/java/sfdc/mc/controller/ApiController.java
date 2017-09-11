package sfdc.mc.controller;

import com.exacttarget.fuelsdk.*;
import com.exacttarget.fuelsdk.internal.DataExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sfdc.mc.service.ApiService;

import java.util.List;

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
        //apiService.GetDataExtensionData("23AC1A36-5E45-4FE5-BF4B-7AFBE434C1AB");
        //apiService.GetDataExtensionDetails("23AC1A36-5E45-4FE5-BF4B-7AFBE434C1AB");
        apiService.GetDataExtensionsDetails();
        return "api/index";
    }

    /**
     * SDK page
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
     * TODO Column's names order doesn't match records columns!!!
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
