package com.sample.agora.util;


public class PostItem
{
    public String getPostPermalink()
    {
        return postPermalink;
    }

    public void setPostPermalink(String postPermalink)
    {
        this.postPermalink = postPermalink;
    }

    private String postImageUrl, postTitle, postText, postVotes, postPermalink;
    private long postTime;

    public String getPostImageUrl()
    {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl)
    {
        this.postImageUrl = postImageUrl;
    }

    public String getPostTitle()
    {
        return postTitle;
    }

    public void setPostTitle(String postTitle)
    {
        this.postTitle = postTitle;
    }

    public String getPostText()
    {
        return postText;
    }

    public void setPostText(String postText)
    {
        this.postText = postText;
    }

    public String getPostVotes()
    {
        return postVotes;
    }

    public void setPostVotes(String postVotes)
    {
        this.postVotes = postVotes + " points";
    }

    public long getPostTime()
    {
        return postTime;
    }

    public void setPostTime(long postTime)
    {
        this.postTime = postTime;
    }
}
