package com.example.instagramclone.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.instagramclone.Post;
import com.example.instagramclone.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private ImageView ivProfilePicture;
    private TextView tvFollowers;
    private TextView tvUsername;
    private TextView tvFollowing;
    private TextView tvPosts;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivProfilePicture = view.findViewById(R.id.ivProfilePicProfile);
        //ParseFile image = post.getImage();

        Post test = new Post();                             // Empty post to go directly from Post-> User to get user pfp
        test.setUser(ParseUser.getCurrentUser());
        tvFollowers = view.findViewById(R.id.tvNumberOfFollowers);
        tvUsername = view.findViewById(R.id.tvUsernameProfile);


        ParseUser user = ParseUser.getCurrentUser();


            user.fetchInBackground(new GetCallback<ParseUser>() {
                @Override
                public void done(ParseUser user, ParseException e) {
                int followCount =   user.getInt("followers");
                    Log.d(TAG, "onViewCreated: " + followCount);
                    tvFollowers.setText(String.valueOf(followCount));
                    tvUsername.setText(test.getUser().getUsername());
                }
            });









        ParseFile profileImage = test.getUserProfileImage();
        Glide.with(getContext()).load(profileImage.getUrl()).circleCrop().into(ivProfilePicture);


    }

    ParseUser getAccount() {
        Post post = new Post();
        ParseUser User = ParseUser.getCurrentUser();
        return User;

    }

}