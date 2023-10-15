package com.alcon3sl.cms.specialty;

import com.alcon3sl.cms.model.article.specialty.Specialty;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpecialtyTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void shouldReturnASpecialtyWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity("/specialty/7", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(7);
    }

    @Test
    public void shouldReturnASpecialtyNotFoundExceptionWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/specialty/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        String message = documentContext.read("$.message");
        assertThat(message).isEqualTo("Specialty not found");
    }

    @Test
    public void shouldReturnAllSpecialtyWhenListIsRequested() throws JSONException {
        ResponseEntity<String> response = restTemplate.getForEntity("/specialty?page=0&size=1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        int size = (int) (new JSONObject(page.get(1).toString())).get("pageSize");
        assertThat(size).isEqualTo(1);
    }
}
