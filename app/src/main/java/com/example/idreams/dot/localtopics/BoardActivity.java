package com.example.idreams.dot.localtopics;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.idreams.dot.BaseActivity;
import com.example.idreams.dot.R;

import java.util.ArrayList;

public class BoardActivity extends BaseActivity {

    private final static String LOG_TAG = "BoardActivity";
    private RecyclerView boardRecyclerView;
    BoardRecyclerViewAdapter adapter;
    private int spanCount = 2;
    private int spacing = 30;
    private boolean includeEdge = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        setupWindowAnimations();

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        boardRecyclerView = (RecyclerView) findViewById(R.id.rv_board);

        adapter = new BoardRecyclerViewAdapter(this, getBoards());
        boardRecyclerView.setAdapter(adapter);

        // Optimizations if all item views are of the same height and width
        // for significantly smoother scrolling.
        boardRecyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getApplicationContext(), 2);
        boardRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        // Attach the layout manager to the recycler view
        boardRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private ArrayList<Board> getBoards() {
        String[] boardStringArray = getResources().getStringArray(R.array.board);

        ArrayList<Board> boardArrayList = new ArrayList<>();

        for (int i = 0; i < boardStringArray.length; i++) {
            boardArrayList.add(new Board(boardStringArray[i]));
        }

        return boardArrayList;
    }

    private void setupWindowAnimations() {
        Explode explode = new Explode();
        explode.setDuration(1000);
        getWindow().setEnterTransition(explode);

        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setReturnTransition(fade);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.setFilter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty())
                    adapter.flushFilter();
                else {
                    adapter.setFilter(newText);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }
}
