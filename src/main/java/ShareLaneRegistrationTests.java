import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

public class ShareLaneRegistrationTests {
    public static final String url = "https://sharelane.com/";
    public static final String signUpUrl = "https://sharelane.com/cgi-bin/register.py";
    public static final String correctZipCode = "12345";
    public static final String incorrectZipCode = "1234";

    @Test
    public void goingThrowTheFLowToRegistrationPage() {
        open(url);
        $(By.xpath("//*[text()='ENTER']")).click();
        $(By.xpath("//*[text()='Sign up']")).click();
        String currentUrl = url();
        Assert.assertEquals(currentUrl, signUpUrl);
        Selenide.closeWebDriver();
    }

    @Test
    public void linkShouldEndsWithEnteredZipcodeAfterContinue() {
        open(signUpUrl);
        $(By.name("zip_code")).sendKeys(correctZipCode);
        $(By.cssSelector("[value=Continue]")).click();
        String expectedUrl = "https://sharelane.com/cgi-bin/register.py?page=1&zip_code=12345";
        Assert.assertEquals(expectedUrl, url());
        Selenide.closeWebDriver();
    }

    @Test
    public void errorMessageShouldBeDisplayedWith_4_digitsInZipCodeField() {
        open(signUpUrl);
        $(By.name("zip_code")).sendKeys(incorrectZipCode);
        $(By.cssSelector("[value=Continue]")).click();
        String errorMessage = By.cssSelector(".error_message").toString();
        Assert.assertEquals(errorMessage, By.cssSelector(".error_message").toString());
        Selenide.closeWebDriver();
    }

    @Test
    public void zipCodeWithEmptyFileds_errorMsgShouldBeDisplayed() {
        open(signUpUrl);
        $(By.cssSelector("[value=Continue]")).click();
        String errorMessage = By.cssSelector(".error_message").toString();
        Assert.assertEquals(errorMessage, By.cssSelector(".error_message").toString());
        Selenide.closeWebDriver();
    }

    @Test
    public void registrationWithEmptyFileds_errorMsgShouldBeDisplayed() {
        open(signUpUrl);
        $(By.name("zip_code")).sendKeys(correctZipCode);
        $(By.cssSelector("[value=Continue]")).click();
        $(By.cssSelector("[value=Register]")).click();
        String errorMessage = By.cssSelector(".error_message").toString();
        Assert.assertEquals(errorMessage, By.cssSelector(".error_message").toString());
        Selenide.closeWebDriver();
    }

    @Test
    public void correctFieldsRegistration() {
        open(signUpUrl);
        $(By.name("zip_code")).sendKeys(correctZipCode);
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
