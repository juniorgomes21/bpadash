package br.com.bpadash.validations.bpa.race;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RaceValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RaceValid {

    String message() default "Apenas números. Valores aceitos 01 Branca, 02 Preta, 03 Parda, 04 Amarela, 05 Indígena";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean updateErrors() default false;
}
