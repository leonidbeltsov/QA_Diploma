package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.netology.data.Card;
import ru.netology.data.DataGenerator;
import ru.netology.page.BuyByCardPage;
import ru.netology.page.BuyInCreditPage;
import ru.netology.page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UiTests {

    Card approvedCard = DataGenerator.getApprovedCard();
    Card declinedCard = DataGenerator.getDeclinedCard();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        Configuration.headless = true;
        open("http://localhost:8080/");
    }

    @Test
    @DisplayName("approval with approved card")
    public void shouldBuyWithApprovedCard() {
        DashboardPage dashboardPage = new DashboardPage();
        BuyByCardPage buyByCard = dashboardPage.openBuyByCardPage();
        buyByCard.fillForm(approvedCard);
        buyByCard.notificationOkIsVisible();
        assertFalse(buyByCard.notificationErrorIsDisplayed());
    }

    @Test
    @DisplayName("declined with declined card")
    public void shouldBeDeclinedBuyWithDeclinedCard() {
        DashboardPage dashboardPage = new DashboardPage();
        BuyByCardPage buyByCard = dashboardPage.openBuyByCardPage();
        buyByCard.fillForm(declinedCard);
        buyByCard.notificationErrorIsVisible();
        assertFalse(buyByCard.notificationOkIsDisplayed());
    }

    @Test
    @DisplayName("credit approval with approved card")
    public void shouldBuyInCreditWithApprovedCard() {
        DashboardPage dashboardPage = new DashboardPage();
        BuyInCreditPage buyInCredit = dashboardPage.openBuyInCreditPage();
        buyInCredit.fillForm(approvedCard);
        buyInCredit.notificationOkIsVisible();
        assertFalse(buyInCredit.notificationErrorIsDisplayed());
    }

    @Test
    @DisplayName("credit declined with declined card")
    public void shouldBeNotApprovedWhitDeclinedCard() {
        DashboardPage dashboardPage = new DashboardPage();
        BuyInCreditPage buyInCredit = dashboardPage.openBuyInCreditPage();
        buyInCredit.fillForm(declinedCard);
        buyInCredit.notificationErrorIsVisible();
        assertFalse(buyInCredit.notificationOkIsDisplayed());
    }

    @ParameterizedTest
    @DisplayName("UI parameterized tests")
    @CsvFileSource(resources = "/Values.csv")
    void shouldShowWarning(String number, String month, String year, String owner, String cvc, String message) {
        Card incorrectValues = new Card(number, month, year, owner, cvc);
        DashboardPage dashboardPage = new DashboardPage();
        BuyByCardPage buyByCardPage = dashboardPage.openBuyByCardPage();
        buyByCardPage.fillForm(incorrectValues);
        assertEquals(buyByCardPage.getInputInvalidMessage(), message);
    }
}
