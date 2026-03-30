package com.framework.tests;

import com.framework.config.ConfigLoader;
import com.framework.pageLocators.LoginPage;
import org.testng.annotations.Test;

public class TC00_LoginSmokeTest extends BaseTest {

    @Test(groups = { "smoke", "login" })
    public void loginPageShowsRequiredFields() {
        e.openUrlAndValidate(ConfigLoader.get("login.url", "https://tap.hanover.com"),
                LoginPage.userId, "Login page", "Open the login page");
        e.validateOnPage(LoginPage.password, "Login page");
        e.screenshot("login_page_smoke");
    }

}
