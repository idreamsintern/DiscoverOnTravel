package com.example.idreams.dot.nearby;

import android.content.Context;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idreams.dot.R;

import java.util.ArrayList;

/**
 * Created by chichunchen on 2015/7/15.
 */
public class ShowNearbyAdapter extends ArrayAdapter<CheckIn> {
    ImageView fbimage;
    String MY_URL_STRING;

    public ShowNearbyAdapter(Context context, ArrayList<CheckIn> checkins) {
        super(context, 0, checkins);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CheckIn checkins = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.show_nearby_row, parent, false);
        }
        // Lookup view for data population
        //TextView tvId = (TextView) convertView.findViewById(R.id.show_id);
        CheckBox ckCheck = (CheckBox) convertView.findViewById(R.id.checkbox);
        TextView tvName = (TextView) convertView.findViewById(R.id.show_name);
        InputFilter[] namefilter = new InputFilter[1];
        namefilter[0] = new InputFilter.LengthFilter(15);
        tvName.setFilters(namefilter);
        TextView tvCheckins = (TextView) convertView.findViewById(R.id.show_checkins);
        // Populate the data into the template view using the data object
        // tvId.setText(checkins.id);
        // MY_URL_STRING = "https://graph.facebook.com/v2.4/"+checkins.id+"picture/";
        // new DownloadImageTask((ImageView) convertView.findViewById(R.id.facebookimage))
        // .execute(MY_URL_STRING);

        ckCheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(getContext(), "Check", Toast.LENGTH_LONG).show();
                    // TODO pass checkin to schedule.
                }
            }
        });
        tvName.setText(checkins.name);
        tvCheckins.setText(checkins.checkins);
        // Return the completed view to render on screen
        return convertView;
    }

//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            bmImage.setImageBitmap(result);
//        }
//    }
}