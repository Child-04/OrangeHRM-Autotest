package pages;
import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginPage {
    private Page page;

    // XPath cho error message
    private String errorLocator = "//div[contains(@class,'oxd-alert oxd-alert--error')]";
    private String errorRequied = "//div[contains(@class,'oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message')]";
    // XPath cho input
    private String usernameInput = "//input[@name='username']";
    private String passwordInput = "//input[@name='password']";
    private String loginButton   = "//button[@type='submit']";

    public LoginPage(Page page) {
        this.page = page;
    }

    // method login
    public void loginAs(String username, String password) {
        page.fill(usernameInput, username);
        page.fill(passwordInput, password);
        page.click(loginButton);
    }

    // method lấy message lỗi
    public String getErrorMessage() {
        return page.textContent(errorLocator).trim();
    }
    public String getRequiedMessage() {
        return page.textContent(errorRequied).trim();
    }
}
