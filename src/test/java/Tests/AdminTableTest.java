package Tests;

import Bases.LoggedInBaseTest;
//import pages.AdminPage;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class AdminTableTest extends LoggedInBaseTest {

    @Test
    public void test_GetUserTable() {
        pages.AdminPage adminPage = new pages.AdminPage(page);
        adminPage.openAdminPage();

        // Call method in AdminPage
        List<Map<String, String>> tableData = adminPage.getUserTableData();

        for (Map<String, String> row : tableData) {
            System.out.println(row);
        }

        // Ví dụ: lấy tất cả usernames
        System.out.println("=== All Usernames ===");
        for (Map<String, String> row : tableData) {
            System.out.println(row.get("Username")); // cột Username
        }

        page.waitForTimeout(30000);

    }
}
