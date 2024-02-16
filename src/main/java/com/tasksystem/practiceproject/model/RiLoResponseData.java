package com.tasksystem.practiceproject.model;
import java.util.ArrayList;
import java.util.List;

public class RiLoResponseData {

    public List<AccessTicket> accessTickets = new ArrayList<>();
    public List<AccessTicket> getAccessTicket() {
        return accessTickets;
    }

    public void setAccessTicket(List<AccessTicket> accessTickets) {
        this.accessTickets = accessTickets;
    }
}