package ohtu;

import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Tester {

    public static void main(String[] args) {
     // System.setProperty("webdriver.chrome.driver", "/Users/mikkoruuskanen/Downloads/chromedriver"); 

        WebDriver driver = new HtmlUnitDriver();
        
        navigateToLoginPage(driver);
        login(driver, "pekka", "akkep");
        
        navigateToLoginPage(driver);
        //epäonnistunut kirjautuminen: oikea käyttäjätunnus, väärä salasana
        login(driver, "pekka", "foobar");
        
        // epäonnistunut kirjautuminen: ei-olemassaoleva käyttäjätunnus
        login(driver, "foobar", "foobar");
        
        //uuden käyttäjätunnuksen luominen
        signUp(driver);
        
        //uuden käyttäjätunnuksen luomisen jälkeen tapahtuva ulkoskirjautuminen sovelluksesta
        goToMainPageAndLogout(driver);
        
        driver.quit();
    }
    
    private static void signUp(WebDriver driver) {
        Random r = new Random();
        driver.get("http://localhost:4567/user");
        sleep(1);
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys("testaaja"+r.nextInt());
        element = driver.findElement(By.name("password"));
        element.sendKeys("testaa");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("testaa");
        
        sleep(1);
        element.submit();
        sleep(1);
    }
    
    private static void navigateToLoginPage(WebDriver driver) {
        driver.get("http://localhost:4567");
        
        sleep(1);
        
        WebElement element = driver.findElement(By.linkText("login"));
        element.click();

        sleep(1);
    }
    
    private static void login(WebDriver driver, String username, String password) {
   
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        
        sleep(1);
        element.submit();

        sleep(1);
    }
    
    private static void sleep(int n){
        try{
            Thread.sleep(n*1000);
        } catch(Exception e){}
    }

    private static void goToMainPageAndLogout(WebDriver driver) {
        WebElement element = driver.findElement(By.linkText("continue to application mainpage"));
        element.click();
        sleep(1);
        
        element = driver.findElement(By.linkText("logout"));
        element.click();
        sleep(1);
    }
}
