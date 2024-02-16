package com.tasksystem.practiceproject.client;

import java.io.IOException;

public class RiLoResponseException extends Exception {

//    public RiLoResponseException(String message) {
//        super(message);
//    }

    public RiLoResponseException(IOException cause) {
//        super(cause);
//        System.out.println("###Message = ");
        System.out.println("#######Cause = " + cause + " END########");
    }
}
