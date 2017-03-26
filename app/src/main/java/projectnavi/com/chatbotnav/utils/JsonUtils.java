package projectnavi.com.chatbotnav.utils;

import org.json.JSONException;
import org.json.JSONObject;

import projectnavi.com.chatbotnav.model.Info;

/**
 * Created by Bruno on 26/03/2017.
 */

public class JsonUtils {

    public static JSONObject convertToJson(Info info) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deviceName", info.getDeviceName());
        jsonObject.put("speakText", info.getSpeakText());

        return jsonObject;
    }

}
