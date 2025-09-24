package Bases;


import org.testng.annotations.BeforeClass;

public class LoggedInBaseTest extends baseTest {

    @BeforeClass
    @Override
    public void setup() {
        super.setup(); // gọi setup từ BaseTest

        page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        page.fill("//input[@name='username']", "Admin");
        page.fill("//input[@name='password']", "admin123");
        page.click("//button[@type='submit']");
        page.waitForURL("**/dashboard/index");
    }
}
