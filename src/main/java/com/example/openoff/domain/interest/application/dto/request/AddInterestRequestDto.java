package com.example.openoff.domain.interest.application.dto.request;

import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.interest.domain.entity.FieldType;
import com.example.openoff.domain.interest.domain.exception.InvalidFieldTypeException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddInterestRequestDto {
    private List<String> fieldTypeList;

    public List<FieldType> getFieldTypeEnumList() {
        List<FieldType> enumList = new ArrayList<>();
        for (String fieldTypeStr : fieldTypeList) {
            try {
                enumList.add(FieldType.valueOf(fieldTypeStr));
            } catch (IllegalArgumentException e) {
                throw InvalidFieldTypeException.of(Error.INVALID_FIELD_TYPE);
            }
        }
        return enumList;
    }
}
