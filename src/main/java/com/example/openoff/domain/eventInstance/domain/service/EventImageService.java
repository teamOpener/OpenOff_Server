package com.example.openoff.domain.eventInstance.domain.service;

import com.example.openoff.common.annotation.DomainService;
import com.example.openoff.domain.eventInstance.application.dto.request.CreateNewEventRequestDto;
import com.example.openoff.domain.eventInstance.domain.entity.EventImage;
import com.example.openoff.domain.eventInstance.domain.entity.EventInfo;
import com.example.openoff.domain.eventInstance.domain.repository.EventImageRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class EventImageService {
    private final EventImageRepository eventImageRepository;

    public List<Long> saveEventImage(EventInfo eventInfo, List<CreateNewEventRequestDto.ImageUrlList> imageDataList) {
        List<EventImage> eventImageList = imageDataList.stream()
                .map(imageData -> EventImage.toEntity(imageData.getImageUrl(), imageData.getIsMain(), eventInfo))
                .collect(Collectors.toList());
        return eventImageRepository.saveAll(eventImageList).stream().map(EventImage::getId).collect(Collectors.toList());
    }

    public String getMainEventImageByEventInfoId(Long eventInfoId) {
        return eventImageRepository.findMainImageUrlByEventInfoId(eventInfoId);
    }
}
