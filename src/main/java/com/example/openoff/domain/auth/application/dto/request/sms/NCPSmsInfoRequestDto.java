package com.example.openoff.domain.auth.application.dto.request.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NCPSmsInfoRequestDto {
    private String to;
    private String content;
}
