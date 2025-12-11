package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private WebDriver driver;

    private final String url = "https://qa-scooter.praktikum-services.ru/";

    // шаг 1 на форме заказа "Для кого самокат"
    private final By firstNameInput = By.xpath("//input[@placeholder='* Имя']");
    private final By lastNameInput = By.xpath("//input[@placeholder='* Фамилия']");
    private final By adressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroInput = By.xpath("//input[@placeholder='* Станция метро']");
    private final By phoneNumberInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    private final By orderButtonNext = By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    // выпадающий список метро
    private final By metroDropdown = By.className("select-search__select");
    private final By metroStation= By.xpath("//div[contains(@class, 'select-search__select')]");


    // шаг 2 на форме заказа "Про аренду"
    private final By deliveryDateOrder = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    //private final By deliveryDatepicker = By.xpath("//div[@class='react-datepicker']");
    private final By rentalPeriodOrder = By.className("Dropdown-placeholder");
    private final By rentalPeriodOptions = By.xpath("//div[@class='Dropdown-option']");
    private final By blackCheckbox = By.id("black");
    private final By greyCheckbox = By.id("grey");

    private final By commentOrder = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//button[contains(@class, 'Button_Middle') and text()='Заказать']");

    // модальное окно подтверждения заказа
    private final By confirmOrderButton = By.xpath("//button[text()='Да']");
    private final By orderSuccessModal = By.className("Order_ModalHeader__3FDaJ");
    private final By orderSuccessText = By.xpath("//div[contains(@class, 'Order_ModalHeader')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // заполнение формы заказа "Для кого самокат" / шаг 1
    public void firstStepOrder(String firstName, String lastName, String address, String metro, String phone) {
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(adressInput).sendKeys(address);

        // выбор станции метро
        driver.findElement(metroInput).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(metroDropdown));

        // выбор первой подходящей станции
        driver.findElement(metroInput).sendKeys(metro);
        driver.findElements(metroStation).get(0).click();

        driver.findElement(phoneNumberInput).sendKeys(phone);
        driver.findElement(orderButtonNext).click();
    }

    // заполнение формы заказа "Про аренду" / шаг 2
    public void secondStepOrder(String deliveryDate, String rentalPeriod, String color, String comment) {
        // выбор даты
        driver.findElement(deliveryDateOrder).sendKeys(deliveryDate);
        driver.findElement(By.tagName("body")).click();

        // выбор срока аренды
        driver.findElement(rentalPeriodOrder).click();
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Dropdown-menu")));

        // выбор опции по тексту
        for (WebElement option : driver.findElements(rentalPeriodOptions)) {
            if (option.getText().contains(rentalPeriod)) {
                option.click();
                break;
            }
        }

        // выбор цвета
        if ("чёрный".equalsIgnoreCase(color)) {
            driver.findElement(blackCheckbox).click();
        } else if ("серый".equalsIgnoreCase(color)) {
            driver.findElement(greyCheckbox).click();
        }

        // комментарий
        if (comment != null && !comment.isEmpty()) {
            driver.findElement(commentOrder).sendKeys(comment);
        }

        // тап на кнопку заказа
        driver.findElement(orderButton).click();
    }


    // подтверждение заказа
    public void confirmOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
        driver.findElement(confirmOrderButton).click();
    }

    // проверка успешного оформления заказа
    public boolean orderSuccess() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(orderSuccessModal));
            return driver.findElement(orderSuccessText).getText().contains("Заказ оформлен");
        } catch (Exception e) {
            return false;
        }
    }

}
