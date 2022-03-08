import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;


public class CRUD_Contact {

    private String fname = "CRUDFirstName";
    private String lname = "CRUDLastName";
    private String email = "CRUD@example.com";
    public String chromeDriverPath = "drivers/chromedriver";
    public WebDriver driver;
    public WebDriverWait wait;
    public int defaultWait = 15;

    public void enterValues (String inputName, String inputValue){
        WebElement fieldValue = driver.findElement(By.xpath("//label[normalize-space(.)=\"" + inputName + "\"]/ancestor::div[contains(@class,'ussr-component-form-control')]"));
        fieldValue.sendKeys(inputValue);
        fieldValue.findElement(By.cssSelector("op-button-icon.ussr-form-control-action-save")).click();
    }

    /*
    @AfterClass
    public void close(){
        driver.close();
        driver.quit();
    }
*/
    @Test
    public void setup() {

        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();

    }
    @Test
    public void testLogin() {
        // Within the browser, navigate to a URL as provided as a String
        driver.navigate().to("https://app.ontraport.com/login.php");
        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.id("login_button"));
        username.sendKeys("test@example.com");
        password.sendKeys("fakepass123");
        login.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ontraport_panel_action_new")));
        System.out.println("Login Successful!");

    }

   @Test (dependsOnMethods = "testLogin")
    public void testCreateContact() {
       driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
       WebElement CreateButton = driver.findElement(By.id("ontraport_panel_action_new"));
       CreateButton.click();
       driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
       enterValues("First Name", fname);
       enterValues("Last Name", lname);
       enterValues("Email", email);
       System.out.println("Contact Created!");


    }

   @Test (dependsOnMethods = "testCreateContact")
    public void testReadContact() {
       String createdContact = driver.getCurrentUrl();
       driver.get("https://app.ontraport.com/#!/contact/listAll");
       driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
       WebElement SearchButton = driver.findElement(By.cssSelector(".chrome-action-bar-search"));
       SearchButton.click();
       WebElement SearchInput = driver.findElement(By.cssSelector(".chrome-action-bar-search-input input"));
       SearchInput.sendKeys(email + Keys.RETURN);
       driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
       WebElement CreatedContact = driver.findElement(By.cssSelector("[title='" + email + "']"));
       CreatedContact.click();
       System.out.println("Contact Found!");



   }

    @Test (dependsOnMethods = "testReadContact")
    public void testUpdateContact() {
        fname = "CRUDUpdatedFirstName";
        lname = "CRUDUpdatedLastName";
        email = "CRUDUpdated@example.com";
        enterValues("First Name", fname);
        enterValues("Last Name", lname);
        enterValues("Email", email);
        System.out.println("Contact Updated!");
    }

}

