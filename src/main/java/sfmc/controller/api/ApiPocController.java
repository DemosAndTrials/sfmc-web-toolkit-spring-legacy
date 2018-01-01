package sfmc.controller;

import com.exacttarget.fuelsdk.ETDataExtensionRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sfmc.service.ApiService;

import java.util.Map;

import static java.lang.System.out;

/**
 * API POC Controller
 * api playground
 */
@Controller
@RequestMapping("api/poc")
public class ApiPocController {

    @Autowired
    ApiService apiService;

    /**
     * Index page
     *
     * @return
     */
    @GetMapping(value = {"/", "/index"})
    public String index() {
        //apiService.getDataExtensionsDetails();
        return "api/poc/test";
    }

    @PostMapping(value = "/create/{key}", headers = "Accept=application/json")
    public ResponseEntity createRow(@PathVariable String key, @RequestBody Map<String, String> data) {
        // save
//        ETDataExtensionRow row = new ETDataExtensionRow();
//        row.setDataExtensionKey(key);
//        for (Map.Entry<String, String> entry : data.entrySet()) {
//            row.setColumn(entry.getKey(), entry.getValue());
//        }
//
//        ETDataExtensionRow result = apiService.create(row);
//        if (result != null)
//        {
//            return new ResponseEntity(data, HttpStatus.OK);
//        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/delete/{key}")
    public ResponseEntity deleteRow(@PathVariable String key, @RequestBody Map<String, String> data) {
        // save
        ETDataExtensionRow row = new ETDataExtensionRow();
        row.setDataExtensionKey("F0692A03-5FF6-4F84-B715-B519EEA64CA7");
        row.setColumn("SUBSCRIBERKEY", "test12@mail.com");

//        ETDataExtensionRow row = new ETDataExtensionRow();
//        row.setDataExtensionKey(key);
//        for (Map.Entry<String, String> entry : data.entrySet()) {
//            row.setColumn(entry.getKey(), entry.getValue());
//        }
        // TODO not working!!!
        //apiService.delete(row);

        out.println("************** delete: " + key + " *****************");
        return new ResponseEntity(true, HttpStatus.OK);
    }
}
