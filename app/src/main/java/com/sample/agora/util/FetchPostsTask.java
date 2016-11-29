package com.sample.agora.util;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FetchPostsTask extends AsyncTask<String, Void, ArrayList<PostItem>>
{
    FetchPostsAsyncListener fetchPostsAsyncListener;

    public FetchPostsTask(FetchPostsAsyncListener fetchPostsAsyncListener)
    {
        this.fetchPostsAsyncListener = fetchPostsAsyncListener;

    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<PostItem> doInBackground(String... params)
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String postJsonStr = null;

        try
        {
            String REDDIT_BASE_URL = "https://www.reddit.com/.json";

            Uri builtUri = Uri.parse(REDDIT_BASE_URL).buildUpon().build();
            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Alien V1.0");
            urlConnection.setReadTimeout(30000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
        }
        catch (MalformedURLException e)
        {
            Log.e("getConnection()", "Invalid URL: " + e.toString());
        }
        catch (IOException e)
        {
            Log.e("getConnection()", "Could not connect: "+e.toString());
        }

        try
        {

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
            {
                Log.e("Err", "Received null");
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0)
            {
                Log.e("Err", "Received empty");
                return null;
            }
            else
            {
                Log.e("Err", String.valueOf(buffer.length()));
            }
            postJsonStr = buffer.toString();

            return getPostDataFromJson(postJsonStr);
        }
        catch (Exception e)
        {
            Log.e("Main Activity", "Error ", e);
            return null;
        }
        finally
        {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (final IOException e)
                {
                    Log.e("Main Activity", "Error ", e);
                }
            }
        }
    }

    @Override
    protected void onPostExecute(ArrayList<PostItem> postItems)
    {
        super.onPostExecute(postItems);
        fetchPostsAsyncListener.onPostExecute(postItems);
    }


    private ArrayList<PostItem> getPostDataFromJson(String response) throws JSONException
    {
        final String FEED_DATA = "data";
        final String POST_LIST = "children";
        final String POST_DATA = "data";

        ArrayList<PostItem> postItems = new ArrayList<>();

        JSONObject postJson = new JSONObject(response);
        JSONObject data = postJson.getJSONObject(FEED_DATA);
        JSONArray postsArray = data.getJSONArray(POST_LIST);

        for (int i = 0; i < postsArray.length(); i++)
        {
            JSONObject topic = postsArray.getJSONObject(i).getJSONObject(POST_DATA);
            PostItem postItem = new PostItem();

            String postTitle = topic.getString("title");
            postItem.setPostTitle(postTitle);

            String postText = topic.getString("selftext");
            postItem.setPostText(postText);

            long postTime = topic.getLong("created_utc");
            postItem.setPostTime(postTime);

            String postVotes = topic.getString("score");
            postItem.setPostVotes(postVotes);

            String postLink = topic.getString("permalink");
            postItem.setPostPermalink(postLink);

            if (topic.has("preview"))
            {
                JSONObject preview = topic.getJSONObject("preview");
                JSONArray images = preview.getJSONArray("images");
                JSONObject source = images.getJSONObject(0);
                JSONObject source2 = source.getJSONObject("source");

                String url = source2.optString("url");
                postItem.setPostImageUrl(url);
            }
            else
            {
                postItem.setPostImageUrl(null);
            }

            postItems.add(postItem);
        }

        return postItems;

    }


    public interface FetchPostsAsyncListener
    {
        void onPostExecute(ArrayList<PostItem> postItems);
    }


}