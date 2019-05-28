package com.android.gezginapp.models;

public class PostModel {

    int postPicture;
    String postName;
    String postDescription;

    public PostModel(){

    }
    public PostModel(int postPicture, String postName, String postDescription) {
        this.postPicture = postPicture;
        this.postName = postName;
        this.postDescription = postDescription;
    }

    public int getPostPicture() {
        return postPicture;
    }

    public String getPostName() {
        return postName;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostPicture(int postPicture) {
        this.postPicture = postPicture;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }
}
