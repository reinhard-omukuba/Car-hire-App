package com.iqra.carrenting;

public class model {

    String captions;
    String car_model;
    String car_price;
    String engine_size;
    String captionsId;
    String image;
    String tweet;
    String tweet_id;


    public model() {

    }

    public model(String captions, String car_model, String car_price, String engine_size, String captionsId, String image, String tweet, String tweet_id) {
        this.captions = captions;
        this.car_model = car_model;
        this.car_price = car_price;
        this.engine_size = engine_size;
        this.captionsId = captionsId;
        this.image = image;
        this.tweet = tweet;
        this.tweet_id = tweet_id;
    }

    public String getCaptions() {
        return captions;
    }

    public void setCaptions(String captions) {
        this.captions = captions;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public String getCar_price() {
        return car_price;
    }

    public void setCar_price(String car_price) {
        this.car_price = car_price;
    }

    public String getEngine_size() {
        return engine_size;
    }

    public void setEngine_size(String engine_size) {
        this.engine_size = engine_size;
    }

    public String getCaptionsId() {
        return captionsId;
    }

    public void setCaptionsId(String captionsId) {
        this.captionsId = captionsId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getTweet_id() {
        return tweet_id;
    }

    public void setTweet_id(String tweet_id) {
        this.tweet_id = tweet_id;
    }
}
