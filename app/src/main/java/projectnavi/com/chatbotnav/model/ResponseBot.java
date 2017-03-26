package projectnavi.com.chatbotnav.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Bruno on 25/03/2017.
 */

public class ResponseBot {

    @JsonProperty("output")
    private List<String> responseText;

    public ResponseBot(){}

    public List<String> getResponseText() {
        return responseText;
    }

    public void setResponseText(List<String> responseText) {
        this.responseText = responseText;
    }
}
