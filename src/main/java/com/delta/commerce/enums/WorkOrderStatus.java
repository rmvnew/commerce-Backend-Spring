package com.delta.commerce.enums;

public enum WorkOrderStatus {

    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    PENDING("Pending"),
    AWAITING_APPROVAL("Awaiting Approval"),
    COMPLETED("Completed"),
    CANCELED("Canceled"),
    CLOSED("Closed");


    private final String name;

    WorkOrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
