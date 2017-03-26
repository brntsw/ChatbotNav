package projectnavi.com.chatbotnav.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Bruno on 25/03/2017.
 */

public class DateUtils {

    public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ROOT);

        Date date = new Date();

        return sdf.format(date);
    }

}
