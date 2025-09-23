package pages;

import com.microsoft.playwright.*;

public class LoginPage {
    private final Page page;

    private final String errorLocator = "//div[contains(@class,'oxd-alert oxd-alert--error')]";
    private final String errorUserReq = "//input[@name='username']/ancestor::div[contains(@class,'oxd-form-row')]//span";
    private final String errorPassReq = "//input[@name='password']/ancestor::div[contains(@class,'oxd-form-row')]//span";

    private final String usernameInput = "//input[@name='username']";
    private final String passwordInput = "//input[@name='password']";
    private final String loginButton = "//button[@type='submit']";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void loginAs(String username, String password) {
        page.fill(usernameInput, username);
        page.fill(passwordInput, password);
        page.click(loginButton);
    }

    public boolean isLoginSuccess() {
        return page.url().contains("/dashboard/index");
    }

    public String getInvalidError() {
        return page.locator(errorLocator).textContent().trim();
    }

    public String getUserRequiredError() {
        return page.locator(errorUserReq).textContent().trim();
    }

    public String getPassRequiredError() {
        return page.locator(errorPassReq).textContent().trim();
    }
}