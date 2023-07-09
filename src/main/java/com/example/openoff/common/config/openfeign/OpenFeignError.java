package com.example.openoff.common.config.openfeign;

import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import feign.Response;
import feign.codec.ErrorDecoder;

public class OpenFeignError implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return BusinessException.of(Error.DATA_NOT_FOUND);
        }
        return null;
    }

}
