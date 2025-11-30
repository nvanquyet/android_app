package com.example.android_exam.core.usecase;

import com.example.android_exam.core.result.Result; /**
 * Use case without parameters
 */
public interface UseCaseNoParams<Response> {
    Result<Response> execute();
}
