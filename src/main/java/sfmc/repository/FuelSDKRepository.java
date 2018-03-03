package sfmc.repository;

import com.exacttarget.fuelsdk.*;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static final String REFRESH_TOKEN_NULL = "refreshToken == null";
    private static final String INVOKE_METHOD_ERROR = "error invoking";// "error invoking retrieve method for type class com.exacttarget.fuelsdk.ETDataExtension";
    private ETClient client;

//    public FuelSDKRepository() {
//        initSDKClient();
//    }

    /**
     * Instantiates an sdk client
     */
    private void initSDKClient() {
        ETConfiguration configuration = new ETConfiguration();
        // get config from heroku
        configuration.set("clientId", System.getenv("CLIENT_ID"));
        configuration.set("clientSecret", System.getenv("CLIENT_SECRET"));
        //configuration.set("accessType", System.getenv("ACCESS_TYPE"));
        //configuration.set("requestLegacyToken", "false");

        try {
            client = new ETClient(configuration);
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if client initiated
     */
    private void ensureClientInitialization() {
        if (client == null)
            initSDKClient();
    }

    /**
     * Gets list of data extensions
     */
    public List<ETDataExtension> getDataExtensionsDetails() {
        ensureClientInitialization();
        try {

            List<ETDataExtension> exts = new ArrayList<>();
            ETResponse<ETDataExtension> response = client.retrieve(ETDataExtension.class);
            System.out.println("*** de retrieved with status: " + response.getStatus());
            for (ETDataExtension ext : response.getObjects()) {
                exts.add(ext);
                System.out.println("DE: " + ext);
//                List<ETDataExtensionColumn> columns = ext.retrieveColumns();
//                for (ETDataExtensionColumn col : columns)
//                    System.out.println(col);
            }
            return exts;
        } catch (ETSdkException e) {
            if (HandleTokenExpiration(e))
                getDataExtensionsDetails();
        }
        return null;
    }

    public List<ETDataExtension> getDataExtensionsDetails(String folderId) {
        ensureClientInitialization();

        ETExpression expression = new ETExpression();
        expression.setProperty("folderId");
        expression.setOperator(ETExpression.Operator.EQUALS);
        expression.setValue(folderId);

        ETFilter filter = new ETFilter();
        filter.setExpression(expression);
        List<ETDataExtension> exts = new ArrayList<>();
        ETResponse<ETDataExtension> response = null;
        try {
            response = client.retrieve(ETDataExtension.class, filter);
        } catch (ETSdkException e) {
            e.printStackTrace();
        }
        System.out.println("*** de retrieved with status: " + response.getStatus());
        for (ETDataExtension ext : response.getObjects()) {
            exts.add(ext);
            System.out.println("DE: " + ext);
        }
        return exts;
    }



    public List<ETFolder> getFolders(String contentType){
        ensureClientInitialization();
        try {

            ETExpression expression = new ETExpression();
            expression.setProperty("contentType");
            expression.setOperator(ETExpression.Operator.EQUALS);
            expression.setValue(contentType);

            ETFilter filter = new ETFilter();
            filter.setExpression(expression);

            List<ETFolder> folders = new ArrayList<>();
            ETResponse<ETFolder> response = client.retrieve(ETFolder.class, filter);
            System.out.println("*** folders retrieved with status: " + response.getStatus());
            for (ETFolder folder : response.getObjects()) {
                folders.add(folder);
                System.out.println("folder: " + folder);
            }
            return folders;
        }
        catch (ETSdkException e) {
            if (HandleTokenExpiration(e))
                getDataExtensionsDetails();
        }
        return null;

    }

    /**
     * Get data extension by id
     *
     * @param id
     * @return
     */
    public ETDataExtension getDataExtensionById(String id) {

        ensureClientInitialization();
        try {
            ETExpression expression = new ETExpression();
            expression.setProperty("id");
            expression.setOperator(ETExpression.Operator.EQUALS);
            expression.setValue(id);

            ETFilter filter = new ETFilter();
            filter.setExpression(expression);

            ETResponse<ETDataExtension> response = client.retrieve(ETDataExtension.class, filter);
            System.out.println("*** de retrieved with status: " + response.getStatus());
            if (response.getObjects().size() > 0) {
                ETDataExtension ext = response.getObjects().get(0);
                ext.retrieveColumns();
                ext.getColumns().sort(Comparator.comparing(o -> o.getCreatedDate()));// TODO ????
                return ext;
            }
        } catch (ETSdkException e) {
            if (HandleTokenExpiration(e))
                getDataExtensionById(id);
        }
        return null;
    }

    public ETDataExtension getDataExtensionByKey(String key) {
        ensureClientInitialization();

        try {
            ETResponse<ETDataExtension> result = client.retrieve(ETDataExtension.class, "key=" + key);
            System.out.println("*** de retrieved with status: " + result.getStatus());
            ETDataExtension de = result.getObject();
            if (de != null) {
                de.retrieveColumns();
            }
            return de;
        } catch (ETSdkException e) {
            if (HandleTokenExpiration(e))
                getDataExtensionByKey(key);
        }
        return null;
    }

    /**
     * delete DE
     *
     * @param key
     * @return
     */
    public boolean deleteDataExtension(String key) {
        ensureClientInitialization();
        try {
            ETDataExtension de = new ETDataExtension();
            de.setKey(key);
            ETResponse<ETDataExtension> res = client.delete(de);
            System.out.println("*** de deleted with status: " + res.getStatus());
            if (res.getStatus() == ETResult.Status.OK)
                return true;
        } catch (ETSdkException e) {
            if (HandleTokenExpiration(e))
                deleteDataExtension(key);
        }
        return false;
    }

    public ETDataExtensionRow getDataExtensionRowBySfId(String deKey, String id) {
        ETExpression expression = new ETExpression();
        expression.setProperty("SF_ID");
        expression.setOperator(ETExpression.Operator.EQUALS);
        expression.setValue(id);

        ETFilter filter = new ETFilter();
        filter.setExpression(expression);

        List<ETDataExtensionRow> res = getDataExtensionRecords("key=" + deKey, filter);
        return res.size() > 0 ? res.get(0) : null;
    }

    /**
     * Get Row
     *
     * @param key
     * @param today
     * @return
     * @throws ETSdkException
     */
    public ETDataExtensionRow getDataExtensionRecord(String key, String today) throws ETSdkException {
        // create expression
        // property on left side, value on right side
        ETExpression ex1 = ETExpression.parse("START_DATE <= '" + today + "'");
        ETExpression ex2 = ETExpression.parse("END_DATE >= '" + today + "'");
        ETExpression exp = new ETExpression();
        exp.addSubexpression(ex1);
        exp.setOperator(ETExpression.Operator.AND);
        exp.addSubexpression(ex2);
        // set filter
        ETFilter filter = new ETFilter();
        filter.setExpression(exp);
        // get record
        List<ETDataExtensionRow> res = getDataExtensionRecords("key=" + key, filter);
        return res.size() > 0 ? res.get(0) : null;
    }

    /**
     * Get records by DE Key
     *
     * @param key
     */
    public List<ETDataExtensionRow> getDataExtensionRecordsByKey(String key) {
        return getDataExtensionRecords("key=" + key, new ETFilter());
    }

    /**
     * Select DE records using filter
     *
     * @param dataExtension
     * @param filter
     * @return
     */
    private List<ETDataExtensionRow> getDataExtensionRecords(String dataExtension, ETFilter filter) {
        ensureClientInitialization();
        List<ETDataExtensionRow> records = new ArrayList<>();
        try {
            ETResponse<ETDataExtensionRow> res = ETDataExtension.select(client, dataExtension, filter);
            System.out.println("*** records selected with status: " + res.getStatus());
            for (ETDataExtensionRow row : res.getObjects()) {
                System.out.println(row);
                records.add(row);
            }
            return records;
        } catch (ETSdkException e) {
            if (HandleTokenExpiration(e))
                getDataExtensionRecords(dataExtension, filter);
        }
        return records;
    }

    /**
     * create DE Row
     *
     * @param record
     * @return
     */
    public ETDataExtensionRow createDataExtensionRow(ETDataExtensionRow record) {
        ensureClientInitialization();
        try {
            ETResponse<ETDataExtensionRow> res = client.create(record);// TODO use de.insert?!
            System.out.println("*** record created with status: " + res.getStatus());
            return res.getObject();
        } catch (ETSdkException e) {
            if (HandleTokenExpiration(e))
                createDataExtensionRow(record);
        }
        return null;
    }

    /**
     * delete DE Row
     * row should include primary key
     * TODO use expressions
     * @param de
     * @param record
     * @return
     */
    public boolean deleteDataExtensionRow(ETDataExtension de, ETDataExtensionRow record) {
        try {
            ETResponse<ETDataExtensionRow> res = de.delete(record);
            System.out.println("*** record deleted with status: " + res.getStatus());
            if (res.getStatus() == ETResult.Status.OK)
                return true;
        } catch (ETSdkException e) {
            if (HandleTokenExpiration(e))
                deleteDataExtensionRow(de, record);
        }
        return false;
    }

//    /**
//     * update DE Row
//     *
//     * @param de
//     * @param record
//     * @return
//     */
//    public ETDataExtensionRow updateDataExtensionRow(ETDataExtension de, ETDataExtensionRow record) {
//        ensureClientInitialization();
//        try {
//            ETResponse<ETDataExtensionRow> res = de.update(record);
//            if (res.getStatus() == ETResult.Status.OK)
//                return res.getObject();
//            return null; // TODO throw exception
//        } catch (ETSdkException e) {
//            if (HandleTokenExpiration(e))
//                updateDataExtensionRow(de, record);
//        }
//        return null;
//    }

    /**
     * update DE row
     * row should include de key
     *
     * @param record
     * @return
     */
    public ETDataExtensionRow updateDataExtensionRow(ETDataExtensionRow record) {

        try {
            ETResponse<ETDataExtensionRow> res = ETDataExtensionRow.update(client, Arrays.asList(record));
            System.out.println("*** record updated with status: " + res.getStatus());
            if (res.getStatus() == ETResult.Status.OK)
                return res.getObject();
            return null; // TODO throw exception
        } catch (ETSdkException e) {
            if (HandleTokenExpiration(e))
                updateDataExtensionRow(record);
        }
        return null;
    }

    /**
     * Request new token if old one already expired
     *
     * @param ex
     * @return
     */
    private Boolean HandleTokenExpiration(ETSdkException ex) {
        ex.printStackTrace();
        String message = ex.getMessage();
        if (message.equals(REFRESH_TOKEN_NULL)) {
            System.out.println("*** Token expired *** " + client.getAccessToken());
            try {
                String token = client.requestToken();
                System.out.println("*** Token requested *** " + token);
                System.out.println("*** Client's token *** " + client.getAccessToken());
                return true;
            } catch (ETSdkException e) {
                e.printStackTrace();
            }
        }
        else if(message.startsWith(INVOKE_METHOD_ERROR)){
            // new client
            initSDKClient();
            return true;
        }

        return false;
    }
}
