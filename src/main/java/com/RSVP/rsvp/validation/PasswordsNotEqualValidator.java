package com.RSVP.rsvp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsNotEqualValidator implements ConstraintValidator<PasswordsNotEqual, Object> {

    private String passwordFieldName;

    private String passwordVerificationFieldName;

    @Override
    public void initialize(PasswordsNotEqual constraintAnnotation) {
        this.passwordFieldName = constraintAnnotation.passwordFieldName();
        this.passwordVerificationFieldName = constraintAnnotation.passwordVerificationFieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        try {
            String password = (String) ValidatorUtil.getFieldValue(value, passwordFieldName);
            String passwordVerification = (String) ValidatorUtil.getFieldValue(value, passwordVerificationFieldName);

            if (passwordsAreNotEqual(password, passwordVerification)) {
                ValidatorUtil.addValidationError(passwordFieldName, context);
                ValidatorUtil.addValidationError(passwordVerificationFieldName, context);

                return false;
            }
        }
        catch (Exception ex) {
            throw new RuntimeException("Exception occurred during validation", ex);
        }

        return true;
    }

    private boolean passwordsAreNotEqual(String password, String passwordVerification) {
        return !(password == null ? passwordVerification == null : password.equals(passwordVerification));
    }
}
