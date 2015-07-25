package com.example.idreams.dot.feedback;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idreams.dot.BaseActivity;
import com.example.idreams.dot.MainActivity;
import com.example.idreams.dot.R;
import com.example.idreams.dot.chat.ChatActivity;
import com.example.idreams.dot.chat.ChatListActivity;
import com.example.idreams.dot.nearby.NearbyActivity;
import com.example.idreams.dot.utils.TemplateAdapter;
import com.firebase.client.Firebase;


public class FeedbackActivity extends BaseActivity implements TemplateAdapter.CreateViewFromCallback,FeedbackFragment.FeedbackFragmentCallbacks {
    TemplateAdapter mAdapter;
    Item item1,item2;
    Firebase mFirebaseRef;
    private Class targetClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(getIntent().getStringExtra("source_context")) {
            case "MainActivity": targetClass = MainActivity.class; break;
            case "NearbyActivity":  targetClass = NearbyActivity.class; break;
            case "ChatListActivity":  targetClass = ChatListActivity.class; break;
            case "ChatActivity":  targetClass = ChatActivity.class; break;
        }
        setContentView(R.layout.activity_feedback);
        item1=new Item("bug report","state what error you encountered");
        item2=new Item("suggestion","how this app can get better");
        mAdapter=new TemplateAdapter<Item>(this, R.layout.feedback_item);
        mAdapter.add(item1);
        mAdapter.add(item2);
        ListView feedback_list= (ListView) findViewById(R.id.feedback_list);
        feedback_list.setAdapter(mAdapter);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public Intent getSupportParentActivityIntent(){

        return new Intent(this, targetClass);
    }

    @Override
    public View createViewFromResource(int position, View convertView, ViewGroup parent, int Resource ){
        View view=null;
        if(convertView==null){
            //view=getLayoutInflater().inflate(Resource, parent);
            LayoutInflater inflater =(LayoutInflater)LayoutInflater.from(this);
            view=inflater.inflate(R.layout.feedback_item,parent,false);
        }
        else{
            view=convertView;
        }
        Item item = (Item)mAdapter.getItem(position);
        Button itembtn=(Button)view.findViewById(R.id.itembtn);
        TextView discription = (TextView)view.findViewById(R.id.description);
        itembtn.setTag(item);
        itembtn.setText(item.getTitle());
        discription.setText(item.getDiscription());
        itembtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentMgr = ((Activity) v.getContext()).getFragmentManager();
                FragmentTransaction fragmentTrans = fragmentMgr.beginTransaction();
                FeedbackFragment feedback_frag = new FeedbackFragment();
                fragmentTrans.replace(R.id.container, feedback_frag, "feedback");
                fragmentTrans.commit();
                setTitle((String) (((Item) v.getTag()).getTitle()));
            }
        });
        return view;
    }
    public void getFeedback(View view){
        EditText feedback=(EditText)findViewById(R.id.feedback);
        String sFeedback=feedback.getText().toString();
        String sType=(String)getTitle();
        Firebase.setAndroidContext(this);
        String FirebaseURL="https://torrid-inferno-6846.firebaseio.com/";
        mFirebaseRef = new Firebase(FirebaseURL).child("feedback");
        feedbackObj obj=new feedbackObj(sType,sFeedback);
        mFirebaseRef.push().setValue(obj);
        feedback.setText("");
        Toast.makeText(this,"Thanks for your "+getTitle(), Toast.LENGTH_SHORT).show();

    }
    public class feedbackObj{
        public String type,content;
        feedbackObj(){}
        feedbackObj(String type,String content){
            this.type=type;
            this.content=content;
        }
    }

}
