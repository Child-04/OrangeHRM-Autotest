package tests;

import bases.baseTest;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends baseTest {
    private LoginPage loginPage;

    @Test
    public void testValidLogin() {
        loginPage.loginAs("Admin", "admin123");
        page.waitForURL("**/dashboard/index");
        Assert.assertTrue(page.url().contains("/dashboard/index"),
                "Login không thành công, không thấy 'dashboard' trong URL!");

    }

    @Test
    public void testInvalidLogin_WrongUserAndPass() {
        loginPage.loginAs("wrongUser", "wrongPass");
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid credentials");
    }

    @Test
    public void testInvalidLogin_CorrectUserWrongPass() {
        loginPage.loginAs("Admin", "wrongPass");
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid credentials");
    }

    @Test
    public void testInvalidLogin_WrongUserCorrectPass() {
        loginPage.loginAs("wrongUser", "admin123");
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid credentials");
    }

    //@Test
    public void testInvalidLogin_EmptyUserAndPass() {
        loginPage.loginAs(" ", " ");
        Assert.assertEquals(loginPage.getRequiedMessage(), "Required");
    }

    //@Test
    public void testInvalidLogin_EmptyUser() {
        loginPage.loginAs("", "admin123");
        Assert.assertEquals(loginPage.getRequiedMessage(), "Required");
    }

    //@Test
    public void testInvalidLogin_EmptyPass() {
        loginPage.loginAs("Admin", "");
        Assert.assertEquals(loginPage.getRequiedMessage(), "Required");
    }
}
