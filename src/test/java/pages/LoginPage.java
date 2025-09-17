package pages;
import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginPage {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)); // headless(true) nếu không cần mở browser
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterMethod
    public void tearDown() {
        browser.close();
        playwright.close();
    }

    @Test
    public void testValidLogin() {
        page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // nhập username và password đúng
        page.fill("input[name='username']", "Admin");
        page.fill("input[name='password']", "admin123");
        page.click("button[type='submit']");

        // kiểm tra đã chuyển hướng sang dashboard
        page.waitForURL("**/dashboard/index");
        Assert.assertTrue(page.url().contains("dashboard/index"), "Login failed with valid credentials");
    }

    @Test
    public void testInvalidLogin() {
        page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // nhập username sai
        page.fill("input[name='username']", "wrongUser");
        page.fill("input[name='password']", "wrongPass");
        page.click("button[type='submit']");

        // kiểm tra thông báo lỗi xuất hiện
        String errorText = page.textContent("div.oxd-alert.oxd-alert--error");
        Assert.assertEquals(errorText.trim(), "Invalid credentials");
    }
}
