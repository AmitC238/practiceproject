package com.tasksystem.practiceproject.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasksystem.practiceproject.model.RiLoResponseData;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // Add this annotation to mark this class as a Spring-managed component
public class RiLoResponse {

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;



    public RiLoResponse(OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    public RiLoResponseData getAccessTickets(String householdId) throws IOException {
        String riloUrl = "http://localhost:9000/rilo";
        HttpUrl url = HttpUrl.parse(riloUrl + "/access-tickets/" + householdId);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = okHttpClient.newCall(request).execute()){
            if(!response.isSuccessful()){
                throw new IOException("Unexpected response code: " + response);
            }

            assert response.body() != null;
            return getRiLoResponseData(response.body().string());
        }
    }

    private RiLoResponseData getRiLoResponseData(String responseBody) throws JsonProcessingException {
        return objectMapper.readValue(responseBody, RiLoResponseData.class);
    }
}
