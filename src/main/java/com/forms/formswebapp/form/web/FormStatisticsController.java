package com.forms.formswebapp.form.web;

import com.forms.formswebapp.form.domain.FormStatisticsService;
import com.forms.formswebapp.form.domain.dto.FormDemographicDto;
import com.forms.formswebapp.form.domain.dto.FormSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor
class FormStatisticsController {

    private final FormStatisticsService formStatisticsService;


    @GetMapping("/{link}/summary")
    ResponseEntity<?> getSummaryStatistics(@PathVariable final String link) {
        final FormSummaryDto response = formStatisticsService.getFormStatistics(link);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/{link}/demographic")
    ResponseEntity<?> getDemographicStatistics(@PathVariable final String link) {
        final FormDemographicDto response = formStatisticsService.getFormDemographicStatistics(link);
        return ResponseEntity.ok().body(response);
    }

}
