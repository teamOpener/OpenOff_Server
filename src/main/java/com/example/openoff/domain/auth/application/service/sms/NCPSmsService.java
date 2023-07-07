package com.example.openoff.domain.auth.application.service.sms;

import com.example.openoff.common.annotation.UseCase;
import com.example.openoff.common.exception.Error;
import com.example.openoff.common.util.UserUtils;
import com.example.openoff.domain.auth.application.dto.request.sms.NCPSmsInfoRequestDto;
import com.example.openoff.domain.auth.application.dto.request.sms.NCPSmsSendRequestDto;
import com.example.openoff.domain.auth.application.dto.response.sms.NCPSmsResponseDto;
import com.example.openoff.domain.auth.application.exception.OAuthException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class NCPSmsService {
    private final NCPSmsProperties ncpSmsProperties;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserUtils userUtils;

    public String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ ncpSmsProperties.getServiceId()+"/messages";
        String timestamp = time.toString();
        String accessKey = ncpSmsProperties.getAccessKey();
        String secretKey = ncpSmsProperties.getSecretKey();

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }

    public NCPSmsResponseDto sendSms(NCPSmsInfoRequestDto ncpSmsInfoRequestDto) {
        try {
            Random random = new Random();
            StringBuilder randomNum = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                randomNum.append(random.nextInt(9) + 1);
            }
            ncpSmsInfoRequestDto.setContent("[OpenOff] 인증번호 ["+randomNum+"]를 입력해주세요.");

            redisTemplate.opsForValue().set(
                    ncpSmsInfoRequestDto.getTo(),
                    randomNum.toString(),
                    300000,
                    TimeUnit.MILLISECONDS);

            Long time = System.currentTimeMillis();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-ncp-apigw-timestamp", time.toString());
            headers.set("x-ncp-iam-access-key", ncpSmsProperties.getAccessKey());
            headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

            List<NCPSmsInfoRequestDto> messages = new ArrayList<>();
            messages.add(ncpSmsInfoRequestDto);

            NCPSmsSendRequestDto request = NCPSmsSendRequestDto.builder()
                    .type("SMS")
                    .contentType("COMM")
                    .countryCode("82")
                    .from(ncpSmsProperties.getSenderPhone())
                    .content(ncpSmsInfoRequestDto.getContent())
                    .messages(messages)
                    .build();

            String body = objectMapper.writeValueAsString(request);
            HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            NCPSmsResponseDto response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + ncpSmsProperties.getServiceId() + "/messages"), httpBody, NCPSmsResponseDto.class);
            return response;
        } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException | JsonProcessingException | URISyntaxException e) {
            throw new OAuthException(Error.NCP_SMS_FAILED);
        }
    }
}
