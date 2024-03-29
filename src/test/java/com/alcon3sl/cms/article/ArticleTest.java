package com.alcon3sl.cms.article;

import com.alcon3sl.cms.model.article.article.Article;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnAllArticleWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate.getForEntity("/article", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnAnArticleWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity("/article/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");
        Double initialCost = documentContext.read("$.initialCost");

        assertThat(id).isEqualTo(1);
        assertThat(name).isEqualTo("test");
        assertThat(initialCost).isEqualTo(200.00);
    }

    @Test
    void shouldReturnAnArticleNotFoundWithUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/article/0", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldCreateANewArticle() {
        Article article = new Article();
        article.setName("article");
        article.setInitialCost(200.00);
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/article", article, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI newLocation = createResponse.getHeaders().getLocation();
        ResponseEntity<String> response = restTemplate.getForEntity(newLocation, String.class);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");
        Double initialCost = documentContext.read("$.initialCost");

        assertThat(id).isNotNull();
        assertThat(name).isEqualTo("article");
        assertThat(initialCost).isEqualTo(200.00);
    }

    @Test
    void shouldDeleteAnExistingArticle() {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/article/1", HttpMethod.DELETE, null, Void.class
        );
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> response = restTemplate.getForEntity("/article/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldUpdateAnExistingArticle() {
        Article article = new Article();
        article.setName("New article");
        article.setInitialCost(100.00);
        HttpEntity<Article> httpEntity = new HttpEntity<>(article);
        ResponseEntity<Void> updateResponse = restTemplate.exchange(
                "/article/1", HttpMethod.PUT, httpEntity, Void.class
        );
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> response = restTemplate.getForEntity("/article/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");
        Double initialCost = documentContext.read("$.initialCost");

        assertThat(id).isEqualTo(1);
        assertThat(name).isEqualTo("New article");
        assertThat(initialCost).isEqualTo(100.00);
    }

    @Test
    void shouldNotUpdateAnArticleThatDoesNotExist() {
        Article article = new Article();
        article.setName("New article");
        article.setInitialCost(100.00);
        HttpEntity<Article> httpEntity = new HttpEntity<>(article);
        ResponseEntity<Void> updateResponse = restTemplate.exchange(
                "/article/0", HttpMethod.PUT, httpEntity, Void.class
        );
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
