package com.loanbroker.commons.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SsnValidator implements ConstraintValidator<SsnFormat, String>
    {

        @Override
        public void initialize(SsnFormat constraintAnnotation) {

        }

        @Override
        public boolean isValid(String ssn, ConstraintValidatorContext constraintValidatorContext) {
            return ssn != null && ssn.matches("[0-3][0-9][0-1][1-9]\\d{2}-\\d{4}?[^0-9]*");
        }
    }
