package com.example.covid19tracker.Models;

import java.util.List;

public class NewsModel {

    private String status;
    List<ArticlesModel> articles;

    public NewsModel(String status, List<ArticlesModel> articles) {
        this.status = status;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ArticlesModel> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesModel> articles) {
        this.articles = articles;
    }
}
