package com.course.news;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

@RestController
public class ArticlesController {
    public static final String dirForFile = "/home/oskar/articles";
    private final RestTemplate restTemplate;

    public ArticlesController(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping("articles/newFile")
    public String generateNewFile(@RequestParam(defaultValue = "") String name) {
        File dir = new File("/home/oskar");
        File dir2 = (new File(dirForFile));
        if (!dir.exists()) {
            dir.mkdir();
            (new File(dirForFile)).mkdir();
        }
        if (!dir2.exists()) {
            dir2.mkdir();
        }
        String url = "https://newsapi.org/v2/top-headlines?country=pl&category=business&apiKey=604d6c8ec9c849819c04b02b48c00cfa";
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String json = this.restTemplate.getForObject(url, String.class);

        ArticleResult articleResult = gson.fromJson(json, ArticleResult.class);
        String result = "";
        for (Article article: articleResult.articles) {
            result += article.title + ":" + article.description + ":" + article.author + "\r\n";
        }

        try {
            FileWriter fileWriter = new FileWriter(FileController.dirForFile+"/text.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(result);
            printWriter.close();
        } catch (Exception e) {

        }

        return result;
    }
}
