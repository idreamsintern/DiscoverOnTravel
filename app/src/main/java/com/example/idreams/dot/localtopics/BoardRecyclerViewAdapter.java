package com.example.idreams.dot.localtopics;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idreams.dot.R;

import java.util.ArrayList;

/**
 * Created by chichunchen on 2015/7/20.
 */
public class BoardRecyclerViewAdapter extends
        RecyclerView.Adapter<BoardRecyclerViewAdapter.ViewHolder> {

    private static ArrayList<Board> boards;
    private Context context;

    public BoardRecyclerViewAdapter(Context context, ArrayList<Board> boards) {
        this.boards = boards;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvName;
        public Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.tvName = (TextView) itemView.findViewById(R.id.tvName);
            this.context = context;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition(); // gets item position
            Board board = boards.get(position);

            Intent intent = new Intent(context, TopicListActivity.class);
            intent.putExtra("BoardName", board.getName());
            context.startActivity(intent);
        }
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public BoardRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).
                inflate(R.layout.item_board, parent, false);

        return new BoardRecyclerViewAdapter.ViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(BoardRecyclerViewAdapter.ViewHolder holder, int position) {
        Board board = boards.get(position);
        holder.tvName.setText(board.getName());
    }

    @Override
    public int getItemCount() {
        return boards.size();
    }
}