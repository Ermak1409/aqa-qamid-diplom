package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {
    private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvcField = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement expiredCardError = $(byText("Истёк срок действия карты")).parent().$(".input__sub");
    private SelenideElement incorrectPeriodCard = $(byText("Неверно указан срок действия карты")).parent().$(".input__sub");
    private SelenideElement incorrectFormat = $(byText("Неверный формат")).parent().$(".input__sub");
    private SelenideElement requiredFieldError = $(byText("Поле обязательно для заполнения"));


    public void fillForm(DataHelper.CardInfo cardInfo) {
        cardNumberField.setValue(cardInfo.getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        ownerField.setValue(cardInfo.getOwner());
        cvcField.setValue(cardInfo.getCardCVC());
        continueButton.click();
    }

    public void successFullPayment() {
        $(".notification_status_ok").shouldBe(Condition.visible, Duration.ofSeconds(30));
    }

    public void declinedPayment() {
        $(byCssSelector(".notification_status_error")).shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    public void emptyForm() {
        continueButton.click();
        incorrectFormat.shouldBe(visible);
        requiredFieldError.shouldBe(visible);
    }

    public void cardNumberErrorVisible() {
        incorrectFormat.shouldBe(visible);
    }


    public void monthEmptyFieldErrorVisible() {
        incorrectFormat.shouldBe(visible);
    }


    public void yearEmptyFieldErrorVisible() {
        incorrectFormat.shouldBe(visible);
    }

    public void incorrectPeriodCardVisible() {
        incorrectPeriodCard.shouldBe(visible);
    }

    public void expiredCardErrorVisible() {

        expiredCardError.shouldBe(visible);
    }

    public void ownerErrorVisible() {
        incorrectFormat.shouldBe(visible);

    }

    public void ownerEmptyFieldErrorVisible() {
        requiredFieldError.shouldBe(visible);
    }

    public void cvcErrorVisible() {
        incorrectFormat.shouldBe(visible);


    }


}
