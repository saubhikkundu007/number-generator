package numbergen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import numbergen.Application;
import numbergen.domain.JobStatus;
import numbergen.model.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JobControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void before() {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testAllApis() throws IOException {

        HttpEntity<String> postEntity = new HttpEntity<String>(
                "{\"Goal\": \"10\", \"Step\": \"2\"}", headers);
        HttpEntity<String> getEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> responseGenerateApi =  restTemplate.exchange(createURLWithPort("/api/generate"), HttpMethod.POST, postEntity, String.class);
        assertEquals(202, responseGenerateApi.getStatusCode().value());

        Task task = objectMapper.readValue(responseGenerateApi.getBody(), Task.class);
        ResponseEntity<String> responseStatusApi = restTemplate.exchange(
                createURLWithPort("/api/tasks/"+task.getTask()+"/status"),
                HttpMethod.GET, getEntity, String.class);
        assertEquals("{\"result\":\"SUCCESS\"}", responseStatusApi.getBody());


        ResponseEntity<String> responseTaskApi = restTemplate.exchange(
                createURLWithPort("/api/tasks/"+task.getTask()+"?action=get_numlist"),
                HttpMethod.GET, getEntity, String.class);
        assertEquals("{\"result\":\"10,8,6,4,2,0\"}", responseTaskApi.getBody());

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }



}