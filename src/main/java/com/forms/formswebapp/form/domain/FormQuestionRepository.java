package com.forms.formswebapp.form.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FormQuestionRepository extends MongoRepository<FormQuestion, String> {
}
