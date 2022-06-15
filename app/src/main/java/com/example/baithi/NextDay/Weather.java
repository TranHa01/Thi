package com.example.baithi.NextDay;

public class Weather {
    String currentTime, state, urlIcon, min, max;

    public Weather(String currentTime, String state, String urlIcon, String min, String max) {
        this.currentTime = currentTime;
        this.state = state;
        this.urlIcon = urlIcon;
        this.min = min;
        this.max = max;
    }
    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrlIcon() {
        return urlIcon;
    }

    public void setUrlIcon(String urlIcon) {
        this.urlIcon = urlIcon;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}

