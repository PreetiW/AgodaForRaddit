package com.sample.agora.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.sample.agora.util.PostsRecylerAdapter;

import com.sample.agora.R;
import com.sample.agora.util.FetchPostsTask;
import com.sample.agora.util.PostItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FetchPostsTask.FetchPostsAsyncListener, PostsRecylerAdapter.PostItemClickHandler
{
    RecyclerView postsRecyclerView;
    PostsRecylerAdapter postsRecyclerAdapter;
    ArrayList<PostItem> postItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        postsRecyclerView = (RecyclerView) findViewById(R.id.posts_recyclerview);
        setSupportActionBar(toolbar);

        postItems = new ArrayList<>();
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        postsRecyclerAdapter = new PostsRecylerAdapter(postItems, this);
        postsRecyclerView.setAdapter(postsRecyclerAdapter);
        postsRecyclerAdapter.setOnPostItemClickListener(this);

        new FetchPostsTask(this).execute();
    }

    @Override
    public void onPostExecute(ArrayList<PostItem> postItems)
    {
        if(postItems != null)
        {
            this.postItems.clear();
            this.postItems.addAll(postItems);
            postsRecyclerAdapter.notifyDataSetChanged();
        }
        else if(postItems == null)
        {
            Toast.makeText(this, "No data received", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onPostItemClick(String postItemLink)
    {
        /* Using Chrome Custom Tabs instead of a WebView
         * This is now the recommended method by Google due to its efficiency
         * It also allows theming and seamless switching between browser and in-app browser
         */
        String articleUrl = "http://reddit.com" + postItemLink;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(Color.parseColor("#ffffff"));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(articleUrl));
    }

}
