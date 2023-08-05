package com.example.openoff.domain.comment.application.mapper;

import com.example.openoff.common.annotation.Mapper;
import com.example.openoff.common.dto.PageResponse;
import com.example.openoff.domain.comment.application.dto.request.CommentWriteRequestDto;
import com.example.openoff.domain.comment.application.dto.response.ChildCommentInfoResponseDto;
import com.example.openoff.domain.comment.application.dto.response.CommentWriteResponseDto;
import com.example.openoff.domain.comment.application.dto.response.ParentCommentInfoResponseDto;
import com.example.openoff.domain.comment.domain.entity.EventComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public class CommentMapper {
    public static CommentWriteResponseDto mapToCommentWriteResDto(EventComment eventComment, CommentWriteRequestDto commentWriteRequestDto){
        return CommentWriteResponseDto.builder()
                .commentId(eventComment.getId())
                .eventInfoId(commentWriteRequestDto.getEventInfoId())
                .parentId(commentWriteRequestDto.getParentId())
                .content(commentWriteRequestDto.getContent())
                .build();
    }

    public static PageResponse<ParentCommentInfoResponseDto> mapToParentCommentInfoResponseDto(List<String> staffIds, Page<EventComment> eventComments) {
        List<ParentCommentInfoResponseDto> responseDtos = eventComments.stream().map(data ->
                ParentCommentInfoResponseDto.builder()
                        .commentId(data.getId())
                        .userId(data.getWriter().getId())
                        .nickname(data.getWriter().getNickname())
                        .content(data.getContent())
                        .childCount(data.getChildren().size())
                        .createdAt(data.getCreatedDate())
                        .isStaff(staffIds.contains(data.getWriter().getId())).build()
        ).collect(Collectors.toList());
        return PageResponse.of(new PageImpl<>(responseDtos, eventComments.getPageable(), eventComments.getTotalElements()));
    }

    public static List<ChildCommentInfoResponseDto> mapToChildCommentInfoResponseDto(List<String> staffIds, List<EventComment> comments) {
        List<ChildCommentInfoResponseDto> responseDtos = comments.stream().map(data ->
                ChildCommentInfoResponseDto.builder()
                        .commentId(data.getId())
                        .userId(data.getWriter().getId())
                        .nickname(data.getWriter().getNickname())
                        .content(data.getContent())
                        .createdAt(data.getCreatedDate())
                        .isStaff(staffIds.contains(data.getWriter().getId())).build()
        ).collect(Collectors.toList());
        return responseDtos;
    }
}
