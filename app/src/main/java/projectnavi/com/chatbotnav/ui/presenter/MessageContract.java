package projectnavi.com.chatbotnav.ui.presenter;

import java.util.List;

import projectnavi.com.chatbotnav.model.Info;

/**
 * Created by Bruno on 25/03/2017.
 */

public interface MessageContract {

    interface View{
        void onSuccessMessage(List<String> msgRetorno);
        void onErrorMessage(int code);
    }

    interface Presenter{
        void sendMessage(Info info);
    }
}
