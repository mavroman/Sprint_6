package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.MainPage;
import pages.OrderPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // создаю WebDriver, который будет работать с нашим браузером
        driver = new ChromeDriver();
        // driver = new FirefoxDriver();
        // ожидание
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @ParameterizedTest
    @CsvSource({
            "Рома, Романов, ул. Пушкина 1, Лубянка, +79046000001, 21.12.2025, сутки, чёрный, Не звонить",
            "Тест, Тестов, Тестовая 999, Сокольники, +79999999999, 30.12.2025, трое суток, серый, Позвонить"
    })
    public void testOrderHeaderButton(
            String firstName, String lastName, String address,
            String metroStation, String phone, String deliveryDate,
            String rentalPeriod, String color, String comment) {

        MainPage mainPage = new MainPage(driver);
        // открываем сайт
        mainPage.open();
        // принимаем куки
        mainPage.clickCookieButton();
        // тап по верхней кнопке заказа
        mainPage.clickHeaderOrderButton();

        // заполнение формы заказа
        OrderPage orderPage = new OrderPage(driver);
        orderPage.firstStepOrder(firstName, lastName, address, metroStation, phone);
        orderPage.secondStepOrder(deliveryDate, rentalPeriod, color, comment);
        orderPage.confirmOrder();

        // проверка успешного оформления
        assertTrue(orderPage.orderSuccess(),"Заказ должен быть успешно оформлен");
    }

    // тест с нижней кнопкой
    @ParameterizedTest
    @CsvSource({
            "Рома, Романов, ул. Пушкина 1, Лубянка, +79046000001, 21.12.2025, сутки, чёрный, Не звонить",
            "Тест, Тестов, Тестовая 999, Сокольники, +79999999999, 30.12.2025, трое суток, серый, Позвонить"
    })
    public void testOrderHomeButton(
            String firstName, String lastName, String address,
            String metroStation, String phone, String deliveryDate,
            String rentalPeriod, String color, String comment) throws InterruptedException {

        MainPage mainPage = new MainPage(driver);
        // открываем сайт
        mainPage.open();
        // принимаем куки
        mainPage.clickCookieButton();
        // тап по верхней кнопке заказа
        mainPage.clickHomeOrderButton();

        // заполнение формы заказа
        OrderPage orderPage = new OrderPage(driver);
        orderPage.firstStepOrder(firstName, lastName, address, metroStation, phone);
        orderPage.secondStepOrder(deliveryDate, rentalPeriod, color, comment);
        orderPage.confirmOrder();

        // проверка успешного оформления
        assertTrue(orderPage.orderSuccess(),"Заказ должен быть успешно оформлен");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
