package com.technicaltest.ecommerce.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ApiResponse<T> {
    @Schema(description = "Response message")
    private String message;
    @Schema(description = "Response data")
    private T data;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "ApiResponse{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
