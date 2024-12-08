package com.forms.formswebapp.form;

import com.forms.formswebapp.form.dto.FormRequestDto;
import com.forms.formswebapp.form.dto.FormResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor
public class FormController {

    private final FormFacade formFacade;

    @PostMapping
    public ResponseEntity<Void> createForm(@RequestBody FormRequestDto formRequestDto) {
        formFacade.createForm(formRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{link}")
    public ResponseEntity<FormResponseDto> getFormByLink(@PathVariable String link) {
        formFacade.getFormByLink(link);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
