package com.example.Ecommerce_Clothing_Server.dto.response;


public record ApiResponseDTO<T> (
    boolean success,
    String message,
    T data
) {
    // 成功回應的靜態工廠方法
    public static <T> ApiResponseDTO<T> success(String message, T data) {
        return new ApiResponseDTO<>(true, message, data);
    }

    // 失敗回應的靜態工廠方法
    public static <T> ApiResponseDTO<T> error(String message) {
        return new ApiResponseDTO<>(false, message, null);
    }
}