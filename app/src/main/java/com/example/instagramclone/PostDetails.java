package com.example.instagramclone;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class PostDetails extends AppCompatActivity {

    public static final String TAG = "inside PostDetails.java";
    private TextView tvUsernameDetails;
    private TextView tvDescriptionDetails;
    private ImageView ivImageDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);



        // End of the Tunnel arriving from MovieDetailsActivites
        // movie ID gets the integer stored inside of movieid, sets it to 0 if null
        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));



        tvUsernameDetails = findViewById(R.id.tvDetailUsername);
        tvDescriptionDetails = findViewById(R.id.tvDescriptionDetails);
        ivImageDetails = findViewById(R.id.ivDetailImage);

        tvUsernameDetails.setText(post.getUser().getUsername());
        tvDescriptionDetails.setText(post.getDescription());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivImageDetails);
        }





    }
}