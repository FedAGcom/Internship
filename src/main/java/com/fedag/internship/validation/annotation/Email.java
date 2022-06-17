package com.fedag.internship.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author damir.iusupov
 * @interface Email
 * @since 2022-06-16
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {
    String message() default "Email not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
