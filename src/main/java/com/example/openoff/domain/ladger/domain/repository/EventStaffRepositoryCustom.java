package com.example.openoff.domain.ladger.domain.repository;

import com.example.openoff.domain.user.domain.entity.User;

import java.util.List;

public interface EventStaffRepositoryCustom {
    List<User> findEventStaffUsersByEventInfo_Id(Long eventInfoId);

}
