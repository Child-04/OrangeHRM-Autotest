package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdminPage {
    private Page page;

    // Locator
    private String adminMenu = "//span[text()='Admin']";
    private String tableRows = "//div[@class='oxd-table-body']/div[@class='oxd-table-card']";

    // Constructor
    public AdminPage(Page page) {
        this.page = page;
    }

    // Action: click vào Admin menu
    public void openAdminPage() {
        page.locator(adminMenu).click();
        page.waitForURL("**/admin/viewSystemUsers");
        page.waitForSelector("//div[@class='oxd-table-body']");
    }

    // hàm lấy table dưới dạng List<Map<String, String>>
    public List<Map<String, String>> getUserTableData() {
        // ===== 1. Lấy header row (key) =====
        Locator headerCells = page.locator("//div[@class='oxd-table-header']//div[@role='columnheader']");
        int colCount = headerCells.count();
        List<String> headers = new ArrayList<>();
        for (int i = 1; i < colCount - 1; i++) { // bỏ checkbox và cột Action
            headers.add(headerCells.nth(i).innerText().trim());
        }
        System.out.println("Headers: " + String.join(" | ", headers));

        // ===== 2. Lấy body rows (value) =====
        Locator rows = page.locator("//div[@class='oxd-table-body']/div[@class='oxd-table-card']");
        int rowCount = rows.count();
        System.out.println("Total number of rows: " + rowCount);

        List<Map<String, String>> tableData = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            Locator row = rows.nth(i);
            Locator cells = row.locator("div[role='cell']");
            int cellCount = cells.count();

            // Dùng LinkedHashMap để giữ đúng thứ tự headers
            Map<String, String> rowMap = new LinkedHashMap<>();
            for (int j = 1; j < Math.min(cellCount - 1, headers.size()); j++) {
                String cellText = cells.nth(j).innerText().trim();
                rowMap.put(headers.get(j-1), cellText);
            }
            tableData.add(rowMap);
        }

        return tableData;
    }
}
