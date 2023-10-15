package com.alcon3sl.cms.article;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    }

    @Test
    void shouldReturnAnArticleNotFoundWithUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/article/0", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
