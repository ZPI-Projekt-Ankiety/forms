package com.forms.formswebapp.form.domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FilledOutFormRepository extends MongoRepository<FilledOutForm, String> {

    List<FilledOutForm> findAllByFormIdIs(String formId);

    Optional<FilledOutForm> findByFormId(String formId);

    @Query("{ 'respondentData.userEmail': ?0 }")
    List<FilledOutForm> findAllByRespondentEmail(String userEmail);

}
