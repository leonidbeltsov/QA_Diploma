package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import ru.netology.data.Card;
import ru.netology.data.DBHelper;
import ru.netology.data.DataGenerator;
import ru.netology.page.BuyInCreditPage;
import ru.netology.page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyTests {

    Card approvedCard = DataGenerator.getApprovedCard();
    Card declinedCard = DataGenerator.getDeclinedCard();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
//        DBHelper.cleanData();
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

//    OK
    @org.junit.jupiter.api.Test
    @DisplayName("1 approval with approved card")
    public void shouldBuyWithApprovedCard() {
        var dashboardPage = new DashboardPage();
        var buyByCardPage = dashboardPage.openBuyByCardPage();
        buyByCardPage.fillForm(approvedCard);
        buyByCardPage.notificationOkIsVisible();
        assertEquals("APPROVED", DBHelper.getPaymentStatus());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("2 declined with declined card")
    public void shouldBeDeclinedBuyWithDeclinedCard() {
        var dashboardPage = new DashboardPage();
        var buyByCardPage = dashboardPage.openBuyByCardPage();
        buyByCardPage.fillForm(declinedCard);
//        buyByCardPage.notificationErrorIsVisible();
        assertEquals("DECLINED", DBHelper.getPaymentStatus());
    }

//    OK
    @org.junit.jupiter.api.Test
    @DisplayName("3 credit approval with approved card")
    public void shouldBuyInCreditWithApprovedCard() {
        var dashboardPage = new DashboardPage();
        var buyInCreditPage = dashboardPage.openBuyInCreditPage();
        buyInCreditPage.fillForm(approvedCard);
        buyInCreditPage.notificationOkIsVisible();
        assertEquals("APPROVED", DBHelper.getCreditStatus());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("4 credit declined with declined card")
    public void shouldBeNotApprovedWhitDeclinedCard() {
        DashboardPage dashboardPage = new DashboardPage();
        BuyInCreditPage buyInCredit = dashboardPage.openBuyInCreditPage();
        buyInCredit.fillForm(declinedCard);
//        buyInCredit.notificationErrorIsVisible();
        assertEquals("DECLINED", DBHelper.getCreditStatus());
    }

////    OK
//    @ParameterizedTest
//    @DisplayName("UI parameterized tests")
//    @CsvFileSource(resources = "/Values.csv")
//    void shouldShowWarning(String number, String month, String year, String owner, String cvc, String message) {
//        Card incorrectValues = new Card(number, month, year, owner, cvc);
//        DashboardPage dashboardPage = new DashboardPage();
//        BuyByCardPage buyByCardPage = dashboardPage.openBuyByCardPage();
//        buyByCardPage.fillForm(incorrectValues);
//        assertEquals(buyByCardPage.getInputInvalidMessage(), message);
//    }
}
