package miso.demochatapplication;


import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyChatViewHolder> {
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;

    private List<Chat> mChats;

    public ChatAdapter(List<Chat> chats) {
        mChats = chats;
    }

    @Override
    public MyChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == VIEW_TYPE_ME){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_mine,parent,false);
        }
        else if(viewType == VIEW_TYPE_OTHER){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_other,parent,false);
        }
        return new MyChatViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MyChatViewHolder holder, int position) {

        holder.txtChatMessage.setText(mChats.get(position).getMessage());

    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals(mChats.get(position).getSednerid(),Chat.currentuser)){
            return VIEW_TYPE_ME;
        }
        else{
            return VIEW_TYPE_OTHER;
        }
    }

    static class MyChatViewHolder extends RecyclerView.ViewHolder {
         TextView txtChatMessage, txtUserAlphabet;

        public MyChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = (TextView) itemView.findViewById(R.id.text_view_chat_message);
            txtUserAlphabet = (TextView) itemView.findViewById(R.id.text_view_user_alphabet);
        }
    }


}