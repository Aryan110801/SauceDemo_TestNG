import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TestNG {
    @Test(dataProvider = "credentials")
    public void testing(String Scenario, String Username, String Password){
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Aryan.Pandey\\Downloads\\chromedriver_win32\\chromedriver.exe");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys(Username);
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys(Password);
        driver.findElement(By.xpath("//input[@id='login-button']")).click();

        if (Scenario.equals("Both Correct")){
            WebElement login = driver.findElement(By.xpath("//span[@class='title']"));
            Assert.assertTrue(login.isDisplayed(), "Successfull login");
            driver.quit();
        }
        else if (Scenario.equals("Both Wrong")) {
            String errormessage = driver.findElement(By.xpath("//div[@class='error-message-container error']")).getText();
            Assert.assertEquals(errormessage,"Epic sadface: Username and password do not match any user in this service");
            driver.quit();
        } else if (Scenario.equals("Both null")) {
            String errormessage = driver.findElement(By.xpath("//h3[@data-test='error']")).getText();
            Assert.assertEquals(errormessage,"Epic sadface: Username is required");
            driver.quit();
        } else if (Scenario.equals("wrong username")) {
            String errormessage = driver.findElement(By.xpath("//h3[@data-test='error']")).getText();
            Assert.assertEquals(errormessage,"Epic sadface: Username and password do not match any user in this service");
            driver.quit();
        } else if (Scenario.equals("wrong password")) {
            String errormessage = driver.findElement(By.xpath("//h3[@data-test='error']")).getText();
            Assert.assertEquals(errormessage, "Epic sadface: Username and password do not match any user in this service");
            driver.quit();
        }
    }
    @DataProvider(name = "credentials")
    public Object[][] getdata(){
        return new Object[][]{
                {"Both Correct","standard_user","secret_sauce"},
                {"Both Wrong","aryan123","aryan110801"},
                {"Both null","",""},
                {"wrong username","aryan123","secret_sauce"},
                {"wrong password","standard_user","aryan110801"}
        };
    }
}
