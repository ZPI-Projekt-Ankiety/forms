@org.springframework.modulith.ApplicationModule(
        allowedDependencies = {"mail", "user", "user :: domain", "common"}
)
package com.forms.formswebapp.form;