package com.example.idreams.dot.chat;

import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idreams.dot.BaseActivity;
import com.example.idreams.dot.R;
import com.facebook.Profile;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by schwannden on 2015/7/15.
 */
public class ChatActivity extends BaseActivity {

    private static final String FIREBASE_URL = "https://torrid-inferno-6846.firebaseio.com/";
    private static final long ANIM_DURATION = 1000;
    private static final String chatRoomBackgroud = "CHATROOM_BACKGROUND";
    private String mUsername;
    private String mUserId;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;

    private ChatListAdapter mChatListAdapter;
    private Profile currentProfile;
    private View bgViewGroup;
    private ImageView currentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_chat);
        bgViewGroup = findViewById(R.id.chat_view_group);
        setupWindowAnimations();

        currentProfile = Profile.getCurrentProfile();
        if (currentProfile != null) {
            currentImage = (ImageView) findViewById(R.id.current_thumbnail);
            Picasso.with(getApplicationContext())
                    .load(currentProfile.getProfilePictureUri(150, 150))
                    .into(currentImage);
        }

        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("chat");

        // Setup our input methods. Enter key on the keyboard or pushing the send button
        EditText inputText = (EditText) findViewById(R.id.messageInput);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Make sure we have a mUsername
        setupUsername();
        setTitle("Chatting as " + mUsername);
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = (ListView) findViewById(R.id.chatroom_list);
        // Tell our list adapter that we only want 50 messages at a time
        mChatListAdapter = new ChatListAdapter(mFirebaseRef.limit(50), this, R.layout.chat_message, mUsername);
        listView.setAdapter(mChatListAdapter);
        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mChatListAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(ChatActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mChatListAdapter.cleanup();
    }

    private void setupUsername() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = prefs.getString("username", "traveller");
        mUserId = prefs.getString("userid", null);
    }

    private void sendMessage() {
        EditText inputText = (EditText) findViewById(R.id.messageInput);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, mUsername, mUserId);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFirebaseRef.push().setValue(chat);
            inputText.setText("");
        }
    }

    private void setupWindowAnimations() {
        Bundle extras = getIntent().getExtras();
        int background = extras.getInt(chatRoomBackgroud);
        Drawable backgroundImage = getResources().getDrawable(background);
        bgViewGroup.setBackground(backgroundImage);
        bgViewGroup.getBackground().setAlpha(150);
    }

}
