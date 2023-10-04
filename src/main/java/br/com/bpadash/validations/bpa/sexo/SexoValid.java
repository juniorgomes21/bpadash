package br.com.bpadash.validations.bpa.sexo;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SexoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SexoValid {

    String message() default "M - Masculino, F - Feminino";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
