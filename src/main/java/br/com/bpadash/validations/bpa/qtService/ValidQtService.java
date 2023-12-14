package br.com.bpadash.validations.bpa.qtService;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = QtServiceValidator.class)
public @interface ValidQtService {
    String message() default "Quantidade de serviço inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}