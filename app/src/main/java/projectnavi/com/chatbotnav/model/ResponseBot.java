package projectnavi.com.chatbotnav.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Bruno on 25/03/2017.
 */

public class ResponseBot {

    @JsonProperty("responseText")
    private String responseText;

    public ResponseBot(){}

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
