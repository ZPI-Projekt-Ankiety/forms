package com.forms.formswebapp.form.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FormAnswerRepository extends MongoRepository<FormAnswer, String> {
}
