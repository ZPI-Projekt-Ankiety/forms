package com.forms.formswebapp.form;

import com.forms.formswebapp.form.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor
public class FormController {

    private final FormFacade formFacade;

    @PostMapping
    public ResponseEntity<FormLinkDto> createForm(@RequestBody FormCreationRequestDto formCreationRequestDto) {
        FormLinkDto formLinkDto = formFacade.createForm(formCreationRequestDto);
        return new ResponseEntity<>(formLinkDto, HttpStatus.CREATED);
    }

    @PostMapping("/{linkId}")
    public ResponseEntity<Void> fillOutForm(@PathVariable String linkId,
                                            @RequestBody FormFillOutRequestDto formFillOutRequestDto) {
        formFacade.fillOutForm(linkId, formFillOutRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{link}")
    public ResponseEntity<FormResponseDto> getFormByLink(@PathVariable String link) {
        FormResponseDto formByLink = formFacade.getFormResponseDtoByLink(link);
        return new ResponseEntity<>(formByLink, HttpStatus.OK);
    }

    @GetMapping("/answer/{filledOutFormId}")
    public ResponseEntity<FilledOutFormDto> getFilledOutForm(@PathVariable String filledOutFormId) {
        FilledOutFormDto filledOutForm = formFacade.getFilledOutForm(filledOutFormId);
        return new ResponseEntity<>(filledOutForm, HttpStatus.OK);
    }

    @GetMapping("/{link}/answers")
    public ResponseEntity<List<FilledOutFormDto>> getAnswersForForm(@PathVariable String link) {
        List<FilledOutFormDto> answersForForm = formFacade.getAnswersForForm(link);
        return new ResponseEntity<>(answersForForm, HttpStatus.OK);
    }

    @GetMapping("/{formId}/answer-count")
    public ResponseEntity<Long> getAnswerCount(@PathVariable String formId) {
        long count = formFacade.getCountOfAnswersByFormId(formId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PatchMapping("/{formId}/closing-time")
    public ResponseEntity<UpdateClosingTimeRequestDto> updateClosingTime(
            @PathVariable String formId,
            @RequestBody UpdateClosingTimeRequestDto request) {
        UpdateClosingTimeRequestDto updatedFormClosingTimeTime = formFacade.updateFormClosingTime(formId, request.newClosingTime());
        return new ResponseEntity<>(updatedFormClosingTimeTime, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserForms(final Authentication authentication) {
        final List<UserFormsDto> response = formFacade.getUserForms(authentication.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-created")
    public ResponseEntity<?> getUserCreatedForms(final Authentication authentication) {
        final List<FormResponseDto> userCreatedForms = formFacade.getUserCreatedForms(authentication.getName());
        return ResponseEntity.ok(userCreatedForms);
    }

}
