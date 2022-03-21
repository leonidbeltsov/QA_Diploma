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

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.netology.data.DataGenerator.*;

public class MyTests {

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
        form.fillForm(getApprovedCard());
        form.notificationOkIsVisible();
        assertEquals("APPROVED", DBHelper.getPaymentStatus());
    }

    // Неверное заполнение таблицы order_entity
    @Test
    @DisplayName("-credit approval with approved card")
    public void shouldShowNotificationOkForPurchaseInCreditWithApprovedCard() {
        var dashboardPage = new DashboardPage();
        var buyInCreditPage = dashboardPage.openBuyInCreditPage();
        var form = buyInCreditPage.form();
        form.fillForm(getApprovedCard());
        form.notificationOkIsVisible();
        assertEquals("APPROVED", DBHelper.getCreditStatus());
    }

    // Успех вместо отказа
    @Test
    @DisplayName("-declined with declined card")
    public void shouldShowNotificationWithErrorForPurchaseWithDeclinedCard() {
        var dashboardPage = new DashboardPage();
        var buyByCardPage = dashboardPage.openBuyByCardPage();
        var form = buyByCardPage.form();
        form.fillForm(getDeclinedCard());
        form.notificationErrorIsVisible();
        assertEquals("DECLINED", DBHelper.getPaymentStatus());
    }

    // Успех вместо отказа в кредит
    @Test
    @DisplayName("-credit declined with declined card")
    public void shouldShowNotificationWithErrorForPurchaseInCreditWithDeclinedCard() {
        var dashboardPage = new DashboardPage();
        var buyInCredit = dashboardPage.openBuyInCreditPage();
        var form = buyInCredit.form();
        form.fillForm(getDeclinedCard());
        form.notificationOkIsVisible();
        assertEquals("DECLINED", DBHelper.getCreditStatus());
    }

    // Оба уведомления с неизвестной картой
    @Test
    @DisplayName("-declined with unknown card")
    public void shouldShowNotificationWithErrorForPurchaseWithUnknownCard() {
        var dashboardPage = new DashboardPage();
        var buyByCardPage = dashboardPage.openBuyByCardPage();
        var form = buyByCardPage.form();
        form.fillForm(getUnknownCard());
        form.notificationErrorIsVisible();
        form.closeNotificationError();
        form.notificationOkIsHidden();
        assertEquals("DECLINED", DBHelper.getPaymentStatus());
    }

    // Оба уведомления с неизвестной картой в кредит
    @Test
    @DisplayName("-credit declined with unknown card")
    public void shouldNotBeApprovedInCreditWithUnknownCard() {
        var dashboardPage = new DashboardPage();
        var buyInCredit = dashboardPage.openBuyInCreditPage();
        var form = buyInCredit.form();
        form.fillForm(getUnknownCard());
        form.notificationErrorIsVisible();
        form.closeNotificationError();
        form.notificationOkIsHidden();
        assertEquals("DECLINED", DBHelper.getCreditStatus());
    }

    // Отсутствует предупреждение и отправка формы с кривым именем одобренной картой
    @Test
    @DisplayName("-warning wrong holder with approved card")
    public void shouldShowWarningMassageUnderFieldCardHolderInPageBuyByCardWithWrongHolderAndApprovedCard() {
        var dashboardPage = new DashboardPage();
        var buyByCardPage = dashboardPage.openBuyByCardPage();
        var form = buyByCardPage.form();
        form.fillForm(getApprovedCardWithWrongHolder());
        form.inputInvalid();
//        assertEquals(form.getInputInvalidMessage(), "Неверный формат");
        assertNull(DBHelper.getPaymentStatus());
        assertNull(DBHelper.getCreditStatus());
    }

    // Отсутствует предупреждение и отправка формы с кривым именем и одобренной картой в кредит
    @Test
    @DisplayName("-warning wrong holder with approved card in credit")
    public void shouldShowWarningMassageUnderFieldCardHolderInPageBuyInCreditWithWrongHolderAndApprovedCard() {
        var dashboardPage = new DashboardPage();
        var buyInCredit = dashboardPage.openBuyInCreditPage();
        var form = buyInCredit.form();
        form.fillForm(getDeclinedCardWithWrongHolder());
        form.inputInvalid();
//        assertEquals(form.getInputInvalidMessage(), "Неверный формат");
        assertNull(DBHelper.getPaymentStatus());
        assertNull(DBHelper.getCreditStatus());
    }

    // Сообщение об ошибке Владельца при пустом CVV
    @Test
    @DisplayName("-warning under holder with empty cvv")
    public void shouldNotShowWarningMassageUnderFieldCardHolderInBuyByCardPageWithEmptyCVV() {
        var dashboardPage = new DashboardPage();
        var buyInCredit = dashboardPage.openBuyInCreditPage();
        var form = buyInCredit.form();
        form.fillForm(getCardWithCvvEmpty());
        form.cvvBadFormatError();
        form.cardholderWarningHidden();
    }

    // Сообщение об ошибке Владельца при пустом CVV в кредит
    @Test
    @DisplayName("-warning under holder with empty cvv in credit")
    public void shouldNotShowWarningMassageUnderFieldCardHolderInBuyInCreditPageWithEmptyCVV() {
        var dashboardPage = new DashboardPage();
        var buyInCredit = dashboardPage.openBuyInCreditPage();
        var form = buyInCredit.form();
        form.fillForm(getCardWithCvvEmpty());
        form.cvvBadFormatError();
        form.cardholderWarningHidden();
    }

    // Отправка формы с нулевым месяцем
    @Test
    @DisplayName("-warning under month")
    public void shouldShowWarningMassageUnderFieldMonthInBuyByCardPageWithZeroMonth() {
        var dashboardPage = new DashboardPage();
        var buyByCardPage = dashboardPage.openBuyByCardPage();
        var form = buyByCardPage.form();
        form.fillForm(getCardWithZeroMonth());
        form.inputInvalid();
        //Время для отправки данных в базу данных, в секундах:
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNull(DBHelper.getPaymentStatus());
    }

    // Отправка формы с нулевым месяцем в кредит
    @Test
    @DisplayName("-warning under month")
    public void shouldShowWarningMassageUnderFieldMonthInBuyInCreditPageWithZeroMonth() {
        var dashboardPage = new DashboardPage();
        var buyByCardPage = dashboardPage.openBuyByCardPage();
        var form = buyByCardPage.form();
        form.fillForm(getCardWithZeroMonth());
        form.inputInvalid();
        //Время для отправки данных в базу данных, в секундах:
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNull(DBHelper.getCreditStatus());
    }

    @ParameterizedTest
    @DisplayName("UI parameterized tests buy by card")
    @CsvFileSource(resources = "/Values.csv")
    void shouldShowWarningMassageInPageBuyByCard(String number, String month, String year, String owner, String cvc, String message) {
        var incorrectValues = new Card(number, month, year, owner, cvc);
        var dashboardPage = new DashboardPage();
        var buyByCardPage = dashboardPage.openBuyByCardPage();
        var form = buyByCardPage.form();
        form.fillForm(incorrectValues);
        assertEquals(form.getInputInvalidMessage(), message);
    }

    @ParameterizedTest
    @DisplayName("UI parameterized tests buy in credit")
    @CsvFileSource(resources = "/Values.csv")
    void shouldShowWarningMassageInPageBuyInCredit(String number, String month, String year, String owner, String cvc, String message) {
        var incorrectValues = new Card(number, month, year, owner, cvc);
        var dashboardPage = new DashboardPage();
        var buyInCredit = dashboardPage.openBuyInCreditPage();
        var form = buyInCredit.form();
        form.fillForm(incorrectValues);
        assertEquals(form.getInputInvalidMessage(), message);
    }
}
