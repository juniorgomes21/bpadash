package br.com.bpadash.validations.bpa.org;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OrgValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrgValid {

    String message() default "O campo deve ser preenchido apenas com (BPA, PNI, SIE, SIB, MIN, PAC, SCL, EXT)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
