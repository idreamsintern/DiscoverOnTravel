package com.example.idreams.dot.localtopics;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.idreams.dot.BaseActivity;
import com.example.idreams.dot.R;

import java.util.ArrayList;

public class BoardActivity extends BaseActivity {

    private final static String LOG_TAG = "BoardActivity";
    private RecyclerView boardRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
//        final String[] boardStringArray = getResources().getStringArray(R.array.board);

        boardRecyclerView = (RecyclerView) findViewById(R.id.rv_board);

        BoardRecyclerViewAdapter adapter = new BoardRecyclerViewAdapter(this, getBoards());
        boardRecyclerView.setAdapter(adapter);
        boardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Optimizations if all item views are of the same height and width
        // for significantly smoother scrolling.
        boardRecyclerView.setHasFixedSize(true);

    }

    private ArrayList<Board> getBoards() {
        ArrayList<Board> items = new ArrayList<>();
        items.add(new Board("Gossip"));
        items.add(new Board("lol"));
        items.add(new Board("nba"));
        items.add(new Board("mlb"));

        return items;
    }

    private void setListenter() {
        //        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, boardStringArray);

        //        boardRecyclerView.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                        Intent intent = new Intent(getApplicationContext(), TopicListActivity.class);
//                        String board_name = boardStringArray[position];
//                        intent.putExtra("BoardName", board_name);
//                        startActivity(intent);
//                    }
//                }
//        );
//        boardRecyclerView.setAdapter(adapter);
    }
}
