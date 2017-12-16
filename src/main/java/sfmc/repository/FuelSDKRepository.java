package sfmc.repository;

import com.exacttarget.fuelsdk.*;
import com.exacttarget.fuelsdk.internal.*;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Fuel SDK Repository
 * https://developer.salesforce.com/docs/atlas.en-us.noversion.mc-sdks.meta/mc-sdks/index-sdk.htm
 * http://salesforce-marketingcloud.github.io/FuelSDK-Java/index.html?overview-summary.html
 * http://salesforce-marketingcloud.github.io/FuelSDK-Java/
 */
@Repository
public class FuelSDKRepository {

    private ETClient client;

//    public FuelSDKRepository() {
//        InitSDKClient();
//    }

    /**
     * Instantiates an sdk client
     */
    private void InitSDKClient() {
        ETConfiguration configuration = new ETConfiguration();
        // get config from heroku
        configuration.set("clientId",  System.getenv("CLIENT_ID"));
        configuration.set("clientSecret", System.getenv("CLIENT_SECRET"));

        try {
            client = new ETClient(configuration);
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
    }

    private void EnsureClientInitialization() {
        if (client == null)
            InitSDKClient();
    }

    /**
     * Gets access token
     */
    public void GetToken() {
        String token = client.getAccessToken();
        System.out.println("*** TOKEN: " + token);
    }

    /**
     * Gets list of data extensions
     */
    public List<ETDataExtension> GetDataExtensionsDetails() {
        try {
            EnsureClientInitialization();
            List<ETDataExtension> exts = new ArrayList<>();
            ETResponse<ETDataExtension> response = client.retrieve(ETDataExtension.class);
            for (ETDataExtension ext : response.getObjects()) {
                exts.add(ext);
                System.out.println("DE: " + ext);
//                List<ETDataExtensionColumn> columns = ext.retrieveColumns();
//                for (ETDataExtensionColumn col : columns)
//                    System.out.println(col);
            }
            return exts;
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get data extension by id
     *
     * @param id
     * @return
     */
    public ETDataExtension GetDataExtensionDetails(String id) {

        EnsureClientInitialization();
        try {
            ETExpression expression = new ETExpression();
            expression.setProperty("id");
            expression.setOperator(ETExpression.Operator.EQUALS);
            expression.setValue(id);

            ETFilter filter = new ETFilter();
            filter.setExpression(expression);

            ETResponse<ETDataExtension> response = client.retrieve(ETDataExtension.class, filter);
            if (response.getObjects().size() > 0) {
                ETDataExtension ext = response.getObjects().get(0);
                ext.retrieveColumns();
                ext.getColumns().sort(Comparator.comparing(o -> o.getCreatedDate()));
                return ext;
            }
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public void GetDataExtensionDetails(String key) {
//        ETExpression expression = new ETExpression();
//        expression.setProperty("CustomerKey");
//        expression.setOperator(ETExpression.Operator.EQUALS);
//        expression.setValue("23AC1A36-5E45-4FE5-BF4B-7AFBE434C1AB");
//
//        ETFilter filter = new ETFilter();
//        filter.setExpression(expression);
//        ETResponse<ETDataExtension> response = null;
//        try {
//            response = client.retrieve(ETDataExtension.class, filter);
//            for (ETDataExtension ext : response.getObjects()) {
//                System.out.println(ext);
//                List<ETDataExtensionColumn> columns = ext.retrieveColumns();
//                for (ETDataExtensionColumn col : columns)
//                    System.out.println(col);
//            }
//        } catch (ETSdkException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Get records by data extension key
     *
     * @param key
     */
    public List<ETDataExtensionRow> GetDataExtensionRecordsByKey(String key) {
        EnsureClientInitialization();
//        // expression
//        ETExpression expression = new ETExpression();
//        expression.setProperty("CustomerKey");
//        expression.setOperator(ETExpression.Operator.EQUALS);
//        expression.setValue(key);
//        // filter
//        ETFilter filter = new ETFilter();
//        filter.setExpression(expression);

        return GetDataExtensionRecords("key=" + key, new ETFilter());
    }

    private List<ETDataExtensionRow> GetDataExtensionRecords(String dataExtension, ETFilter filter) {
        try {
            List<ETDataExtensionRow> records = new ArrayList<>();
            ETResponse<ETDataExtensionRow> res = ETDataExtension.select(client, dataExtension, filter);
            for (ETDataExtensionRow row : res.getObjects()) {
                System.out.println(row);
                records.add(row);
            }
            return records;
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ETDataExtensionRow CreateDataExtensionRow(ETDataExtensionRow record){
        try {
            ETResponse<ETDataExtensionRow> res = client.create(record);
            return res.getObject();
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ETDataExtensionRow DeleteDataExtensionRow(ETDataExtensionRow record){
        EnsureClientInitialization();
        try {
            ETResponse<ETDataExtensionRow> res = client.delete(record);

            return res.getObject();
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
        return null;
    }
}
