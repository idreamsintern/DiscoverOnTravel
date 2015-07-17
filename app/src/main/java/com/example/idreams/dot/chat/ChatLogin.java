package com.example.idreams.dot.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.idreams.dot.R;

/**
 * Created by dengli on 2015/7/16.
 */
public class ChatLogin extends AppCompatActivity {
    EditText etLogin;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlogin);

        etLogin = (EditText) findViewById(R.id.edit_loginName);
        btnLogin = (Button) findViewById(R.id.btn_loginName);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatLogin.this, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("etLogin", etLogin.getText().toString());

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}
