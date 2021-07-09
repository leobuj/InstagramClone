package com.example.instagramclone;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.Date;

public class PostDetails extends AppCompatActivity {

    public static final String TAG = "inside PostDetails.java";
    private TextView tvUsernameDetails;
    private TextView tvDescriptionDetails;
    private ImageView ivImageDetails;
    private ImageView ivProfileImageDetails;
    private TextView tvTimestampDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);



        // End of the Tunnel arriving from MovieDetailsActivites
        // movie ID gets the integer stored inside of movieid, sets it to 0 if null
        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));



        tvUsernameDetails = findViewById(R.id.tvDetailUsername);
        tvDescriptionDetails = findViewById(R.id.tvDescriptionDetails);
        tvTimestampDetails = findViewById(R.id.tvTimeStamp);
        ivImageDetails = findViewById(R.id.ivDetailImage);
        ivProfileImageDetails = findViewById(R.id.ivProfileDetails);

        tvUsernameDetails.setText(post.getUser().getUsername());
        tvDescriptionDetails.setText(post.getDescription());

        String timestamp = calculateTimeAgo(post.getCreatedAt());
        tvTimestampDetails.setText(timestamp);
        Log.e("Iinside PostDetails", "timestamp is: " + timestamp);
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivImageDetails);
        }

        ParseFile profileImage = post.getUserProfileImage();
        if (profileImage != null) {
            Glide.with(this).load(profileImage.getUrl()).circleCrop().into(ivProfileImageDetails);
        }


    }


    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }

        return "";
    }


}