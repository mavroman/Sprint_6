package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage {

    private WebDriver driver;

    private final String url = "https://qa-scooter.praktikum-services.ru/";

    // заголовок страницы
    private final By headerHomeTitle = By.className("Home_Header__iJKdX");

    // кнопка "Заказать"
    private final By headerOrderButton = By.xpath("//button[@class='Button_Button__ra12g']");
    private final By homeOrderButton = By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    // раздел "Вопросы о важном"
    private final By questionsUnit = By.className("Home_SubHeader__zwi_E");

    // вопросы
    private final By questionsButtons = By.xpath("//div[@class='accordion__button']");
    private final By questionsAnswers = By.xpath("//div[contains(@class, 'accordion__panel')]");

    // кнопка принятия куки
    private final By cookieButton = By.className("App_CookieButton__3cvqF");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // метод для открытия главной страницы
    public void open() {
        driver.get(url);
    }

    // метод для клика по кнопке "Заказать" в шапке сайта
    public void clickHeaderOrderButton() {
        driver.findElement(headerOrderButton).click();
    }

    // метод для клика по нижней кнопке "Заказать"
    public void clickHomeOrderButton() throws InterruptedException {
        WebElement element = driver.findElement(homeOrderButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        Thread.sleep(500);
        element.click();
    }

    // метод для принятия куки
    public void clickCookieButton() {
        driver.findElement(cookieButton).click();
    }

    // метод для клика по вопросу
    public void clickQuestion(int index) {
        List<WebElement> elements = driver.findElements(questionsButtons);
        if (index >= 0 && index < elements.size()) {
            WebElement question = elements.get(index);
            // Скроллим до вопроса
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", question);
            // тап по вопросу
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);
        }
    }

    // метод для получения текста ответа в аккордеоне по индексу
    public String getAnswerText(int index) {
        List<WebElement> answers = driver.findElements(questionsAnswers);
        if (index >= 0 && index < answers.size()) {
            return answers.get(index).getText();
        }
        return "";
    }

    // метод для проверки, что ответ отображается
    public boolean isAnswerDisplayed(int index) {
        List<WebElement> answers = driver.findElements(questionsAnswers);
        if (index >= 0 && index < answers.size()) {
            return answers.get(index).isDisplayed();
        }
        return false;
    }





}
