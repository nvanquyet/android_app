package com.example.android_exam.core.result;

/**
 * Result wrapper for operations that can succeed or fail
 * Similar to Kotlin's Result type
 */
public class Result<T> {
    private final T data;
    private final String error;
    private final boolean isSuccess;

    private Result(T data, String error, boolean isSuccess) {
        this.data = data;
        this.error = error;
        this.isSuccess = isSuccess;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null, true);
    }

    public static <T> Result<T> error(String error) {
        return new Result<>(null, error, false);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isError() {
        return !isSuccess;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public T getDataOrThrow() {
        if (!isSuccess) {
            throw new IllegalStateException("Result is not successful: " + error);
        }
        return data;
    }
}

