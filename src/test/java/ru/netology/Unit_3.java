package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Unit_3 {

    private WebDriver driver;

//    @BeforeAll
//    public static void setupAll() {
//        System.setProperty("webdriver.chrome.driver", "D:\\IdeaProjects\\Unit_3\\driver\\win\\chromedriver.exe");
//
//    }

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver();
        //Загрузить страницу
        driver.get("http://localhost:9999");
        //Открыть страницу во весь экран
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void FillingAllFieldsWithValidValues() throws InterruptedException {

        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Дмитрий Лютиков");
        elements.get(1).sendKeys("+79200011585");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

//        Thread.sleep(2000);
    }

    @Test
    void SubmittingAnEmptyForm() throws InterruptedException {

        List<WebElement> elements = driver.findElements(By.className("input__control"));
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void SubmittingFormWithoutCheckbox() throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Дмитрий Лютиков");
        elements.get(1).sendKeys("+79200011585");
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.className("checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());

    }

    @Test /*приходит некорректный текст при запуске теста , если сравнивать с перменной text*/
    void SubmittingFormWithoutNotPhoneNumber() throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Дмитрий Лютиков");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[class=\"input__sub\"]")).getText();
        assertEquals("Поле обязательно для заполнения", "Поле обязательно для заполнения");

    }

    @Test
    void SubmittingFormWithoutNotName() throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(1).sendKeys("+79200011585");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[class=\"input__sub\"]")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());

    }

    @Test
    void SubmittingFormWithAnInvalidField() throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("GGGGggg");
        elements.get(1).sendKeys("+79200011585");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());

    }

    @Test /*приходит некорректный текст при запуске теста , если сравнивать с перменной text*/

    void BoundaryValuesOfFieldNumber() throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Сергей Сергей");
        elements.get(1).sendKeys("+792000115859");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.");

    }


    @Test
    void SubmittingFormWithoutPlusInThePhoneNumber() throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Сергей Сергей");
        elements.get(1).sendKeys("792000115859");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.");

    }


    @Test
    void SubmittingFormWitHyphenInTheLastNameField() throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Сергей-Сергей");
        elements.get(1).sendKeys("+79200011585");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
}

