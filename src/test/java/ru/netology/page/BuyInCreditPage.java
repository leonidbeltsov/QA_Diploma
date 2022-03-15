package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class BuyInCreditPage {

    public BuyInCreditPage() {
        SelenideElement heading = $$("h3").find(text("Кредит по данным карты"));
        heading.shouldBe(visible);
    }

    public Form form() {
        return new Form();
    }
}
