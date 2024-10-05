package org.acme.dto;

public class ResponseDto<T> {

    private String status;
    private String message;
    private T data;

    // Constructors
    public ResponseDto() {
    }

    public ResponseDto(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    // Static utility methods for creating standardized responses
    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>("success", "Operation successful", data);
    }

    public static <T> ResponseDto<T> success(String message, T data) {
        return new ResponseDto<>("success", message, data);
    }

    public static <T> ResponseDto<T> error(String message) {
        return new ResponseDto<>("error", message, null);
    }
}
