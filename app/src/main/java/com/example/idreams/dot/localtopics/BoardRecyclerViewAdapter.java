package com.example.idreams.dot.localtopics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.idreams.dot.R;

import java.util.ArrayList;

/**
 * Created by chichunchen on 2015/7/20.
 */
public class BoardRecyclerViewAdapter extends
        RecyclerView.Adapter<BoardRecyclerViewAdapter.ViewHolder> {

    public BoardRecyclerViewAdapter(Context context, ArrayList<Board> boards) {
        this.boards = boards;
        this.context = context;
    }

    private ArrayList<Board> boards;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public BoardRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).
                inflate(R.layout.item_board, parent, false);

        return new BoardRecyclerViewAdapter.ViewHolder(itemView);
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