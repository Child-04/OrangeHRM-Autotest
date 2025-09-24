package Tests;

import Bases.LoggedInBaseTest;
import org.testng.annotations.BeforeMethod;
import pages.AdminPage;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminTable_SortingTest extends LoggedInBaseTest {


    private AdminPage adminPage;

    @BeforeMethod
    public void goToAdminPage() {
        adminPage = new AdminPage(page);
        adminPage.openAdminPage();
    }


    @Test
    public void testSortEmployeeNameDesc() {
        adminPage.runSortTest("Employee Name", "desc");
        page.waitForTimeout(10000);
    }



}
