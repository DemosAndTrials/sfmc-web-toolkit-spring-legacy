package sfmc.service;

import com.exacttarget.fuelsdk.ETDataExtension;
import com.exacttarget.fuelsdk.ETDataExtensionRow;
import com.exacttarget.fuelsdk.ETSdkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sfmc.model.CustomActivityExecuteArgs;
import sfmc.repository.FuelSDKRepository;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Blackout Custom Activity Service
 */
@Service
public class BlackoutService {

    @Autowired
    FuelSDKRepository sdkRepository;

    /**
     * @param args
     * @return
     */
    public boolean CheckBlackout(CustomActivityExecuteArgs args) {
        // get local time
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Israel"));
        System.out.println("*** current time: " + df.format(date));

        // check if there is weekend or holiday
        String sourceKey = args.getInArguments().get(0).get("source_de");
        String destinationKey = args.getInArguments().get(1).get("destination_de");

        try {
            // check if today is holiday or weekend
            ETDataExtensionRow holiday = sdkRepository.GetDataExtensionRecord(sourceKey, df.format(date));
            if (holiday != null) {
                // update wait attribute
                ETDataExtensionRow contact = sdkRepository.GetDataExtensionRowByEmail(destinationKey, args.getKeyValue());
                if(contact != null){
                    contact.setDataExtensionKey(destinationKey);
                    contact.setColumn("WaitDate" , holiday.getColumn("EndDate"));
                    sdkRepository.UpdateDataExtensionRow(contact);
                    System.out.println("*** updated contact: " + contact);
                }
            }else{

            }

        } catch (ETSdkException e) {
            e.printStackTrace();
        }

        // update wait by attribute field
        return true;
    }

    public List<ETDataExtension> GetDataExtensionsDetails() {
        return sdkRepository.GetDataExtensionsDetails();
    }
}
