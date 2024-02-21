package com.tasksystem.practiceproject.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasksystem.practiceproject.Exceptions.GlobalExceptionHandler;
import com.tasksystem.practiceproject.Exceptions.RiLoResponseException;
import com.tasksystem.practiceproject.model.RiLoResponseData;
import com.tasksystem.practiceproject.validators.RiloAccessTicketResponseValidation;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.event.FocusEvent;
import java.io.IOException;

import static com.tasksystem.practiceproject.model.ErrorDetails.ErrorConstants.RILO_DOWNSTREAM_FAILURE;

@Component // Add this annotation to mark this class as a Spring-managed component
public class RiLoResponse {

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final RiloAccessTicketResponseValidation riloAccessTicketResponseValidation;



    public RiLoResponse(OkHttpClient okHttpClient, ObjectMapper objectMapper, RiloAccessTicketResponseValidation riloAccessTicketResponseValidation) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
        this.riloAccessTicketResponseValidation = riloAccessTicketResponseValidation;
    }

    public RiLoResponseData getAccessTickets(String householdId) throws IOException, RiLoResponseException {
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

    private RiLoResponseData getRiLoResponseData(String responseBody) throws JsonProcessingException, RiLoResponseException {
//        System.out.println(responseBody);
        RiLoResponseData riloResponse = null;

        try {
            riloResponse = objectMapper.readValue(responseBody, RiLoResponseData.class);
        } catch (JsonProcessingException e) {
//            throw new RiLoResponseException(e);
            if(e.getMessage().startsWith("Unexpected character")) {
//                metricsRecorder.recordDownStreamResponse(DownstreamJSONError.MALFORMED_ATTRIBUTE.getError(), dependency, resource, HttpStatus.INTERNAL_SERVER_ERROR.value());
                logger.error(String.format("An error has occurred with JSON parse: %s", "malformed_attribute", e));
            }
            else if(e.getMessage().startsWith("Unrecognized token")){
//                metricsRecorder.recordDownStreamResponse(DownstreamJSONError.MALFORMED_FIELD.getError(), dependency, resource, HttpStatus.INTERNAL_SERVER_ERROR.value());
                logger.error(String.format("An error has occurred with JSON parse: %s", "malformed_field", e));
            }
            else if (e.getMessage().startsWith("Unrecognized field")){
//                metricsRecorder.recordDownStreamResponse(DownstreamJSONError.MISSING_ATTRIBUTE.getError(), dependency, resource, HttpStatus.INTERNAL_SERVER_ERROR.value());
                logger.error(String.format("An error has occurred with JSON parse: %s", "missing_attribute", e));
            }
            else {
//                metricsRecorder.recordDownStreamResponse(DownstreamJSONError.UNKNOWN_ERROR.getError(),dependency,resource,HttpStatus.INTERNAL_SERVER_ERROR.value());
                logger.error(e.getMessage(), e);
            }
            throw new RiLoResponseException(RILO_DOWNSTREAM_FAILURE);
        }

        if(riloAccessTicketResponseValidation.validateRiloAccessTicketResponse(riloResponse.getAccessTicket())) {
            logger.info("parse successful");
            return riloResponse;
        } else {
            logger.error("An error has occurred with Json parse: " + "missing_field");
            throw new RiLoResponseException(RILO_DOWNSTREAM_FAILURE);
        }
//        return riloResponse;


        }

    }


