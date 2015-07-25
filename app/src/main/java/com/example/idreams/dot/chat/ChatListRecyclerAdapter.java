package com.example.idreams.dot.chat;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        holder.ivTitle.setImageDrawable (context.getDrawable(chatroom.getBackground()));
    }

    @Override
    public int getItemCount() {
        return chatrooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivTitle;
        public CardView chatCardView;
        public Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.ivTitle = (ImageView) itemView.findViewById(R.id.tv_chat_room_title);
            this.chatCardView = (CardView) itemView.findViewById(R.id.card_view);
            this.context = context;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Chatroom chatroom = chatrooms.get(position);
            final String chatRoomBackgroud = "CHATROOM_BACKGROUND";
            int largeBackground = chatroom.getBackground();
            switch (largeBackground) {
                case R.drawable.chatroom1small:
                    largeBackground = R.drawable.chatroom1large; break;
                case R.drawable.chatroom2small:
                    largeBackground = R.drawable.chatroom2large; break;
                case R.drawable.chatroom3small:
                    largeBackground = R.drawable.chatroom3large; break;
            }


            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra(chatRoomBackgroud, largeBackground);
            intent.putExtra("chatroom_title", chatroom.getTitle());

            View sharedView = this.chatCardView;
            String transitionName = v.getResources().getString(R.string.chatroom_icon);

            ActivityOptions transitionActivityOptions =
                    ActivityOptions.makeSceneTransitionAnimation((Activity) context, sharedView, transitionName);
            context.startActivity(intent, transitionActivityOptions.toBundle());
        }
    }
}
