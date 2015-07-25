package com.example.idreams.dot.chat;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.idreams.dot.R;
import com.facebook.Profile;
import com.firebase.client.Query;
import com.squareup.picasso.Picasso;

/**
 * @author greg
 * @since 6/21/13
 * <p/>
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class ChatListAdapter extends FirebaseListAdapter<Chat> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;

    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, Chat.class, layout, activity);
        this.mUsername = mUsername;
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param chat An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, Chat chat) {
        // Map a Chat object to an entry in our
        String author = chat.getAuthor();
        String id     = chat.getId();
        TextView authorText = (TextView) view.findViewById(R.id.author);
        // Profile currentProfile = Profile.getCurrentProfile();

        authorText.setText(author + ": ");
        // If the message was sent by this user, color it differently
        if (author != null && author.equals(mUsername)) {
            authorText.setTextColor(Color.RED);
        } else {
            authorText.setTextColor(Color.BLUE);
        }
        ((TextView) view.findViewById(R.id.message)).setText(chat.getMessage());

        // set facebook user thumbnail
        String thumbnail_address = "https://graph.facebook.com/v2.4/" + id + "/picture/";
        ImageView personalThumbnail = (ImageView) view.findViewById(R.id.personal_thumbnail);
        Picasso.with(view.getContext())
                .load(thumbnail_address)
                .into(personalThumbnail);
    }
}
