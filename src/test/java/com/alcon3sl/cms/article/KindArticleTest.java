package com.alcon3sl.cms.article;

import com.alcon3sl.cms.model.article.kind_article.KindArticle;
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
public class KindArticleTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnAllKindArticleWhenListIsRequested () {
        ResponseEntity<String> response = restTemplate.getForEntity("/kind_article", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number size = documentContext.read("$.totalElements");
        assertThat(size).isEqualTo(8);
    }

    @Test
    void shouldReturnAKindArticleWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity("/kind_article/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");

        assertThat(id).isEqualTo(1);
        assertThat(name).isEqualTo("kind article");
    }

    @Test
    void shouldReturnAKindArticleNotFoundWithUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/kind_article/0", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldReturnANewKindArticle() {
        KindArticle kindArticle = new KindArticle("test");
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/kind_article", kindArticle, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI newLocation = createResponse.getHeaders().getLocation();
        ResponseEntity<String> response = restTemplate.getForEntity(newLocation, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");

        assertThat(id).isNotNull();
        assertThat(name).isEqualTo("test");
    }

    @Test
    void shouldDeleteAnExistingKindArticle() {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/kind_article/9", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> response = restTemplate.getForEntity("/kind_Article/9", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldUpdateAnExistingKindArticle() {
        KindArticle kindArticle = new KindArticle("update_test");
        HttpEntity<KindArticle> httpEntity = new HttpEntity<>(kindArticle);
        ResponseEntity<Void> updateResponse = restTemplate.exchange(
                "/kind_article/2", HttpMethod.PUT, httpEntity, Void.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> response = restTemplate.getForEntity("/kind_article/2", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");

        assertThat(id).isEqualTo(2);
        assertThat(name).isEqualTo("update_test");
    }

    @Test
    void shouldNotUpdateAKindArticleThatDoesNotExist() {
        KindArticle kindArticle = new KindArticle("update_test");
        HttpEntity<KindArticle> httpEntity = new HttpEntity<>(kindArticle);
        ResponseEntity<Void> updateResponse = restTemplate.exchange(
                "/kind_article/0", HttpMethod.PUT, httpEntity, Void.class
        );
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
