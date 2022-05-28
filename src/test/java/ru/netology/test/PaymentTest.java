package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataBase;
import ru.netology.data.DataHelper;
import ru.netology.page.Page;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.CardInfo.*;

public class PaymentTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
        DataBase.cleanTable();
    }

//    Позитивные сценарии

    // Оплата APPROVED картой Пройден
    @Test
    void shouldPayByCardApproved() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.successFullPayment();
        assertEquals("APPROVED", DataBase.getPaymentStatus());

    }

    // Оплата DECLINED картой Не Пройден Issue №1
    @Test
    void shouldPayByCardDeclined() {
        val cardInfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.declinedPayment();
        assertEquals("DECLINED", DataBase.getPaymentStatus());

    }

//    Негативные сценарии

    // Отправка пустой формы Пройден
    @Test
    void shouldPayByCardEmptyForm() {
        val Page = new Page();
        Page.payByCard();
        val paymentPage = new PaymentPage();
        paymentPage.emptyForm();
    }

    // Пустое поле номер карты Пройден
    @Test
    void shouldPayByCardEmptyCard() {
        val cardInfo = new DataHelper.CardInfo(null, getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    // Короткий номер карты Пройден
    @Test
    void shouldPayByCardShortCard() {
        val cardInfo = new DataHelper.CardInfo(getShortCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    // Номер карты 0000 Не Пройден Issue №2
    @Test
    void shouldPayByCardWithNumber0() {
        val cardInfo = new DataHelper.CardInfo(getCardNumber0(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }


    // Пустое поле месяц Пройден
    @Test
    void shouldPayByCardEmptyMonth() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), null, getValidYear(), getOwnerName(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    // Месяц меньше текущего Не Пройден Issue №3
    @Test
    void shouldPayByCardWithPastMonth() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getPastMonth(), getValidYear(), getOwnerName(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.expiredCardErrorVisible();
    }

    // Месяц 00 Пройден
    @Test
    void shouldPayByCardWithMonth00() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthWithNulls(), getValidYear(), getOwnerName(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    //    Месяц больше 12 Пройден
    @Test
    void shouldPayByCardWithMonthOver12() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthOver12(), getValidYear(), getOwnerName(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }


    // Пустое поле год Пройден
    @Test
    void shouldPayByCardWithoutYear() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), null, getOwnerName(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorVisible();
    }


    // Год меньше текущего Пройден
    @Test
    void shouldPayByCardWithPastYear() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getPastYear(), getOwnerName(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.expiredCardErrorVisible();
    }

    // Год 00 Пройден
    @Test
    void shouldPayByCardWithNullsYear() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getNullsYear(), getOwnerName(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.expiredCardErrorVisible();
    }

    // Год больше срока действия карты Пройден
    @Test
    void shouldPayByCardWithFutureYear() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getFutureYear(), getOwnerName(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorFuture();
    }


    // Поле владелец пустое Пройден
    @Test
    void shouldPayByCardWithoutName() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), null, getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // В поле владелец пробел Пройден
    @Test
    void shouldPayByCardWithNameSpace() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameSpace(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // ФИО владельца со спец символами Не Пройден Issue №4
    @Test
    void shouldPayByCardWithNameWithSigns() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameWithSigns(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // ФИО владельца кириллицей Не Пройден Issue №5
    @Test
    void shouldPayByCardWithNameInRussian() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameInRussia(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // ФИО владельца цифрами Не Пройден Issue №6
    @Test
    void shouldPayByCardWithNameInDigits() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameInDigits(), getCVC());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    //    Поле CVC пустое Пройден
    @Test
    void shouldPayByCardWithoutCVC() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), null);
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    //    Поле CVC с двумя цифрами Пройден
    @Test
    void shouldPayByCardWithCVCWithTwoDigits() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVCShort());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    // Поле CVC с 000 Не Пройден Issue №7
    @Test
    void shouldPayByCardWithCVCWithNulls() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVCWithNulls());
        val Page = new Page();
        val paymentPage = Page.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcErrorVisible();
    }


}
