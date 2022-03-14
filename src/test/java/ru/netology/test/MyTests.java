package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.netology.data.Card;
import ru.netology.data.DBHelper;
import ru.netology.data.DataGenerator;
import ru.netology.page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyTests {

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
        DBHelper.cleanData();
    }

    @Test
    @DisplayName("approval with approved card")
    public void shouldBuyWithApprovedCard() {
        var dashboardPage = new DashboardPage();
        var buyByCardPage = dashboardPage.openBuyByCardPage();
        buyByCardPage.fillForm(approvedCard);
        buyByCardPage.notificationIsVisible();
        buyByCardPage.notificationOkIsVisible();
        assertEquals("APPROVED", DBHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("credit approval with approved card")
    public void shouldBuyInCreditWithApprovedCard() {
        var dashboardPage = new DashboardPage();
        var buyInCreditPage = dashboardPage.openBuyInCreditPage();
        buyInCreditPage.fillForm(approvedCard);
        buyInCreditPage.notificationIsVisible();
        buyInCreditPage.notificationOkIsVisible();
        assertEquals("APPROVED", DBHelper.getCreditStatus());
    }

    @Test
    @DisplayName("credit declined with declined card")
    public void shouldBeNotApprovedWhitDeclinedCard() {
        var dashboardPage = new DashboardPage();
        var buyInCredit = dashboardPage.openBuyInCreditPage();
        buyInCredit.fillForm(declinedCard);
        buyInCredit.notificationIsVisible();
        buyInCredit.notificationErrorIsVisible();
        assertEquals("DECLINED", DBHelper.getCreditStatus());
    }

    @Test
    @DisplayName("declined with declined card")
    public void shouldBeDeclinedBuyWithDeclinedCard() {
        var dashboardPage = new DashboardPage();
        var buyByCardPage = dashboardPage.openBuyByCardPage();
        buyByCardPage.fillForm(declinedCard);
        buyByCardPage.notificationIsVisible();
        buyByCardPage.notificationErrorIsVisible();
        assertEquals("DECLINED", DBHelper.getPaymentStatus());
    }

    @ParameterizedTest
    @DisplayName("UI parameterized tests buy by card")
    @CsvFileSource(resources = "/Values.csv")
    void shouldShowWarning(String number, String month, String year, String owner, String cvc, String message) {
        var incorrectValues = new Card(number, month, year, owner, cvc);
        var dashboardPage = new DashboardPage();
        var buyByCardPage = dashboardPage.openBuyByCardPage();
        buyByCardPage.fillForm(incorrectValues);
        assertEquals(buyByCardPage.getInputInvalidMessage(), message);
    }
}
