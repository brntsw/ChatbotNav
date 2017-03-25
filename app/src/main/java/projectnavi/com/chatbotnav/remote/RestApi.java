package projectnavi.com.chatbotnav.remote;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import projectnavi.com.chatbotnav.model.ResponseBot;
import projectnavi.com.chatbotnav.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * Created by Bruno on 25/03/2017.
 */
public interface RestApi {

    @Headers({"Accept: application/json", "Content-type: application/json"})
    @GET(AppConstants.SEND_INFO)
    Call<ResponseBot> sendInfo(@QueryMap Map<String, String> params);

    class Builder{
        private static HttpLoggingInterceptor getLoggingInterceptor(){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return interceptor;
        }

        private static OkHttpClient getOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor){
            return new OkHttpClient.Builder()
                    .dispatcher(new Dispatcher(Executors.newFixedThreadPool(AppConstants.NUMBER_OF_THREADS)))
                    .addInterceptor(httpLoggingInterceptor)
                    .readTimeout(AppConstants.TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(AppConstants.TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
        }

        public static RestApi build(){
            OkHttpClient client = getOkHttpClient(getLoggingInterceptor());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(client)
                    .build();

            return retrofit.create(RestApi.class);
        }
    }

}
