package bussiness.validators;

/**
 This class is used to validate inputs in the graphical interface.
 */
public class StringValidator implements Validator<String>{
    /**
     *
     * @param s The string to be validated
     * @return A boolean value, false if the string is null and true otherwise
     */
    @Override
    public boolean isValid(String s) {
        return !s.isEmpty();
    }
}
