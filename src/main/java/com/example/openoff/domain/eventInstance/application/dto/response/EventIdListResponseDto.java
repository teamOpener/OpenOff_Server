package com.example.openoff.domain.eventInstance.application.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventIdListResponseDto {
  private List<Long> eventIdList;

  @Builder
  public EventIdListResponseDto(List<Long> eventIdList) {
    this.eventIdList = eventIdList;
  }

}
