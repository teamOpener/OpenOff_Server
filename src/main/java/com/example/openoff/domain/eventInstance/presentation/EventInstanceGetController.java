package com.example.openoff.domain.eventInstance.presentation;

import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.common.dto.ResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.request.EventSearchRequestDto;
import com.example.openoff.domain.eventInstance.application.dto.response.DetailEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.response.HostEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.dto.response.MainTapEventInfoResponse;
import com.example.openoff.domain.eventInstance.application.dto.response.SearchMapEventInfoResponseDto;
import com.example.openoff.domain.eventInstance.application.service.EventSearchUseCase;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/event-instance")
@RequiredArgsConstructor
public class EventInstanceGetController {
    @Value("${passkeyword}")
    private String passKeyword;

    private final EventSearchUseCase eventSearchUseCase;

    @GetMapping(value = "/detail/{eventInfoId}")
    public ResponseEntity<ResponseDto<DetailEventInfoResponseDto>> getEventInfoDetail
            (
                    @RequestHeader(value="openoff", required=false) String superPass,
                    @PathVariable Long eventInfoId)
    {
        if (superPass != null && superPass.equals(passKeyword)) {
            DetailEventInfoResponseDto detailEventInfo = eventSearchUseCase.getDetailEventInfo(eventInfoId, false);
            return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "[비로그인] 이벤트 상세정보 불러오기를 성공하였습니다.", detailEventInfo));
        } else {
            DetailEventInfoResponseDto detailEventInfo = eventSearchUseCase.getDetailEventInfo(eventInfoId, true);
            return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "이벤트 상세정보 불러오기를 성공하였습니다.", detailEventInfo));
        }
    }

    @GetMapping(value = "/search")
    public ResponseEntity<ResponseDto<List<SearchMapEventInfoResponseDto>>> searchMapEventInfo
            (@ModelAttribute EventSearchRequestDto eventSearchRequestDto)
    {
        List<SearchMapEventInfoResponseDto> searchMapEventInfoResponseDtoList = eventSearchUseCase.searchMapEventInfo(eventSearchRequestDto);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "검색 조건에 따라 지도에 이벤트 정보를 불러오는데 성공하였습니다.", searchMapEventInfoResponseDtoList));
    }

    @GetMapping(value = "/search/one")
    public ResponseEntity<ResponseDto<SearchMapEventInfoResponseDto>> searchMapOnlyOneEventInfo
            (
                    @RequestParam Long eventInfoId
            )
    {
        SearchMapEventInfoResponseDto searchMapEventInfoResponseDto = eventSearchUseCase.searchMapEventInfoOnlyOne(eventInfoId);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "지도에 이벤트 정보를 불러오는데 성공하였습니다.", searchMapEventInfoResponseDto));
    }

    @GetMapping(value = "/main/personal")
    public ResponseEntity<ResponseDto<List<MainTapEventInfoResponse>>> getMainTapListByMyInterestField
            ()
    {
        List<MainTapEventInfoResponse> personalEventInfoList = eventSearchUseCase.getPersonalEventInfoList();
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "맞춤 이벤트 탭에 띄울 이벤트 정보를 불러오는데 성공하였습니다.", personalEventInfoList));
    }

    @GetMapping(value = "/main/{fieldType}")
    public ResponseEntity<ResponseDto<PageResponse<MainTapEventInfoResponse>>> getMainTapListByFieldType
            (
                    @RequestHeader(value="openoff", required=false) String superPass,
                    @PathVariable FieldType fieldType,
                    @RequestParam(required = false) Long eventInfoId,
                    @PageableDefault(size = 8) Pageable pageable
            )
    {
        if (superPass != null && superPass.equals(passKeyword)) {
            PageResponse<MainTapEventInfoResponse> mainTapNotLoginEventInfoResponsePage = eventSearchUseCase.getNotLoginMainTapList(eventInfoId, fieldType, null, pageable);
            return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "[비로그인] 메인 탭에 띄울 이벤트 정보를 불러오는데 성공하였습니다.", mainTapNotLoginEventInfoResponsePage));
        }
        PageResponse<MainTapEventInfoResponse> mainTapEventInfoResponsePage = eventSearchUseCase.getMainTapList(eventInfoId, fieldType, null, pageable);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "메인 탭에 띄울 이벤트 정보를 불러오는데 성공하였습니다.", mainTapEventInfoResponsePage));
    }

    @GetMapping(value = "/main/vogue")
    public ResponseEntity<ResponseDto<PageResponse<MainTapEventInfoResponse>>> getMainTapListByVogue
            (
                    @RequestHeader(value="openoff", required=false) String superPass,
                    @RequestParam(required = false) Integer count,
                    @RequestParam(required = false) Long eventInfoId,
                    @PageableDefault(size = 8) Pageable pageable
            )
    {
        if (superPass != null && superPass.equals(passKeyword)) {
            PageResponse<MainTapEventInfoResponse> mainTapNotLoginEventInfoResponsePage = eventSearchUseCase.getNotLoginMainTapList(eventInfoId, null, count, pageable);
            return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "[비로그인] 메인 탭에 띄울 이벤트 정보를 불러오는데 성공하였습니다.", mainTapNotLoginEventInfoResponsePage));
        }
        PageResponse<MainTapEventInfoResponse> mainTapEventInfoResponsePage = eventSearchUseCase.getMainTapList(eventInfoId, null, count, pageable);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "메인 탭에 띄울 이벤트 정보를 불러오는데 성공하였습니다.", mainTapEventInfoResponsePage));
    }

    @GetMapping(value = "/host")
    public ResponseEntity<ResponseDto<PageResponse<HostEventInfoResponseDto>>> getMyHostEventInfos
            (
                    @RequestParam(required = false) FieldType fieldType,
                    @RequestParam(required = false) Long eventInfoId,
                    @PageableDefault(size = 8) Pageable pageable
            )
    {
        PageResponse<HostEventInfoResponseDto> hostEventInfoResponseDtoPage = eventSearchUseCase.getHostEventInfoList(eventInfoId, fieldType, pageable);
        return ResponseEntity.ok(ResponseDto.of(HttpStatus.OK.value(), "주최한 이벤트 목록 조회가 완료되었습니다.", hostEventInfoResponseDtoPage));
    }


}
