package br.com.bpadash.validations.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PackageUserValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PackageUserValid {
    String message() default "Package invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
