package bussiness.validators;

/**
 * Interface used to define a contract for QuantityValidator class and StringValidator class
 *  @param <T> the type of input to be validated
 */
public interface Validator<T>{
    /**
     *
     *@param t the input value to be validated
     *@return true if the input is valid, false otherwise
     */
    public boolean isValid(T t);
}
