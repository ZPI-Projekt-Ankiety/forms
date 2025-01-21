package com.forms.formswebapp.form.domain;

import com.forms.formswebapp.form.domain.dto.FilledOutFormDto;
import com.forms.formswebapp.form.domain.dto.FormDemographicDto;
import com.forms.formswebapp.form.domain.dto.FormSummaryDto;
import com.forms.formswebapp.form.domain.dto.RespondentData;
import com.forms.formswebapp.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        return new FormDemographicDto(getGenderCount(answers), getAgeGroupsCount(answers));
    }

    private Map<String, Integer> getAgeGroupsCount(final List<FilledOutFormDto> answers) {
        Map<String, Integer> ageGroupsCount = new LinkedHashMap<>();
        AGE_GROUPS.forEach((groupLabel, upperBound) -> ageGroupsCount.put(groupLabel, 0));

        for (FilledOutFormDto formDto : answers) {
            int age = (formDto.respondentData() == null) ? 0 : formDto.respondentData().age();

            for (Map.Entry<String, Integer> groupEntry : AGE_GROUPS.entrySet()) {
                if (age <= groupEntry.getValue()) {
                    ageGroupsCount.put(
                            groupEntry.getKey(),
                            ageGroupsCount.get(groupEntry.getKey()) + 1
                    );
                    break;
                }
            }
        }
        return ageGroupsCount;
    }

    private Map<String, Integer> getGenderCount(final List<FilledOutFormDto> answers) {
        Map<String, Integer> genderCounts = Arrays.stream(User.Gender.values())
                .collect(Collectors.toMap(Enum::name, gender -> 0));

        answers.stream()
                .map(FilledOutFormDto::respondentData)
                .filter(respondentData -> respondentData.getGender() != null)
                .map(user -> user.getGender().name())
                .forEach(gender -> genderCounts.merge(gender, 1, Integer::sum));
        return genderCounts;
    }

}
