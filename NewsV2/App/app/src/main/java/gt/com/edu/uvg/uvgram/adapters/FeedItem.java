package gt.com.edu.uvg.uvgram.adapters;

/**
 * Created by josejoescobar on 4/25/2017.
 */

public class FeedItem {
    private String tweetID, tweetText, imageUrl, ext, time;
    private String profilePic = "https://media.licdn.com/mpr/mpr/shrink_200_200/AAEAAQAAAAAAAAIQAAAAJGQyMWQxZDI0LWNjMmYtNDYzZi1hZTIyLWU0MTE2YWY1ZDNlNg.png";
    private static final String URL = "http://ec2-54-213-143-106.us-west-2.compute.amazonaws.com/images/";

    public FeedItem() {

    }

    public FeedItem(String tweetID, String tweetText, String ext, String time) {
        this.tweetID = tweetID;
        this.tweetText = tweetText;
        this.ext = ext;
        this.imageUrl = URL + tweetID + ext;
        this.time = time;

    }

    public String getTime() {
        return time;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public String getTweetID() {
        return tweetID;
    }

    public void setTweetID(String tweetID) {
        this.tweetID = tweetID;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public static String getURL() {
        return URL;
    }
}
