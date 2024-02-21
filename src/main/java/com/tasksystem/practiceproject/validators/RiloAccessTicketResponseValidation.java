package com.tasksystem.practiceproject.validators;

import com.tasksystem.practiceproject.Exceptions.RiLoResponseException;
import com.tasksystem.practiceproject.model.AccessTicket;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class RiloAccessTicketResponseValidation {

    public Boolean validateRiloAccessTicketResponse(List<AccessTicket> riloAccessTicketResponse) {
        Field[] allFields = AccessTicket.class.getDeclaredFields();
        for (AccessTicket ticket : riloAccessTicketResponse) {
            if (!Arrays.stream(allFields).allMatch(field -> {
                field.setAccessible(true);
                try {
                    return field.get(ticket) != null && field.get(ticket) != "";
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            })) {
                return false;
            }
        }
        return true;
    }
}
