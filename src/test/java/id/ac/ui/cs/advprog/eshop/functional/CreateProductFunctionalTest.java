package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest(){
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void user_can_create_product_and_see_it_in_list(ChromeDriver driver) throws Exception {
        String name = "Functional Product";
        String qty = "7";

        driver.get(baseUrl + "/product/create");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nameInput")));

        driver.findElement(By.id("nameInput")).sendKeys(name);
        driver.findElement(By.id("quantityInput")).sendKeys(qty);

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("/product/list"));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + name + "']")));
        assertNotNull(driver.findElement(By.xpath("//td[text()='" + name + "']")));

        String quantityText = driver.findElement(By.xpath("//td[text()='" + name + "']/following-sibling::td[1]")).getText();
        assertEquals(qty, quantityText);
    }
}
