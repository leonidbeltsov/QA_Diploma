package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.netology.data.Card;
import ru.netology.data.DBHelper;
import ru.netology.page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.netology.data.DataGenerator.*;

public class MyTests {

    Card approvedCard = getApprovedCard();
    Card declinedCard = getDeclinedCard();
    Card unknownCard = getUnknownCard();
    Card approvedCardWithWrongHolder = getApprovedCardWithWrongHolder();
    Card declinedCardWithWrongHolder = getDeclinedCardWithWrongHolder();

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
    @DisplayName("+approval with approved card")
    public void shouldShowNotificationOkForPurchaseWithApprovedCard() {
        var dashboardPage = new DashboardPage();
        var buyByCardPage = dashboardPage.openBuyByCardPage();
        var form = buyByCardPage.form();
        form.fillForm(approvedCard);
        form.notificationIsVisible();
        form.notificationOkIsVisible();
//        assertEquals("APPROVED", DBHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("+credit approval with approved card")
    public void shouldShowNotificationOkForPurchaseInCreditWithApprovedCard() {
        var dashboardPage = new DashboardPage();
        var buyInCreditPage = dashboardPage.openBuyInCreditPage();
        var form = buyInCreditPage.form();
        form.fillForm(approvedCard);
        form.notificationIsVisible();
        form.notificationOkIsVisible();
//        assertEquals("APPROVED", DBHelper.getCreditStatus());
    }

    @Test
    @DisplayName("-declined with declined card")
    public void shouldShowNotificationWithErrorForPurchaseWithDeclinedCard() {
        var dashboardPage = new DashboardPage();
        var buyByCardPage = dashboardPage.openBuyByCardPage();
        var form = buyByCardPage.form();
        form.fillForm(declinedCard);
        form.notificationIsVisible();
        form.notificationErrorIsVisible();
//        assertEquals("DECLINED", DBHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("-credit declined with declined card")
    public void shouldShowNotificationWithErrorForPurchaseInCreditWithDeclinedCard() {
        var dashboardPage = new DashboardPage();
        var buyInCredit = dashboardPage.openBuyInCreditPage();
        var form = buyInCredit.form();
        form.fillForm(declinedCard);
        form.notificationIsVisible();
        form.notificationErrorIsVisible();
//        assertEquals("DECLINED", DBHelper.getCreditStatus());
    }

//    @Test
//    @DisplayName("+declined with unknown card")
//    public void shouldNotBeApprovedWithUnknownCard() {
//        var dashboardPage = new DashboardPage();
//        var buyByCardPage = dashboardPage.openBuyByCardPage();
//        var form = buyByCardPage.form();
//        form.fillForm(unknownCard);
//        form.notificationIsVisible();
//        form.notificationErrorIsVisible();
//        assertNull(DBHelper.getPaymentStatus());
//    }

//    @Test
//    @DisplayName("+credit declined with unknown card")
//    public void shouldNotBeApprovedInCreditWithUnknownCard() {
//        var dashboardPage = new DashboardPage();
//        var buyInCredit = dashboardPage.openBuyInCreditPage();
//        var form = buyInCredit.form();
//        form.fillForm(unknownCard);
//        form.notificationIsVisible();
//        form.notificationErrorIsVisible();
//        assertNull(DBHelper.getCreditStatus());
//    }

//    @Test
//    @DisplayName("-1")
//    public void shouldShowWarningMassageInPageBuyByCardWithWrongHolderAndApprovedCard(){
//        var dashboardPage = new DashboardPage();
//        var buyByCardPage = dashboardPage.openBuyByCardPage();
//        var form = buyByCardPage.form();
//        form.fillForm(approvedCardWithWrongHolder);
//        assertEquals(form.getInputInvalidMessage(), "Неверный формат");
//        assertNull(DBHelper.getPaymentStatus());
//    }

//    @Test
//    @DisplayName("-2")
//    public void shouldShowWarningMassageInPageBuyInCreditWithWrongHolderAndApprovedCard(){
//        var dashboardPage = new DashboardPage();
//        var buyInCredit = dashboardPage.openBuyInCreditPage();
//        var form = buyInCredit.form();
//        form.fillForm(approvedCardWithWrongHolder);
//        assertEquals(form.getInputInvalidMessage(), "Неверный формат");
//        assertNull(DBHelper.getCreditStatus());
//    }

//    @Test
//    @DisplayName("-3")
//    public void shouldShowWarningMassageInPageBuyByCardWithWrongHolderAndDeclinedCard(){
//        var dashboardPage = new DashboardPage();
//        var buyByCardPage = dashboardPage.openBuyByCardPage();
//        var form = buyByCardPage.form();
//        form.fillForm(declinedCardWithWrongHolder);
//        assertEquals(form.getInputInvalidMessage(), "Неверный формат");
//        assertNull(DBHelper.getPaymentStatus());
//    }

//    @Test
//    @DisplayName("-4")
//    public void shouldShowWarningMassageInPageBuyInCreditWithWrongHolderAndDeclinedCard(){
//        var dashboardPage = new DashboardPage();
//        var buyInCredit = dashboardPage.openBuyInCreditPage();
//        var form = buyInCredit.form();
//        form.fillForm(declinedCardWithWrongHolder);
//        assertEquals(form.getInputInvalidMessage(), "Неверный формат");
//        assertNull(DBHelper.getCreditStatus());
//    }

//    @ParameterizedTest
//    @DisplayName("UI parameterized tests buy by card")
//    @CsvFileSource(resources = "/Values.csv")
//    void shouldShowWarningMassageInPageBuyByCard(String number, String month, String year, String owner, String cvc, String message) {
//        var incorrectValues = new Card(number, month, year, owner, cvc);
//        var dashboardPage = new DashboardPage();
//        var buyByCardPage = dashboardPage.openBuyByCardPage();
//        var form = buyByCardPage.form();
//        form.fillForm(incorrectValues);
//        assertEquals(form.getInputInvalidMessage(), message);
//    }

//    @ParameterizedTest
//    @DisplayName("UI parameterized tests buy in credit")
//    @CsvFileSource(resources = "/Values.csv")
//    void shouldShowWarningMassageInPageBuyInCredit(String number, String month, String year, String owner, String cvc, String message) {
//        var incorrectValues = new Card(number, month, year, owner, cvc);
//        var dashboardPage = new DashboardPage();
//        var buyInCredit = dashboardPage.openBuyInCreditPage();
//        var form = buyInCredit.form();
//        form.fillForm(incorrectValues);
//        assertEquals(form.getInputInvalidMessage(), message);
//    }
}
