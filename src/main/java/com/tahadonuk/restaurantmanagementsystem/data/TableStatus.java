package com.tahadonuk.restaurantmanagementsystem.data;

public enum TableStatus {
    AVAILABLE("Available"), FULL("Full"), OUT_OF_SERVICE("Out of service");

    private final String statusText;
    TableStatus(String statusText) {
        this.statusText = statusText;
    }

    public String getStatusText() {
        return statusText;
    }
}
