package com.forms.formswebapp.form.domain;

import com.forms.formswebapp.form.domain.dto.FilledOutFormDto;
import com.forms.formswebapp.form.domain.dto.FormDemographicDto;
import com.forms.formswebapp.form.domain.dto.FormSummaryDto;
import com.forms.formswebapp.form.domain.dto.RespondentData;
import com.forms.formswebapp.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        return AGE_GROUPS.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (int) answers.stream()
                                .map(FilledOutFormDto::respondentData)
                                .filter(respondentData -> respondentData.getBirthdate() != null)
                                .map(RespondentData::age)
                                .filter(age -> {
                                    int upperBound = entry.getValue();
                                    int lowerBound = getLowerBound(entry.getKey());
                                    return age > lowerBound && age <= upperBound;
                                }).count()
                ));
    }

    private int getLowerBound(String ageGroup) {
        int currentUpperBound = AGE_GROUPS.get(ageGroup);
        return AGE_GROUPS.values().stream()
                .filter(value -> value < currentUpperBound)
                .max(Integer::compareTo)
                .orElse(Integer.MIN_VALUE);
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
