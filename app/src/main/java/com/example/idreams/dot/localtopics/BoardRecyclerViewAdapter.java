package com.example.idreams.dot.localtopics;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.idreams.dot.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by chichunchen on 2015/7/20.
 */
public class BoardRecyclerViewAdapter extends
        RecyclerView.Adapter<BoardRecyclerViewAdapter.ViewHolder> {

    private static ArrayList<Board> allBoards;
    private static ArrayList<Board> visibleBoards;
    private Context context;

    public BoardRecyclerViewAdapter(Context context, ArrayList<Board> boards) {
        this.visibleBoards = boards;
        this.allBoards = boards;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvName;
        public ImageView mvBoard;
        public Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.tvName = (TextView) itemView.findViewById(R.id.tvName);
            this.mvBoard = (ImageView) itemView.findViewById(R.id.board_image);
            this.context = context;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition(); // gets item position
            Board board = visibleBoards.get(position);

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
        Board board = visibleBoards.get(position);
        holder.tvName.setText(board.getName());

        Picasso.with(holder.context)
                .load("http://lorempixel.com/200/200/")
                .into(holder.mvBoard);
    }

    @Override
    public int getItemCount() {
        return visibleBoards.size();
    }

    public void flushFilter() {
        visibleBoards = new ArrayList<>();
        visibleBoards.addAll(allBoards);
        notifyDataSetChanged();
    }

    public void setFilter(String query) {
        visibleBoards = new ArrayList<>();
        query = query.toLowerCase();
        for(Board item: allBoards) {
            if (item.getName().toLowerCase().contains(query))
                visibleBoards.add(item);
        }

        notifyDataSetChanged();
    }
}