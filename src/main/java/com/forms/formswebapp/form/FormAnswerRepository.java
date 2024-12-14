package com.forms.formswebapp.form;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FormAnswerRepository extends MongoRepository<FormAnswer, String> {
}
