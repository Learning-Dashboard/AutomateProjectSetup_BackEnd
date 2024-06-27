package com.upc.gessi.automation.domain.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StrategicIndicatorControllerTest {

    @InjectMocks
    private StrategicIndicatorController strategicIndicatorController;

    @Mock
    private OkHttpClient okHttpClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createStrategicIndicatorTest() throws IOException {
        // Arrange
        JsonArray factors = new JsonArray();

        JsonObject factor1 = new JsonObject();
        factor1.addProperty("id", 1);
        factor1.addProperty("externalId", "fulfillmentoftasks");
        factors.add(factor1);

        JsonObject factor2 = new JsonObject();
        factor2.addProperty("id", 2);
        factor2.addProperty("externalId", "commitstasksrelation");
        factors.add(factor2);

        JsonObject factor3 = new JsonObject();
        factor3.addProperty("id", 3);
        factor3.addProperty("externalId", "commits");
        factors.add(factor3);

        // Mock OkHttpClient to return a successful response
        ResponseBody responseBody = mock(ResponseBody.class);
        when(responseBody.string()).thenReturn("Success");

        Response response = mock(Response.class);
        when(response.body()).thenReturn(responseBody);
        when(response.isSuccessful()).thenReturn(true);

        Call call = mock(Call.class);
        when(call.execute()).thenReturn(response);
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);

        // Act
        strategicIndicatorController.createStrategicIndicator("test", factors);

        // Assert
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        verify(okHttpClient, times(3)).newCall(requestCaptor.capture());

        List<Request> capturedRequests = requestCaptor.getAllValues();
        for (Request request : capturedRequests) {
            HttpUrl url = request.url();
            String project = url.queryParameter("prj");
            assertEquals("test", project);
        }
    }
}
