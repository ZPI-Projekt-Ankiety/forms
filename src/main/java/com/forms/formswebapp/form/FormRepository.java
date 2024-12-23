package com.forms.formswebapp.form;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

interface FormRepository extends MongoRepository<Form, String> {

    List<Form> findByClosingTimeBeforeAndStatusNot(final LocalDateTime closingTime, final Form.Status status);

    Optional<Form> findByLink(String link);

}
