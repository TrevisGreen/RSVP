package com.RSVP.rsvp.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

@Target( {TYPE,ANNOTATION_TYPE} )
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsNotEmptyValidator.class)
@Documented
public @interface PasswordsNotEmpty {

    String message() default "PasswordsNotEmpty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String triggerFieldName() default "";

    String passwordFieldName() default "";

    String passwordVerificationFieldName() default "";
}
