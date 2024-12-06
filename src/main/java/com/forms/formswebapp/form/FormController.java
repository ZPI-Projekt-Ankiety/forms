package com.forms.formswebapp.form;

import com.forms.formswebapp.form.dto.FormDto;
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
    public ResponseEntity<Void> createForm(@RequestBody FormDto formDto) {
        formFacade.createForm(formDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
