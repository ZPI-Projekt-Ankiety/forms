package com.forms.formswebapp.form;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FilledOutFormRepository extends MongoRepository<FilledOutForm, String> {

    List<FilledOutForm> findAllByFormIdIs(String formId);

    Optional<FilledOutForm> findByFormId(String formId);

}
