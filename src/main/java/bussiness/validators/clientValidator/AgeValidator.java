package bussiness.validators.clientValidator;

import bussiness.validators.Validator;

public class AgeValidator implements Validator<Integer>{
    @Override
    public boolean isValid(Integer age) {
        return age >= 10;
    }
}
