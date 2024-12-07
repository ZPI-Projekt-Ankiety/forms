package com.forms.formswebapp.form;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

interface FormRepository extends MongoRepository<Form, String> {

    List<Form> findByClosingTimeBeforeAndStatusNot(final LocalDateTime closingTime, final Form.Status status);

}
