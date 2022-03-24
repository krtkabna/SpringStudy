package lab1.practice.service;

import lab1.practice.exception.UnsupportedTypeException;
import org.apache.commons.lang3.RandomStringUtils;
import java.security.SecureRandom;
import java.util.Random;

public class RandomUtil {
    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final String STRING = "java.lang.String";
    private static final Random RANDOM = new SecureRandom();

    private RandomUtil() {
    }

    public static Object getRandom(String type, String limit) {
        return switch (type) {
            case INT -> RANDOM.nextInt(Integer.parseInt(limit));
            case DOUBLE -> RANDOM.nextDouble(Double.parseDouble(limit));
            case STRING -> generateString(Integer.parseInt(limit));
            default -> throw new UnsupportedTypeException(String.format("Type '%s' is not supported", type));
        };
    }

    private static String generateString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }
}

