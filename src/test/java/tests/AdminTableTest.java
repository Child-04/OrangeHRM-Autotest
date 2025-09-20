package tests;

import bases.baseTest;
import org.testng.annotations.Test;
import com.microsoft.playwright.*;
import java.util.List;
public class AdminTableTest extends baseTest{

    @Test
    public void readAdminTable() {
        // Điều hướng đến trang Admin
        page.locator("//span[text()='Admin']").click();
        page.waitForURL("**/admin/viewSystemUsers");
        page.waitForSelector("//div[@class='oxd-table']");

        page.waitForTimeout(5000); // chờ 5 giây

        // Lấy danh sách các hàng (bỏ qua header)
        Locator rows = page.locator("//div[@class='oxd-table-body']/div[@class='oxd-table-card']");
        int rowCount = rows.count();
        System.out.println("Tổng số hàng: " + rowCount);

        // 4. Lặp qua từng hàng và lấy dữ liệu từng cột

        for (int i = 0; i < rowCount; i++) {
            Locator row = rows.nth(i);
            String username = row.locator("div[role='cell']:nth-child(2)").textContent().trim();
            String userRole = row.locator("div[role='cell']:nth-child(3)").textContent().trim();
            String empName  = row.locator("div[role='cell']:nth-child(4)").textContent().trim();
            String status   = row.locator("div[role='cell']:nth-child(5)").textContent().trim();

            System.out.println("Row " + (i + 1) + " → " +
                    "Username: " + username +
                    ", Role: " + userRole +
                    ", Employee: " + empName +
                    ", Status: " + status);
        }

    }


}
