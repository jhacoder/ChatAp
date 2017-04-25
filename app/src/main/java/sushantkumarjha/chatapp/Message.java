package sushantkumarjha.chatapp;

import android.support.v7.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Sushant kumar jha on 01-04-2017.
 */

public class Message {
    private String mText;
    private String mName;
    private long mTime;
    private String mphotoUrl;
    private String mProfieUrl;
    public Message() {
    }

    public Message(String text, String name,String photoUrl,String profileUrl) {
        this.mText = text;
        this.mName = name;
        this.mphotoUrl=photoUrl;
        this.mProfieUrl=profileUrl;
       mTime=new Date().getTime();

    }

    public String getProfieUrl() {
        return mProfieUrl;
    }

    public void setProfieUrl(String profieUrl) {
        this.mProfieUrl = profieUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        this.mTime = time;
    }

    public String getPhotoUrl() {
        return mphotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.mphotoUrl = photoUrl;
    }
}