package com.tasksystem.practiceproject.model;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class AccessTicket {

    private UUID id;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private String productEventId;
    private String productId;
    private String productStaticId;

    public AccessTicket(UUID id, String name, Instant startDate, Instant endDate, String productEventId, String productId, String productStaticId){
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productEventId = productEventId;
        this.productId = productId;
        this.productStaticId = productStaticId;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getProductEventId() {
        return productEventId;
    }

    public void setProductEventId(String productEventId) {
        this.productEventId = productEventId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductStaticId() {
        return productStaticId;
    }

    public void setProductStaticId(String productStaticId) {
        this.productStaticId = productStaticId;
    }

    @Override
    public String toString() {
        return "AccessTicket{" +
                " name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", productEventId='" + productEventId + '\'' +
                '}';
    }
}