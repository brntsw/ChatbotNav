package projectnavi.com.chatbotnav.ui.presenter;

import projectnavi.com.chatbotnav.model.Info;

/**
 * Created by Bruno on 25/03/2017.
 */

public interface MessageContract {

    interface View{
        void onSuccessMessage(String msgRetorno);
        void onErrorMessage();
    }

    interface Presenter{
        void sendMessage(Info info);
    }
}
