package ru.netology.data;

public class DataGenerator {

    private DataGenerator() {
    }

    public static Card getApprovedCard() {
        return new Card("4444 4444 4444 4441", "12", "22", "Card Holder", "123");
    }

    public static Card getDeclinedCard() {
        return new Card("4444 4444 4444 4442", "12", "22", "Card Holder", "123");
    }
}
