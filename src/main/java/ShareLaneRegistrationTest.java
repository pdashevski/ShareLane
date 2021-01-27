import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
//*
public class ShareLaneRegistrationTest {
    public static final String URL = "https://sharelane.com/";
    public static final String SIGN_UP_URL = "https://sharelane.com/cgi-bin/register.py";
    public static final String CORRECT_ZIPCODE = "12345";
    public static final String INCORRECT_ZIPCODE = "1234";

    @Test
    public void goingThrowTheFLowToRegistrationPage() {
        open(URL);
        $(By.xpath("//*[text()='ENTER']")).click();
        $(By.xpath("//*[text()='Sign up']")).click();
        String currentUrl = url();
        Assert.assertEquals(currentUrl, SIGN_UP_URL);
        Selenide.closeWebDriver();
    }

    @Test
    public void linkShouldEndsWithEnteredZipcodeAfterContinue() {
        open(SIGN_UP_URL);
        $(By.name("zip_code")).sendKeys(CORRECT_ZIPCODE);
        $(By.cssSelector("[value=Continue]")).click();
        String expectedUrl = "https://sharelane.com/cgi-bin/register.py?page=1&zip_code=12345";
        Assert.assertEquals(expectedUrl, url());
        Selenide.closeWebDriver();
    }

    @Test
    public void errorMessageShouldBeDisplayedWith_4_digitsInZipCodeField() {
        open(SIGN_UP_URL);
        $(By.name("zip_code")).sendKeys(INCORRECT_ZIPCODE);
        $(By.cssSelector("[value=Continue]")).click();
        String errorMessage = By.cssSelector(".error_message").toString();
        Assert.assertEquals(errorMessage, By.cssSelector(".error_message").toString());
        Selenide.closeWebDriver();
    }

    @Test
    public void zipCodeWithEmptyFileds_errorMsgShouldBeDisplayed() {
        open(SIGN_UP_URL);
        $(By.cssSelector("[value=Continue]")).click();
        String errorMessage = By.cssSelector(".error_message").toString();
        Assert.assertEquals(errorMessage, By.cssSelector(".error_message").toString());
        Selenide.closeWebDriver();
    }

    @Test
    public void registrationWithEmptyFileds_errorMsgShouldBeDisplayed() {
        open(SIGN_UP_URL);
        $(By.name("zip_code")).sendKeys(CORRECT_ZIPCODE);
        $(By.cssSelector("[value=Continue]")).click();
        $(By.cssSelector("[value=Register]")).click();
        String errorMessage = By.cssSelector(".error_message").toString();
        Assert.assertEquals(errorMessage, By.cssSelector(".error_message").toString());
        Selenide.closeWebDriver();
    }

    @Test
    public void correctFieldsRegistration() {
        open(SIGN_UP_URL);
        $(By.name("zip_code")).sendKeys(CORRECT_ZIPCODE);
        $(By.cssSelector("[value=Continue]")).click();
        $(By.name("first_name")).sendKeys("Alex");
        $(By.name("last_name")).sendKeys("Petrov");
        $(By.name("email")).sendKeys("test@test.com");
        $(By.name("password1")).sendKeys("123456");
        $(By.name("password2")).sendKeys("123456");
        $(By.cssSelector("[value=Register]")).click();
        String successRegistrationMsg = By.cssSelector(".confirmation_message").toString();
        Assert.assertEquals(successRegistrationMsg, By.cssSelector(".confirmation_message").toString());
        Selenide.closeWebDriver();
    }
}
