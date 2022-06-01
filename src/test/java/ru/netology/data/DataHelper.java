package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.Year;
import java.util.Locale;

public class DataHelper {

    @Value
    @RequiredArgsConstructor
    public static class CardInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String owner;
        private String cardCVC;

    }


    private DataHelper() {
    }

    public static Faker faker = new Faker(new Locale("en"));


    //    Заполнение поля Номер карты
    public static String getApprovedCardNumber() {
        return ("1111 2222 3333 4444");
    }

    public static String getDeclinedCardNumber() {
        return ("5555 6666 7777 8888");
    }

    public static String getShortCardNumber() {
        return ("1111 2222 3333 444");
    }

    public static String getCardNumber0() {
        return ("0000 0000 0000 0000");
    }

    //    Заполнение поля Месяц
    public static String getValidMonth() {
        LocalDate localDate = LocalDate.now();
        return String.format("%02d", localDate.getMonthValue());
    }

    public static String getPastMonth() {
        LocalDate localDate = LocalDate.now();
        return String.format("%02d", localDate.getMonthValue() - 2);
    }

    public static String getMonthWithNulls() {
        return ("00");
    }

    public static String getMonthOver12() {
        return ("13");
    }


    //    Заполнение поля Год
    public static String getValidYear() {
        return String.format("%ty", Year.now());
    }

    public static String getPastYear() {
        Year Year = java.time.Year.now();
        return String.format("%ty", Year.minusYears(1));
    }

    public static String getNullsYear() {

        return ("00");
    }

    public static String getFutureYear() {
        Year Year = java.time.Year.now();
        return String.format("%ty", Year.plusYears(6));
    }


    //    Заполнение поля Владелец
    public static String getOwnerName() {
        return faker.name().fullName();
    }

    public static String getOwnerNameSpace() {
        return " ";
    }

    public static String getOwnerNameWithSigns() {
        return "?& Ivan";
    }

    public static String getOwnerNameInRussia() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getOwnerNameInDigits() {
        return "1234";
    }


    //    Заполнение поля CVC
    public static String getCVC() {
        return "234";
    }


    public static String getCVCShort() {
        return "21";
    }

    public static String getCVCWithNulls() {
        return "000";
    }

}


