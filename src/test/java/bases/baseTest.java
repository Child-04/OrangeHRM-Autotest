package bases;
import com.microsoft.playwright.*;
import org.testng.annotations.*;


public class baseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );

        context = browser.newContext();
        page = context.newPage();
        page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");


        page.locator("//input[@name='username']").fill("Admin");
        page.locator("//input[@name='password']").fill("admin123");
        page.locator("//button[@type='submit']").click();

        // Chờ trang dashboard load
        page.waitForURL("**/dashboard/index");

    }

    @AfterClass
    public void teardown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
