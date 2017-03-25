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
            Message message = new Message();
            message.setType(AppConstants.MSG_MINE);
            message.setText(msg);

            listaMsg.add(message);
            adapter.notifyData(listaMsg, AppConstants.MSG_MINE);
            recyclerMensagens.smoothScrollToPosition(recyclerMensagens.getAdapter().getItemCount() - 1);

            Info info = new Info();
            info.setDeviceName("microondas");
            info.setSpeakText(msg);

            presenter.sendMessage(info);
            btEnviar.setEnabled(false);
        }
    }

    private void setup(){
        args = getIntent().getExtras();

        if(args != null){
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
    public void onSuccessMessage(String msgRetorno) {
        btEnviar.setEnabled(true);
        Message message = new Message();
        message.setType(AppConstants.MSG_BOT);
        message.setText(msgRetorno);
        listaMsg.add(message);
        adapter.notifyData(listaMsg, AppConstants.MSG_BOT);
        recyclerMensagens.smoothScrollToPosition(recyclerMensagens.getAdapter().getItemCount() - 1);
    }

    @Override
    public void onErrorMessage() {
        Log.e("Erro", "error message");
        btEnviar.setEnabled(true);
        Message message = new Message();
        message.setType(AppConstants.MSG_ERRO);
        message.setText("Error");

        listaMsg.add(message);
        adapter.notifyData(listaMsg, AppConstants.MSG_ERRO);
        recyclerMensagens.smoothScrollToPosition(recyclerMensagens.getAdapter().getItemCount() - 1);
    }
}
