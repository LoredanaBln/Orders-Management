package bussiness.validators;

/**
 This class is used to validate the quantities for orders and products in the graphical interface.
 */

public class QuantityValidator implements Validator<Integer> {
    /**
     *
     * @param quantity The quantity of the product to be validated
     * @return a boolean value as result, false if the quantity is negative and true otherwise
     */
    @Override
    public boolean isValid(Integer quantity) {
        return quantity > 0;
    }
}
