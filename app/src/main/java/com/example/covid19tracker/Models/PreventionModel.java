package com.example.covid19tracker.Models;

public class PreventionModel {

    int preventionImage;
    String preventionName;

    public PreventionModel(int preventionImage, String preventionName) {
        this.preventionImage = preventionImage;
        this.preventionName = preventionName;
    }

    public int getPreventionImage() {
        return preventionImage;
    }

    public void setPreventionImage(int preventionImage) {
        this.preventionImage = preventionImage;
    }

    public String getPreventionName() {
        return preventionName;
    }

    public void setPreventionName(String preventionName) {
        this.preventionName = preventionName;
    }
}
