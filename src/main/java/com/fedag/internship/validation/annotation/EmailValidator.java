package com.fedag.internship.validation.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * class EmailValidator
 *
 * @author damir.iusupov
 * @since 2022-06-16
 */
public class EmailValidator implements ConstraintValidator<Email, String> {
    private final Set<String> mailBoxes = Arrays.stream(MailBoxes.values())
            .map(Objects::toString)
            .collect(Collectors.toSet());
    private final Set<String> regions = Arrays.stream(Regions.values())
            .map(Objects::toString)
            .collect(Collectors.toSet());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        String regex = "([a-zA-Z]{1}[\\w#$!&?<>.-]{0,235})@([a-z]{2,8}).([a-z]{2,3})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if (!matcher.find()) {
            return false;
        } else {
            if (!mailBoxes.contains(matcher.group(2)) ||
                    !regions.contains(matcher.group(3))) {
                return false;
            }
        }
        return value.matches(regex);
    }
}
