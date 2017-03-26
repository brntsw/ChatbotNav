package projectnavi.com.chatbotnav.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import projectnavi.com.chatbotnav.R;
import projectnavi.com.chatbotnav.model.Message;
import projectnavi.com.chatbotnav.utils.AppConstants;

/**
 * Created by Bruno on 25/03/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> mMensagens;

    public MessageAdapter(List<Message> mensagens){
        mMensagens = mensagens;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        switch (viewType){
            case AppConstants.MSG_MINE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mine_message, parent, false);
                break;
            case AppConstants.MSG_BOT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bot_message, parent, false);
                break;
            case AppConstants.MSG_ERRO:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_error_message, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mine_message, parent, false);
                break;
        }

        return new ViewHolder(v);
    }

    public int getItemViewType(int position){
        return mMensagens.get(position).getType();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = mMensagens.get(position);
        if(message != null){
            holder.bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return mMensagens.size();
    }

    public void notifyData(List<Message> mensagens, int msgType){
        mMensagens = mensagens;
        notifyItemInserted(mMensagens.size() - 1);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_message_content)
        TextView tvMessage;

        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Message message){
            tvMessage.setText(message.getText());
            tvTime.setText(message.getTime());
        }
    }

}
