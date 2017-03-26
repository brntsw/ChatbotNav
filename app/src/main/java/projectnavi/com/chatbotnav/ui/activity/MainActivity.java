package projectnavi.com.chatbotnav.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import projectnavi.com.chatbotnav.R;
import projectnavi.com.chatbotnav.model.Info;
import projectnavi.com.chatbotnav.model.Message;
import projectnavi.com.chatbotnav.remote.RestApi;
import projectnavi.com.chatbotnav.ui.adapter.MessageAdapter;
import projectnavi.com.chatbotnav.ui.presenter.MessageContract;
import projectnavi.com.chatbotnav.ui.presenter.MessagePresenter;
import projectnavi.com.chatbotnav.utils.AppConstants;
import projectnavi.com.chatbotnav.utils.DateUtils;

public class MainActivity extends AppCompatActivity implements MessageContract.View {

    @BindView(R.id.recycler_mensagens)
    RecyclerView recyclerMensagens;

    @BindView(R.id.edit_mensagem)
    EditText editMensagem;

    @BindView(R.id.bt_enviar)
    Button btEnviar;

    private MessageAdapter adapter;
    private List<Message> listaMsg;
    private RestApi restApi;
    private MessageContract.Presenter presenter;
    private Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);

        setup();
    }

    @OnClick(R.id.bt_enviar)
    public void buttonEnviarClick(){
        String msg = editMensagem.getText().toString();
        if(!"".equals(msg)){
            editMensagem.setText("");
            Message message = new Message();
            message.setType(AppConstants.MSG_MINE);
            message.setText(msg);
            message.setTime(DateUtils.getCurrentTime());

            listaMsg.add(message);
            adapter.notifyData(listaMsg);
            recyclerMensagens.smoothScrollToPosition(recyclerMensagens.getAdapter().getItemCount() - 1);

            Info info = new Info();
            if(args != null && args.getString("deviceName") != null){
                info.setDeviceName(args.getString("deviceName"));
            }
            else{
                info.setDeviceName("Maquina de Lavar");
            }
            info.setSpeakText(msg);

            presenter.sendMessage(info);
            btEnviar.setEnabled(false);
        }
    }

    private void setup(){
        args = getIntent().getExtras();

        if(args != null && args.getString("deviceName") != null){
            if(getSupportActionBar() != null) {
                getSupportActionBar().setTitle(args.getString("deviceName"));
            }
        }

        restApi = RestApi.Builder.build();

        presenter = new MessagePresenter(this, restApi);

        listaMsg = new ArrayList<>();
        adapter = new MessageAdapter(listaMsg);

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerMensagens.setLayoutManager(manager);
        recyclerMensagens.setAdapter(adapter);
    }

    @Override
    public void onSuccessMessage(List<String> msgRetorno) {
        btEnviar.setEnabled(true);
        Message message = new Message();
        message.setType(AppConstants.MSG_BOT);
        if(msgRetorno.size() > 0){
            message.setText(msgRetorno.get(0));
        }
        else{
            message.setText(getString(R.string.nao_encontrei));
        }
        message.setTime(DateUtils.getCurrentTime());
        listaMsg.add(message);
        adapter.notifyData(listaMsg);
        recyclerMensagens.smoothScrollToPosition(recyclerMensagens.getAdapter().getItemCount() - 1);
    }

    @Override
    public void onErrorMessage(int code) {
        Log.e("Erro", "error message");
        btEnviar.setEnabled(true);
        Message message = new Message();

        switch (code){
            case AppConstants.STATUS_CODE_NOT_FOUND:
                message.setType(AppConstants.MSG_BOT);
                message.setText(getString(R.string.nao_encontrei));
                break;
            case AppConstants.STATUS_CODE_ERROR:
                message.setType(AppConstants.MSG_ERRO);
                message.setText(getString(R.string.erro));
                break;
            default:
                message.setType(AppConstants.MSG_ERRO);
                message.setText(getString(R.string.erro));
                break;
        }

        message.setTime(DateUtils.getCurrentTime());

        listaMsg.add(message);
        adapter.notifyData(listaMsg);
        recyclerMensagens.smoothScrollToPosition(recyclerMensagens.getAdapter().getItemCount() - 1);
    }
}
