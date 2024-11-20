package com.projet.citronix.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class PlantingPeriodValidator implements ConstraintValidator<PlantingPeriod, LocalDate> {

    private int startMonth;
    private int endMonth;

    @Override
    public void initialize(PlantingPeriod constraintAnnotation) {
        this.startMonth = constraintAnnotation.startMonth();
        this.endMonth = constraintAnnotation.endMonth();
    }

    @Override
    public boolean isValid(LocalDate datePlantation, ConstraintValidatorContext context) {
        if (datePlantation == null) {
            return true;
        }

        int moisPlantation = datePlantation.getMonthValue();
        return moisPlantation >= startMonth && moisPlantation <= endMonth;
    }
}