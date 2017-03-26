package projectnavi.com.chatbotnav.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import projectnavi.com.chatbotnav.R;

public class AparelhosActivity extends AppCompatActivity {

    @BindView(R.id.tv_maquina_lavar)
    TextView tvMaquinaLavar;

    @BindView(R.id.tv_microondas)
    TextView tvMicroondas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aparelhos);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_maquina_lavar)
    public void buttonMaquinaLavarClick(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("deviceName", "Maquina de lavar");
        startActivity(intent);
    }

    @OnClick(R.id.tv_microondas)
    public void buttonMicroondasClick(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("deviceName", "Microondas");
        startActivity(intent);
    }
}
