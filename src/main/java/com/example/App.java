package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class App {

    public static void main(String[] args) {

        // 🔥 IMPORTANT: Headless setup for Jenkins
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {

            driver.manage().window().setSize(new Dimension(1920, 1080));

            // ===================== SITE 1 =====================
            driver.get("https://www.saucedemo.com/");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
                    .sendKeys("standard_user");

            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            System.out.println("SauceDemo login successful");

            // ===================== SITE 2 =====================
            driver.switchTo().newWindow(WindowType.TAB);
            driver.get("https://automationexercise.com/products");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_product")))
                    .sendKeys("Men Tshirt");

            driver.findElement(By.id("submit_search")).click();

            WebElement product = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("a[data-product-id='2']")
                    )
            );

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", product);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", product);

            WebElement viewCart = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.cssSelector("#cartModal a[href='/view_cart']")
                    )
            );

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", viewCart);

            System.out.println("Automation Exercise product added to cart");

            // ===================== SITE 3 =====================
            driver.switchTo().newWindow(WindowType.TAB);
            driver.get("https://practicetestautomation.com/practice-test-login/");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))
                    .sendKeys("student");

            driver.findElement(By.id("password")).sendKeys("Password123");
            driver.findElement(By.id("submit")).click();

            System.out.println("Practice Test Automation login successful");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
