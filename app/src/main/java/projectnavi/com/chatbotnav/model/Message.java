package projectnavi.com.chatbotnav.model;

/**
 * Created by Bruno on 25/03/2017.
 */

public class Message {

    private int type;
    private String text;
    private String time;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
