package com.example.idreams.dot.feedback;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.idreams.dot.BaseActivity;
import com.example.idreams.dot.R;
import com.example.idreams.dot.utils.TemplateAdapter;


public class FeedbackActivity extends BaseActivity implements TemplateAdapter.CreateViewFromCallback,FeedbackFragment.FeedbackFragmentCallbacks {
    TemplateAdapter mAdapter;
    Item item1, item2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        item1=new Item("bug report","state what error you encountered");
        item2=new Item("suggestion","how this app can get better");
        mAdapter=new TemplateAdapter<Item>(this, R.layout.feedback_item);
        mAdapter.add(item1);
        mAdapter.add(item2);
        ListView feedback_list= (ListView) findViewById(R.id.feedback_list);
        feedback_list.setAdapter(mAdapter);
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
                FragmentManager fragmentMgr = ((Activity)v.getContext()).getFragmentManager();
                FragmentTransaction fragmentTrans = fragmentMgr.beginTransaction();
                FeedbackFragment feedback_frag=new FeedbackFragment();
                fragmentTrans.replace(R.id.container,feedback_frag,"feedback");
                fragmentTrans.commit();
               //Toast.makeText(v.getContext(), ((Item)v.getTag()).getTitle(), Toast.LENGTH_SHORT).show();
               // getActionBar().setTitle("Test");
            }
        });
        return view;
    }

}
