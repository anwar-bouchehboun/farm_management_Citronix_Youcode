package com.projet.citronix.validation;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class MaximumAgeValidator implements ConstraintValidator<MaximumAge, LocalDate> {
    
    private int maxAge;

    @Override
    public void initialize(MaximumAge constraintAnnotation) {
        this.maxAge = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate datePlantation, ConstraintValidatorContext context) {
        if (datePlantation == null) {
            return true;
        }
        
        int age = Period.between(datePlantation, LocalDate.now()).getYears();
        return age <= maxAge;
    }
}