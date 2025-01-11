package com.forms.formswebapp.form.domain.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record FormSummaryDto(
        String linkId,
        Integer totalResponses,
        Integer avgResponsesPerDay,
        List<DailyResponseDto> dailyResponses
) {

    public record DailyResponseDto(
            LocalDate date,
            Integer responseCount
    ) {
        public static DailyResponseDto from(Map.Entry<LocalDate, List<FilledOutFormDto>> entry) {
            return new DailyResponseDto(entry.getKey(), entry.getValue().size());
        }
    }

}
