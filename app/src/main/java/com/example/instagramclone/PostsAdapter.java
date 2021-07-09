package com.example.instagramclone;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    public static final String TAG = "inside PostsAdapter";
    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvUsername;
        private TextView tvUsernameCaption;
        private TextView tvDescription;
        private ImageView ivImage;
        private ImageView ivProfileImage;
        private ImageButton ibLike;
        private TextView tvLikeCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvUsernameCaption = itemView.findViewById(R.id.tvUsernameCaption);
            tvLikeCount = itemView.findViewById(R.id.tvNumberOfLikes);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            ibLike = itemView.findViewById(R.id.ibLike);
            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {
            // Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvUsernameCaption.setText(post.getUser().getUsername());
            tvLikeCount.setText(post.getLikes());

            ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int likes = Integer.parseInt((String) tvLikeCount.getText());
                    likes++;
                    tvLikeCount.setText(String.valueOf(likes));
                    int position = getAdapterPosition();
                    Post post = posts.get(position);
                    post.setLikes(likes);
                    Log.i("Inside saveCallBack", "post timestamp was " + post.getCreatedAt());
                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Glide.with(context).load(R.drawable.ufi_heart_active).into(ibLike);
                            Log.i("Inside saveCallBack", "post successfully liked");

                        }
                    });
                    Log.i(TAG, "Trying to like post at position= " + getAdapterPosition() + "\n Number of likes it has now is " + post.getLikes());

                }
            });

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            ParseFile profileImage = post.getUserProfileImage();
            if (profileImage != null) {
                Glide.with(context).load(profileImage.getUrl()).circleCrop().into(ivProfileImage);
            }
        }

        @Override
        public void onClick(View v) {
            // collects position
            int position = getAdapterPosition();
            Log.i(TAG, "Post position= " + getAdapterPosition());
            // verifies this position exists
            if (position != RecyclerView.NO_POSITION){

                // collects movie at said position
                Post post = posts.get(position);

                // creates intent for new activity
                Intent intent = new Intent(context, PostDetails.class);

                intent.putExtra("post", Parcels.wrap(post));

                // show activity
                context.startActivity(intent);
            }
        }

    }


    /* Within the RecyclerView.Adapter class */
    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

}
