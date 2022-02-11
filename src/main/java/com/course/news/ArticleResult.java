package com.course.news;

import java.io.Serializable;
import java.util.List;

public class ArticleResult implements Serializable {
    public String status;
    public int totalResults;
    public List<Article> articles;
}
