package tests;

import bases.baseTest;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest extends baseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpTest() {
        loginPage = new LoginPage(page);

            page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        // Chờ input username xuất hiện
        //page.waitForSelector("//input[@name='username']");

    }

    @Test
    public void testLoginSuccess() {
        loginPage.loginAs("Admin", "admin123");
        page.waitForURL("**/dashboard/index");
        Assert.assertTrue(loginPage.isLoginSuccess(), "Login failed!");
    }


    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"wrongUser", "wrongPass", "Invalid credentials", "invalid"},
                {"", "admin123", "Required", "emptyUser"},
                {"Admin", "", "Required", "emptyPass"},
                {"", "", "Required", "emptyBoth"}
        };
    }

    @Test(dataProvider = "loginData")
    public void InvalidtestLogin(String username, String password, String expectedResult, String caseType) {
        loginPage.loginAs(username, password);

        switch (caseType) {

            case "invalid":
                Assert.assertEquals(loginPage.getInvalidError(), expectedResult, "Invalid login message!");
                break;

            case "emptyUser":
                Assert.assertEquals(loginPage.getUserRequiredError(), expectedResult, "Wrong message Required in Username!");
                break;

            case "emptyPass":
                Assert.assertEquals(loginPage.getPassRequiredError(), expectedResult, "Wrong message Required in Password!");
                break;

            case "emptyBoth":
                Assert.assertEquals(loginPage.getUserRequiredError(), expectedResult, "Wrong message Required in Username!");
                Assert.assertEquals(loginPage.getPassRequiredError(), expectedResult, "Wrong message Required in Password!");
                break;
        }
    }
}