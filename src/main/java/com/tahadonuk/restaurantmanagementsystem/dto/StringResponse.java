package com.tahadonuk.restaurantmanagementsystem.dto;

public class StringResponse {
    private String response = "";

    public StringResponse(String resp) {
        this.response = resp;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
