package projectnavi.com.chatbotnav.ui.presenter;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.RequestBody;
import projectnavi.com.chatbotnav.model.Info;
import projectnavi.com.chatbotnav.model.ResponseBot;
import projectnavi.com.chatbotnav.remote.RestApi;
import projectnavi.com.chatbotnav.utils.AppConstants;
import projectnavi.com.chatbotnav.utils.JsonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bruno on 25/03/2017.
 */

public class MessagePresenter implements MessageContract.Presenter {

    private static final String TAG = MessagePresenter.class.getName();

    private RestApi mRestApi;
    private MessageContract.View mView;

    public MessagePresenter(MessageContract.View view, RestApi restApi){
        mView = view;
        mRestApi = restApi;
    }

    @Override
    public void sendMessage(Info info) {
        try {
            JSONObject jsonInfo = JsonUtils.convertToJson(info);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonInfo.toString());

            Call<ResponseBot> infoCall = mRestApi.sendInfo(body);
            infoCall.enqueue(new Callback<ResponseBot>() {
                @Override
                public void onResponse(Call<ResponseBot> call, Response<ResponseBot> response) {
                    if (response.code() == AppConstants.STATUS_CODE_SUCCESS) {
                        mView.onSuccessMessage(response.body().getResponseText());
                    } else {
                        mView.onErrorMessage(response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBot> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                    mView.onErrorMessage(AppConstants.STATUS_CODE_ERROR);
                }
            });
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
