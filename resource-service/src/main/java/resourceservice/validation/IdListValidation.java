package resourceservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import resourceservice.validation.impl.IdListValidationImpl;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = IdListValidationImpl.class)
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface IdListValidation {
    String message() default "CSV string format is invalid or exceeds length restrictions.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
