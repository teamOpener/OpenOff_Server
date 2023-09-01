package com.example.openoff.common.util;

import com.example.openoff.common.annotation.Mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Mapper
public class CommonUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d");
    private static String formatSingleDate(LocalDateTime dateTime) {
        return dateTime.format(formatter) + "(" + dateTime.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN) + ")";
    }
    public static String formatLocalDateTimes(List<LocalDateTime> dateTimeList) {
        if (dateTimeList == null || dateTimeList.isEmpty()) {
            return "";
        }
        dateTimeList = dateTimeList.stream().sorted().collect(Collectors.toList());
        if (dateTimeList.size() == 1) {
            return formatSingleDate(dateTimeList.get(0));
        }
        String firstDate = formatSingleDate(dateTimeList.get(0));
        String lastDate = formatSingleDate(dateTimeList.get(dateTimeList.size() - 1));
        return firstDate + " - " + lastDate;
    }
}
