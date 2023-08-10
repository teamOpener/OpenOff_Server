package com.example.openoff.common.config.openfeign;

import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class OpenFeignError implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        String responseBody = "";

        // 응답 본문을 읽습니다.
        if (response.body() != null) {
            try {
                responseBody = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                log.error("Failed to read response body", e);
            }
        }

        switch (response.status()) {
            case 400:
                logErrorResponse(response, responseBody);
                return BusinessException.of(Error.DATA_NOT_FOUND);
            case 401:
                logErrorResponse(response, responseBody);
                return BusinessException.of(Error.DATA_NOT_FOUND);
            // 필요한 다른 상태 코드에 대한 처리를 추가할 수 있습니다.
        }
        return null;
    }

    private void logErrorResponse(Response response, String responseBody) {
        log.info("URL: {}", response.request().url());
        log.info("Headers: {}", response.request().headers());
        log.info("Response Body: {}", responseBody);
    }

}
