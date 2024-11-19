package com.projet.citronix.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaximumAgeValidator.class)
@Documented
public @interface MaximumAge {
    String message() default "Un arbre ne peut être productif au-delà de 20 ans";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int value() default 20;
}
