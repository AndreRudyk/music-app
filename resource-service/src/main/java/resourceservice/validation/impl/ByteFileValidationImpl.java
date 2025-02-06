package resourceservice.validation.impl;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import resourceservice.validation.ByteFileValidation;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ByteFileValidationImpl implements ConstraintValidator<ByteFileValidation, byte[]> {

    @Override
    public void initialize(ByteFileValidation constraintAnnotation) {
        //ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(byte[] bytes, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Mp3File mp3File = new Mp3File(new ByteArrayInputStream(bytes).toString());
            return mp3File.hasId3v1Tag() || mp3File.hasId3v2Tag();
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            return false;
        }
    }
}
