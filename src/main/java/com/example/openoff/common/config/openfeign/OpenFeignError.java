package com.example.openoff.common.config.openfeign;

import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class OpenFeignError implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new BusinessException(HttpStatus.BAD_REQUEST, "요청 데이터가 없거나 잘못된 요청입니다", Error.DATA_NOT_FOUND);
        }
        return null;
    }

}
