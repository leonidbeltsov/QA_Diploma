package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Card;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Form {
    private final SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private final SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private final SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private final SelenideElement holderField = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");
    private final SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private final SelenideElement notification = $(".notification__title");
    private final SelenideElement notificationOK = $(".notification_status_ok");
    private final SelenideElement notificationError = $(".notification_status_error");
    private final SelenideElement inputInvalid = $(".input__sub");

    public void notificationIsVisible() {
        notification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void fillForm(Card card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        holderField.setValue(card.getHolder());
        cvcField.setValue(card.getCvc());
        continueButton.click();
    }

    public void notificationOkIsVisible() {
        notificationOK.shouldBe(visible);
    }

    public void notificationErrorIsVisible() {
        notificationError.shouldBe(visible);
    }

    public String getInputInvalidMessage() {
        return inputInvalid.getText();
    }

}
