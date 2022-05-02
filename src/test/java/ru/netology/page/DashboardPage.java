package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final SelenideElement buyButton = $$("button").find(exactText("Купить"));
    private final SelenideElement creditButton = $$("button").find(exactText("Купить в кредит"));

    public DashboardPage() {
        SelenideElement heading = $$("h2").find(text("Путешествие дня"));
        heading.shouldBe(visible);
    }

    public BuyByCardPage openBuyByCardPage() {
        buyButton.click();
        return new BuyByCardPage();
    }

    public BuyInCreditPage openBuyInCreditPage() {
        creditButton.click();
        return new BuyInCreditPage();
    }
}
