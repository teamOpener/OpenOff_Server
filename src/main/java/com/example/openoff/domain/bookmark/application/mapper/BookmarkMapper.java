package com.example.openoff.domain.bookmark.application.mapper;

import com.example.openoff.common.annotation.Mapper;
import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.domain.bookmark.application.dto.response.MyBookmarkEventResponseDto;
import com.example.openoff.domain.bookmark.domain.entity.EventBookmark;
import com.example.openoff.domain.eventInstance.domain.entity.EventImage;
import com.example.openoff.domain.eventInstance.domain.entity.EventIndex;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.entity.Location;
import com.example.openoff.domain.interest.domain.entity.EventInterestField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Mapper
public class BookmarkMapper {

    public static PageResponse<MyBookmarkEventResponseDto> toMyBookmarkEventResponseDto(Page<EventBookmark> myBookmarkEvents) {
        List<MyBookmarkEventResponseDto> responseDtos = myBookmarkEvents.stream()
                .map(data -> {
                    EventInfo eventInfo = data.getEventInfo();
                    Location location = eventInfo.getLocation();
                    List<EventIndex> eventIndexes = eventInfo.getEventIndexes();
                    List<EventInterestField> eventInterestFields = eventInfo.getEventInterestFields();
                    List<EventImage> eventImages = eventInfo.getEventImages();
                    String eventMainImageUrl = eventImages.stream()
                            .filter(EventImage::getIsMain)
                            .map(EventImage::getEventImageUrl)
                            .findFirst()
                            .orElse(null);

                    return MyBookmarkEventResponseDto.builder()
                            .bookmarkId(data.getId())
                            .eventInfoId(eventInfo.getId())
                            .eventTitle(eventInfo.getEventTitle())
                            .streetRoadAddress(location.getStreetNameAddress())
                            .eventDateList(eventIndexes.stream().map(EventIndex::getEventDate).collect(Collectors.toList()))
                            .fieldTypeList(eventInterestFields.stream().map(EventInterestField::getFieldType).collect(Collectors.toList()))
                            .eventMainImageUrl(eventMainImageUrl)
                            .totalApplicantCount(eventInfo.getTotalRegisterCount())
                            .build();
                }).collect(Collectors.toList());
        return PageResponse.of(new PageImpl<>(responseDtos, myBookmarkEvents.getPageable(), myBookmarkEvents.getTotalElements()));
    }

}
