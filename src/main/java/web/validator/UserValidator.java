package web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import web.model.User;

@Component
public class UserValidator implements Validator {

    private static final String FIRSTNAME_ERROR_FIELD = "firstName";
    private static final String LASTNAME_ERROR_FIELD = "lastName";
    private static final String FIRSTNAME_ERROR_CODE = "firstName.invalid";
    private static final String LASTNAME_ERROR_CODE = "lastName.invalid";

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            errors.rejectValue(FIRSTNAME_ERROR_FIELD, FIRSTNAME_ERROR_CODE, "First name must be defined!");
        } else if (user.getFirstName().matches(".*\\d.*")) {
            errors.rejectValue(FIRSTNAME_ERROR_FIELD, FIRSTNAME_ERROR_CODE, "First name cannot contain numbers!");
        } else if (user.getFirstName().length() < 2) {
            errors.rejectValue(FIRSTNAME_ERROR_FIELD, FIRSTNAME_ERROR_CODE, "First name is too short!");
        }
        if (user.getLastName() == null || user.getLastName().isBlank()) {
            errors.rejectValue(LASTNAME_ERROR_FIELD, LASTNAME_ERROR_CODE, "Last name must be defined!");
        } else if (user.getLastName().matches(".*\\d.*")) {
            errors.rejectValue(LASTNAME_ERROR_FIELD, LASTNAME_ERROR_CODE, "Last name cannot contain numbers!");
        } else if (user.getLastName().length() < 2) {
            errors.rejectValue(LASTNAME_ERROR_FIELD, LASTNAME_ERROR_CODE, "Last name is too short!");
        }
        if (user.getEmail() == null || !user.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
            errors.rejectValue("email", "email.invalid", "Email must be defined and valid!");
        }
        if (user.getAge() == null || user.getAge() <= 0 || user.getAge() > 125) {
            errors.rejectValue("age", "age.invalid", "Age must be from 1 to 125!");
        }
    }
}
