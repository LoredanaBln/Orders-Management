package bussiness.validators.quantityValidator;

import bussiness.validators.Validator;

public class QuantityValidator implements Validator<Integer> {

    @Override
    public boolean isValid(Integer quantity) {
        return quantity > 0;
    }
}
