package com.alcon3sl.cms.article;

import com.alcon3sl.cms.model.article.manufacturer.Manufacturer;
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
public class ManufacturerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnAllManufacturerWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate.getForEntity("/manufacturer", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnAManufacturerWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity("/manufacturer/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");

        assertThat(id).isEqualTo(1);
        assertThat(name).isEqualTo("Genebre");
    }

    @Test
    void shouldReturnAManufacturerNotFoundWithUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/manufacturer/0", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldCreateANewManufacturer() {
        Manufacturer manufacturer = new Manufacturer("test", null);
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/manufacturer", manufacturer, Void.class);
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
    void shouldDeleteAnExistingManufacturer() {
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/manufacturer/1", HttpMethod.DELETE, null, Void.class
        );
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> response = restTemplate.getForEntity("/manufacturer/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldUpdateAnExistingManufacturer() {
        Manufacturer manufacturer = new Manufacturer("new_test", null);
        HttpEntity<Manufacturer> httpEntity = new HttpEntity<>(manufacturer);
        ResponseEntity<Void> updateResponse = restTemplate.exchange(
                "/manufacturer/1", HttpMethod.PUT, httpEntity, Void.class
        );
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> response = restTemplate.getForEntity("/manufacturer/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        String name = documentContext.read("$.name");

        assertThat(id).isEqualTo(1);
        assertThat(name).isEqualTo("new_test");
    }

    @Test
    void shouldNotUpdateAManufacturerThatDoesNotExist() {
        Manufacturer manufacturer = new Manufacturer("new_test", null);
        HttpEntity<Manufacturer> httpEntity = new HttpEntity<>(manufacturer);
        ResponseEntity<Void> updateResponse = restTemplate.exchange(
                "/manufacturer/0", HttpMethod.PUT, httpEntity, Void.class
        );
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
