package com.sample.agora.util;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sample.agora.R;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostsRecylerAdapter extends RecyclerView.Adapter<PostsRecylerAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<PostItem> postItems;
    PostItemClickHandler postItemClickHandler;

    public PostsRecylerAdapter(ArrayList<PostItem> postItems, Context contextParam)
    {
        this.postItems = postItems;
        context = contextParam;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        if(postItems.get(position).getPostImageUrl() == null)
        {
            holder.postImage.setVisibility(View.GONE);
        }
        else
        {
            holder.postImage.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(postItems.get(position).getPostImageUrl())
                    .asBitmap()
                    .into(holder.postImage);
        }
        System.out.println(postItems.get(position).getPostImageUrl());
        holder.postTitle.setText(postItems.get(position).getPostTitle());
        if(postItems.get(position).getPostText().length() > 1)
        {
            Log.e("Text", postItems.get(position).getPostText());
            holder.postText.setText(postItems.get(position).getPostText());
            holder.postText.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.postText.setVisibility(View.GONE);
        }
        holder.postVotes.setText(postItems.get(position).getPostVotes());
        holder.postTime.setText(getTimeAgo(postItems.get(position).getPostTime()));

    }


    @Override
    public int getItemCount()
    {
        return postItems.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.row_container)
        CardView rowContainer;
        @BindView(R.id.post_image)
        ImageView postImage;
        @BindView(R.id.post_title)
        TextView postTitle;
        @BindView(R.id.post_text)
        TextView postText;
        @BindView(R.id.post_votes)
        TextView postVotes;
        @BindView(R.id.post_time)
        TextView postTime;

        ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


        @OnClick(R.id.row_container)
        public void gridItemClicked()
        {
            int itemPosition = getAdapterPosition();
            postItemClickHandler.onPostItemClick(postItems.get(itemPosition).getPostPermalink());
        }

    }

    public interface PostItemClickHandler
    {
        void onPostItemClick(String selectedPostItemLink);
    }

    public void setOnPostItemClickListener(PostItemClickHandler postItemClickHandler)
    {
        this.postItemClickHandler = postItemClickHandler;
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time)
    {
        if (time < 1000000000000L)
        {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0)
        {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS)
        {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS)
        {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS)
        {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS)
        {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS)
        {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS)
        {
            return "yesterday";
        } else
        {
            return diff / DAY_MILLIS + " days ago";
        }
    }
}
