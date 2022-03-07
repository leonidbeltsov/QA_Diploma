package ru.netology.data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataGenerator {

    private DataGenerator() {
    }

//    public static Card getApprovedCard() {
//        return new Card("4444 4444 4444 4441", "12", "22", "Card Holder", "123");
//    }

//    public static Card getDeclinedCard() {
//        return new Card("4444 4444 4444 4442", "12", "22", "Card Holder", "123");
//    }

    private static final Faker faker = new Faker();

    public static String generateCardNumber(){
        return faker.finance().creditCard(CreditCardType.MASTERCARD);
    }

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
        return new Card("4444 4444 4444 4441",
                generateMonth(),
                generateYear(),
                generateHolder(),
                generateCvc()
        );
    }

    public static Card getDeclinedCard() {
        return new Card("4444 4444 4444 4442",
                generateMonth(),
                generateYear(),
                generateHolder(),
                generateCvc()
        );
    }
}
