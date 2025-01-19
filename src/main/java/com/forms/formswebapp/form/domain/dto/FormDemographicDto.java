package com.forms.formswebapp.form.domain.dto;

import java.util.Map;

public record FormDemographicDto(
        Map<String, Integer> genderDistribution,
        Map<String, Integer> ageGroupDistribution
) {
}
