package sfdc.mc.repository;

import com.exacttarget.fuelsdk.*;
import com.exacttarget.fuelsdk.internal.DataExtension;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Fuel SDK Repository
 * https://developer.salesforce.com/docs/atlas.en-us.noversion.mc-sdks.meta/mc-sdks/index-sdk.htm
 * http://salesforce-marketingcloud.github.io/FuelSDK-Java/index.html?overview-summary.html
 */
@Repository
public class FuelSDKRepository {

    private ETClient client;

    public FuelSDKRepository() {
        InitSDKClient();
    }

    /**
     * Instantiates an sdk client
     */
    private void InitSDKClient() {
        ETConfiguration configuration = new ETConfiguration();
        // TODO move configuration out
        configuration.set("clientId", "i7o8igw9j38ncdnhwjnvlajv");
        configuration.set("clientSecret", "06sChaYgzOIw9tuk5Y2LfMlU");

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

    public ETDataExtension GetDataExtensionDetails(String id) {

        EnsureClientInitialization();

        ETExpression expression = new ETExpression();
        expression.setProperty("id");
        expression.setOperator(ETExpression.Operator.EQUALS);
        expression.setValue(id);

        ETFilter filter = new ETFilter();
        filter.setExpression(expression);
        ETResponse<ETDataExtension> response = null;
        try {
            response = client.retrieve(ETDataExtension.class, filter);
            if (response.getObjects().size() > 0)
                return response.getObjects().get(0);
//            for (ETDataExtension ext : response.getObjects()) {
//                System.out.println(ext);
//                List<ETDataExtensionColumn> columns = ext.retrieveColumns();
//                for (ETDataExtensionColumn col : columns)
//                    System.out.println(col);
//            }
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

    public void GetDataExtensionData(String key) {
        EnsureClientInitialization();
        // expression
        ETExpression expression = new ETExpression();
        expression.setProperty("CustomerKey");
        expression.setOperator(ETExpression.Operator.EQUALS);
        expression.setValue(key);
        // filter
        ETFilter filter = new ETFilter();
        filter.setExpression(expression);

        try {
            ETResponse<ETDataExtensionRow> res = ETDataExtension.select(client, "key=" + key, new ETFilter());
            for (ETDataExtensionRow row : res.getObjects())
                System.out.println(row);
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
    }
}
