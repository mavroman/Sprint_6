package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.MainPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestionsTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // создаем WebDriver, который будет работать с нашим браузером
        driver = new ChromeDriver();
        // driver = new FirefoxDriver();
        // ожидание
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvSource({
            "0, '-утки — 400 рублей. Оплата курьеру — наличными или картой.'",
            "1, 'Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.'",
            "2, 'Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.'",
            "3, 'Только начиная с завтрашнего дня. Но скоро станем расторопнее.'",
            "4, 'Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.'",
            "5, 'Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.'",
            "6, 'Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.'",
            "7, 'Да, обязательно. Всем самокатов! И Москве, и Московской области.'"
    })
    public void testQuestionAnswer(int questionIndex, String answer) throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        // открываем сайт
        mainPage.open();
        // принимаем куки
        mainPage.clickCookieButton();

        // тап на вопрос
        mainPage.clickQuestion(questionIndex);

        Thread.sleep(3000);

        // проверяем, что ответ отображается
        assertTrue(mainPage.isAnswerDisplayed(questionIndex),
                "Ответ на вопрос " + questionIndex + " должен отображаться");

        // проверяем текст ответа
        String actualAnswer = mainPage.getAnswerText(questionIndex);
        assertEquals(actualAnswer, answer,
                "Текст ответа не соответствует ожидаемому. Ожидалось: " +
                        answer + ", получено: " + actualAnswer);
    }




}