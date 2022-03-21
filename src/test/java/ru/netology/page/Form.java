package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Card;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Form {
    private final SelenideElement cardNumber = $(byText("Номер карты")).parent().$(".input__control");
    private final SelenideElement month = $(byText("Месяц")).parent().$(".input__control");
    private final SelenideElement year = $(byText("Год")).parent().$(".input__control");
    private final SelenideElement cardholder = $(byText("Владелец")).parent().$(".input__control");
    private final SelenideElement cardholderWarning = $$(".input__sub").find(text("Поле обязательно для заполнения"));
    private final SelenideElement cvv = $(byText("CVC/CVV")).parent().$(".input__control");
    private final SelenideElement cvvBadFormatError = cvv.parent().parent().$(byText("Неверный формат"));
    private final SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private final SelenideElement notificationOK = $(".notification_status_ok");
    private final SelenideElement notificationError = $(".notification_status_error");
    private final SelenideElement notificationErrorCloseButton = $(".notification_status_error").$(".notification__closer");
    private final SelenideElement inputInvalid = $(".input__sub");

    public void notificationOkIsVisible() {
        notificationOK.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void notificationOkIsHidden() {
        notificationOK.shouldBe(hidden);
    }

    public void notificationErrorIsVisible() {
        notificationError.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void fillForm(Card card) {
        cardNumber.setValue(card.getNumber());
        month.setValue(card.getMonth());
        year.setValue(card.getYear());
        cardholder.setValue(card.getHolder());
        cvv.setValue(card.getCvc());
        continueButton.click();
    }

    public void closeNotificationError() {
        notificationErrorCloseButton.click();
    }

    public void inputInvalid() {
        inputInvalid.shouldBe(visible);
    }

    public String getInputInvalidMessage() {
        return inputInvalid.getText();
    }

    public void cardholderWarningHidden() {
        cardholderWarning.shouldBe(hidden);
    }

    public void cvvBadFormatError() {
        cvvBadFormatError.shouldBe(visible);
    }
}
