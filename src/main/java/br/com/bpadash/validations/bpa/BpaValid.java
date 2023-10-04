package br.com.bpadash.validations.bpa;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BpaValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BpaValid {
    String message() default "Código inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int size() default 3;
    boolean alfa() default false;
}

