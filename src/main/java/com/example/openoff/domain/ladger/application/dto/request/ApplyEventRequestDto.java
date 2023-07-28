package com.example.openoff.domain.ladger.application.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyEventRequestDto {
    private Long eventIndexId;
    private List<AnswerInfo> answerInfoList;

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class AnswerInfo {
        private Long eventExtraQuestionId;
        @NotNull
        private String answer;

        public AnswerInfo(Long eventExtraQuestionId, String answer) {
            this.eventExtraQuestionId = eventExtraQuestionId;
            this.answer = answer;
        }

        public static AnswerInfo of(Long eventExtraQuestionId, String answer) {
            return AnswerInfo.builder()
                    .eventExtraQuestionId(eventExtraQuestionId)
                    .answer(answer)
                    .build();
        }
    }

    @Builder
    public ApplyEventRequestDto(Long eventIndexId, List<AnswerInfo> answerInfoList) {
        this.eventIndexId = eventIndexId;
        this.answerInfoList = answerInfoList;
    }
}
