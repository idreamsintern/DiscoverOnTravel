package com.example.idreams.dot.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.idreams.dot.R;

import java.util.ArrayList;

/**
 * Created by chichunchen on 2015/7/21.
 */
public class ChatListRecyclerAdapter extends RecyclerView.Adapter<ChatListRecyclerAdapter.ViewHolder> {

    private static ArrayList<Chatroom> chatrooms;
    private Context context;

    public ChatListRecyclerAdapter(Context context, ArrayList<Chatroom> chatrooms) {
        this.chatrooms = chatrooms;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle;
        public CardView chatCardView;
        public Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tv_chat_room_title);
            this.chatCardView = (CardView) itemView.findViewById(R.id.card_view);
            this.context = context;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition(); // gets item position
            Chatroom chatroom = chatrooms.get(position);

            Intent intent = new Intent(context, ChatActivity.class);
            // intent.putExtra("ChatName", chatroom.getTitle());
            context.startActivity(intent);
        }
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ChatListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).
                inflate(R.layout.chat_list_item, parent, false);

        return new ChatListRecyclerAdapter.ViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(ChatListRecyclerAdapter.ViewHolder holder, int position) {
        Chatroom chatroom = chatrooms.get(position);
        holder.tvTitle.setText(chatroom.getTitle());
        holder.chatCardView.setBackground(chatroom.getBackground());
    }

    @Override
    public int getItemCount() {
        return chatrooms.size();
    }
}
