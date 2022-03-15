package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataGenerator {

    public static final String approvedCard = ("4444 4444 4444 4441");
    public static final String declinedCard = ("4444 4444 4444 4442");
    public static final String unknownCard = ("4444 4444 4444 4444");


    private DataGenerator() {
    }

    private static final Faker faker = new Faker();

    public static String generateMonth() {
        return LocalDate.now().plusMonths(faker.number().numberBetween(1, 13)).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateYear() {
        return LocalDate.now().plusYears(faker.number().numberBetween(1, 4)).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String generateHolder() {
        return (faker.name().firstName() + " " + faker.name().lastName());
    }


    public static String generateCvc() {
        return faker.numerify("###");
    }

    public static Card getApprovedCard() {
        return new Card(approvedCard,
                generateMonth(),
                generateYear(),
                generateHolder(),
                generateCvc()
        );
    }

    public static Card getDeclinedCard() {
        return new Card(declinedCard,
                generateMonth(),
                generateYear(),
                generateHolder(),
                generateCvc()
        );
    }

    public static Card getUnknownCard() {
        return new Card(unknownCard,
                generateMonth(),
                generateYear(),
                generateHolder(),
                generateCvc()
        );
    }

    public static Card getApprovedCardWithWrongHolder() {
        return new Card(approvedCard,
                generateMonth(),
                generateYear(),
                "123456789Йцукенгшщзхъ!\"№;%:?*()123456789Йцукенгшщзхъ!\"№;%:?*()",
                generateCvc()
        );
    }

    public static Card getDeclinedCardWithWrongHolder() {
        return new Card(declinedCard,
                generateMonth(),
                generateYear(),
                "123456789Йцукенгшщзхъ!\"№;%:?*()123456789Йцукенгшщзхъ!\"№;%:?*()",
                generateCvc()
        );
    }

}
