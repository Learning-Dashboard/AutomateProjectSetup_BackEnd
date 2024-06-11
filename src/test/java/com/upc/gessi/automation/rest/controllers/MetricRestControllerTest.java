package com.upc.gessi.automation.rest.controllers;

import com.upc.gessi.automation.domain.controllers.MetricController;
import okhttp3.Request;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MetricRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    MetricController metricController;

    @InjectMocks
    MetricRestController metricRestController;

    @Mock
    private OkHttpClient okHttpClient;
    @Mock
    private Call call;

    @Mock
    private Response response;

   /* @BeforeEach
    public void setup() {
        // This initializes the mocks and creates the MockMvc instance
        mockMvc = MockMvcBuilders.standaloneSetup(metricRestController).build();
    }

    @Test
    void getMetricsTest() throws Exception {

        Request request = new Request.Builder()
                .url("http://host.docker.internal:8888/api/metrics/current?prj=test")
                .build();

        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);
        when(response.isSuccessful()).thenReturn(true);



        mockMvc.perform(get("/metric").param("name", "test"))
                .andExpect(status().isOk());
    }*/
}
