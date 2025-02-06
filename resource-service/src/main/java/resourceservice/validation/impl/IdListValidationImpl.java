package resourceservice.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.tomcat.util.buf.StringUtils;
import resourceservice.validation.IdListValidation;
import java.util.List;
import java.util.stream.Collectors;

public class IdListValidationImpl implements ConstraintValidator<IdListValidation, List<Integer>> {

    private final int MAX_SIZE = 401;

    @Override
    public boolean isValid(List<Integer> values, ConstraintValidatorContext context) {
        if (values == null) return false;
        String result = values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        if (result.length() > 199) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("List exceeds the maximum allowed length of "
                    + MAX_SIZE).addConstraintViolation();
            return false;
        }
//        if (values.size() > MAX_SIZE) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("List exceeds the maximum allowed length of "
//                    + MAX_SIZE).addConstraintViolation();
//            return false;
//        }
        return true;
    }
}
