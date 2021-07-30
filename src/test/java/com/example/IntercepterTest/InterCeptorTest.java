package com.example.IntercepterTest;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.HashMap;


@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class InterCeptorTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    TestRestController controller;
    @Autowired
    APIInterceptor interceptor;

    @BeforeEach
    public void before() throws IOException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .addInterceptors(interceptor)
                .build();
    }
    @Test
    public void testGET() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "token_TEST_GET");
        headers.setContentType(MediaType.APPLICATION_JSON);
        /** Hit the API */
        mockMvc.perform(MockMvcRequestBuilders.get("/api/get")
                .headers(headers)
                .queryParam("type","GET_TEST")
                .queryParam("name","getRequest"));
    }
    @Test
    public void testPOST() throws Exception {
        HashMap<String,String> body = new HashMap<>();
        body.put("type","POST TEST");
        body.put("NAME","test123");
        body.put("date","2021-07-30");

        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "token_TEST_POST");
        headers.setContentType(MediaType.APPLICATION_JSON);
        /** API call */
        mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                .headers(headers)
                .content(body.toString()));
    }
    @Test
    public void testPUT() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "token_TEST_PUT");
        headers.setContentType(MediaType.APPLICATION_JSON);
        /** API call */
        mockMvc.perform(MockMvcRequestBuilders.put("/api/put")
                .headers(headers)
                .queryParam("type","PUT_TEST")
                .queryParam("name","PUTRequest"));
    }
    @Test
    public void testDELETE() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "token_TEST_DELETE");
        headers.setContentType(MediaType.APPLICATION_JSON);
        /** API call */
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete")
                .headers(headers)
                .queryParam("type","delete_TEST")
                .queryParam("name","deleteRequest"));
    }



}
