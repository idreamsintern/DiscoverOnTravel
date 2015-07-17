package com.example.idreams.dot.localtopics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.idreams.dot.BaseActivity;
import com.example.idreams.dot.R;
import com.example.idreams.dot.SettingsActivity;

public class BoardActivity extends BaseActivity {

    private final static String LOG_TAG = "BoardActivity";
    private ListView boardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        final String[] boardStringArray = getResources().getStringArray(R.array.board);

        boardList = (ListView) findViewById(R.id.localtopic_list);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, boardStringArray);
        boardList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(getApplicationContext(), TopicListActivity.class);
                        String board_name = boardStringArray[position];
                        intent.putExtra("BoardName", board_name);
                        startActivity(intent);
                    }
                }
        );
        boardList.setAdapter(adapter);
    }
}
