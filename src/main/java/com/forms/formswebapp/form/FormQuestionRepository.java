package com.forms.formswebapp.form;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FormQuestionRepository extends MongoRepository<FormQuestion, String> {
}
