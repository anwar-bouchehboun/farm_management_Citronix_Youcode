package com.projet.citronix.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PlantingPeriodValidator.class)
@Documented
public @interface PlantingPeriod {
    String message() default "L'arbre ne peut être planté qu'entre mars et mai";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int startMonth() default 3;
    int endMonth() default 5;
}