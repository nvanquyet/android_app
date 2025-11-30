package com.example.android_exam.core.error;

import android.util.Log;

import com.example.android_exam.data.dto.response.ApiResponse;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * Centralized error handling
 * Provides consistent error messages and error type detection
 */
public class ErrorHandler {
    private static final String TAG = "ErrorHandler";
    private static final String DEFAULT_ERROR_MESSAGE = "Đã xảy ra lỗi. Vui lòng thử lại sau.";

    /**
     * Extract error message from ApiResponse
     * According to API documentation, errors are in metadata.errors array
     */
    public static String extractErrorMessage(ApiResponse<?> response) {
        if (response == null) {
            return DEFAULT_ERROR_MESSAGE;
        }

        // First try to get message from response
        String message = response.getMessage();
        if (message != null && !message.isEmpty()) {
            return message;
        }

        // Check metadata for error details (as per API doc format)
        Map<String, List<String>> metadata = response.getMetadata();
        if (metadata != null) {
            List<String> errors = metadata.get("errors");
            if (errors != null && !errors.isEmpty()) {
                // Return first error message
                return errors.get(0);
            }
        }

        return DEFAULT_ERROR_MESSAGE;
    }

    /**
     * Extract error message from Throwable
     */
    public static String extractErrorMessage(Throwable throwable) {
        if (throwable == null) {
            return DEFAULT_ERROR_MESSAGE;
        }

        if (throwable instanceof SocketTimeoutException) {
            return "Kết nối quá thời gian. Vui lòng kiểm tra kết nối mạng và thử lại.";
        }

        if (throwable instanceof UnknownHostException) {
            return "Không thể kết nối đến server. Vui lòng kiểm tra kết nối mạng.";
        }

        if (throwable instanceof IOException) {
            return "Lỗi kết nối mạng. Vui lòng thử lại sau.";
        }

        String message = throwable.getMessage();
        if (message != null && !message.isEmpty()) {
            return message;
        }

        return DEFAULT_ERROR_MESSAGE;
    }

    /**
     * Log error with context
     */
    public static void logError(String context, Throwable throwable) {
        Log.e(TAG, "Error in " + context + ": " + throwable.getMessage(), throwable);
    }

    /**
     * Log error with message
     */
    public static void logError(String context, String message) {
        Log.e(TAG, "Error in " + context + ": " + message);
    }

    /**
     * Check if error is network related
     */
    public static boolean isNetworkError(Throwable throwable) {
        return throwable instanceof IOException ||
               throwable instanceof SocketTimeoutException ||
               throwable instanceof UnknownHostException;
    }

    /**
     * Check if error is server error (5xx)
     */
    public static boolean isServerError(int statusCode) {
        return statusCode >= 500 && statusCode < 600;
    }

    /**
     * Check if error is client error (4xx)
     */
    public static boolean isClientError(int statusCode) {
        return statusCode >= 400 && statusCode < 500;
    }
}

