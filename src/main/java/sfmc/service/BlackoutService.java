package sfmc.service;

import com.exacttarget.fuelsdk.ETDataExtension;
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
     *
     * @param args
     * @return
     */
    public boolean CheckBlackout(CustomActivityExecuteArgs args) {
        // get local time
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Israel"));
        System.out.println("*** current time: " + df.format(date));

        // check if there is weekend or holiday

        // update wait by attribute field


        return true;
    }

    public List<ETDataExtension> GetDataExtensionsDetails() {
        return sdkRepository.GetDataExtensionsDetails();
    }
}
