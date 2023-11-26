package com.project.auth.validation.constraint.validator;

import com.project.auth.validation.pattern.EmailPattern;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.project.auth.util.ValidationConstants.EMAIL_IS_NOT_VALID;
import static java.lang.annotation.ElementType.*;

@Pattern(regexp = EmailPattern.EMAIL_PATTERN, message = EMAIL_IS_NOT_VALID)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface EmailValidation {

    String message() default EMAIL_IS_NOT_VALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}