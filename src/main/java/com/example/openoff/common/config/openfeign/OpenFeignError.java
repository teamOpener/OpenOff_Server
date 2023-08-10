package com.example.openoff.common.config.openfeign;

import com.example.openoff.common.exception.BusinessException;
import com.example.openoff.common.exception.Error;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpenFeignError implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return BusinessException.of(Error.DATA_NOT_FOUND);
            case 401:
                log.info(response.reason());
                log.info(response.toString());
                log.info(response.body().toString());
                return BusinessException.of(Error.DATA_NOT_FOUND);
        }
        return null;
    }

}
