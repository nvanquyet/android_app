package com.example.android_exam.core.usecase;

import com.example.android_exam.core.result.Result;

/**
 * Base interface for Use Cases (Clean Architecture)
 * Use cases encapsulate business logic
 */
public interface UseCase<Params, Response> {
    Result<Response> execute(Params params);
}

