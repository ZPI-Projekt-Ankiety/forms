package com.forms.formswebapp.form.domain;

import com.forms.formswebapp.form.domain.dto.FilledOutFormDto;
import com.forms.formswebapp.form.domain.dto.FormDemographicDto;
import com.forms.formswebapp.form.domain.dto.FormSummaryDto;
import com.forms.formswebapp.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormStatisticsService {

    private final FormService formService;

    private static final Map<String, Integer> AGE_GROUPS = Map.of(
            "Under 18", 17,
            "18-25", 25,
            "26-35", 35,
            "36-45", 45,
            "46-60", 60,
            "61 and above", 999
    );


    public FormSummaryDto getFormStatistics(final String link) {
        final List<FilledOutFormDto> answers = formService.getAnswersForForm(link);
        final List<FormSummaryDto.DailyResponseDto> groupedByDate = answers.stream()
                .collect(Collectors.groupingBy(FilledOutFormDto::filledOutAtDay))
                .entrySet().stream().map(FormSummaryDto.DailyResponseDto::from)
                .toList();
        final int totalResponses = answers.size();
        final int totalDays = groupedByDate.size();
        final int avgResponsesPerDay = totalDays == 0 ? 0 : totalResponses / totalDays;
        return new FormSummaryDto(link, totalResponses, avgResponsesPerDay, groupedByDate);
    }

    public FormDemographicDto getFormDemographicStatistics(final String link) {
        final List<FilledOutFormDto> answers = formService.getAnswersForForm(link);
        return new FormDemographicDto(getAgeGroupsCount(answers), getGenderCount(answers));
    }

    private Map<String, Integer> getAgeGroupsCount(final List<FilledOutFormDto> answers) {
        return answers.stream()
                .map(f -> formService.getUserByFilledOutForm(f.id()))
                .flatMap(Optional::stream)
                .map(User::age)
                .map(age -> AGE_GROUPS.entrySet().stream()
                        .filter(entry -> age <= entry.getValue())
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse("Unknown"))
                .collect(Collectors.groupingBy(ageGroup -> ageGroup, Collectors.summingInt(ageGroup -> 1)));
    }

    private Map<String, Integer> getGenderCount(final List<FilledOutFormDto> answers) {
        return answers.stream()
                .map(f -> formService.getUserByFilledOutForm(f.id()))
                .flatMap(Optional::stream)
                .map(user -> user.getGender().name())
                .collect(Collectors.groupingBy(gender -> gender, Collectors.summingInt(gender -> 1)));
    }

}
