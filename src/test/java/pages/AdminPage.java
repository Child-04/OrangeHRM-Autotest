package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.Assert;

import java.util.*;

public class AdminPage {
    private Page page;

    // Locators
    private final String adminMenu = "//span[text()='Admin']";
    private final String headerSelector = "//div[@class='oxd-table-header']//div[@role='columnheader']";
    private final String rowSelector = "//div[@class='oxd-table-body']/div[@class='oxd-table-card']";
    private final String cellSelector = "div[role='cell']";

    public AdminPage(Page page) {
        this.page = page;
    }


    // Mở trang Admin
    public void openAdminPage() {
        page.locator(adminMenu).click();
        page.waitForURL("**/admin/viewSystemUsers");
        page.waitForSelector("//div[@class='oxd-table-body']");
    }

    // Lấy headers (bỏ Checkbox và Actions)
    public List<String> getTableHeaders() {
        Locator headerCells = page.locator(headerSelector);
        int colCount = headerCells.count();

        List<String> headers = new ArrayList<>();
        for (int i = 1; i < colCount - 1; i++) { // bỏ checkbox đầu và Actions cuối
            headers.add(headerCells.nth(i).innerText().trim());
        }
        return headers;
    }

    // Lấy toàn bộ table data dạng List<Map>
    public List<Map<String, String>> getUserTableData() {
        List<String> headers = getTableHeaders();
        Locator rows = page.locator(rowSelector);
        int rowCount = rows.count();

        List<Map<String, String>> tableData = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            Locator cells = rows.nth(i).locator(cellSelector);
            int cellCount = cells.count();

            Map<String, String> rowMap = new LinkedHashMap<>();
            for (int j = 1; j < Math.min(cellCount - 1, headers.size() + 1); j++) {
                String cellText = cells.nth(j).innerText().trim();
                rowMap.put(headers.get(j - 1), cellText);
            }
            tableData.add(rowMap);
        }
        return tableData;
    }

    // Hàm thực hiện sort cho cột
    public void sortColumn(String columnName, String sortType) {
        Locator headers = page.locator(headerSelector);
        int colCount = headers.count();

        for (int i = 1; i < colCount - 1; i++) { // bỏ checkbox đầu và Actions cuối
            String headerText = headers.nth(i).innerText().trim();
            if (headerText.equalsIgnoreCase(columnName)) {
                Locator sortIcon = headers.nth(i).locator(".oxd-table-header-sort-icon");
                sortIcon.click();

                Locator dropdown = headers.nth(i).locator(".oxd-table-header-sort-dropdown");
                dropdown.locator("li:has-text('" + (sortType.equals("asc") ? "Ascending" : "Descending") + "')").click();
                page.waitForTimeout(10000); // chờ dữ liệu load lại
                return;
            }
        }
        throw new RuntimeException("Không tìm thấy cột cần sort: " + columnName);
    }

    // Hàm lấy dữ liệu của một cột sau khi sort
    public List<String> getColumnData(String columnName) {
        List<Map<String, String>> tableData = getUserTableData();
        List<String> columnData = new ArrayList<>();

        for (Map<String, String> row : tableData) {
            columnData.add(row.get(columnName));
        }
        return columnData;
    }

    // Hàm kiểm tra dữ liệu đã được sort đúng
    public boolean isSorted(List<String> data, String sortType) {
        List<String> expected = new ArrayList<>(data);
        if (sortType.equals("asc")) {
            Collections.sort(expected);
        } else {
            Collections.sort(expected, Collections.reverseOrder());
        }
        return expected.equals(data);
    }
    public void runSortTest(String columnName, String sortType) {
        sortColumn(columnName, sortType); // gọi trực tiếp vì đang trong class AdminPage
        List<String> actualData = getColumnData(columnName); // gọi đúng cách

        List<String> expectedData = new ArrayList<>(actualData);
        if (sortType.equals("asc")) {
            Collections.sort(expectedData);
        } else {
            Collections.sort(expectedData, Collections.reverseOrder());
        }

        System.out.println("=== Actual Data (" + columnName + " - " + sortType.toUpperCase() + ") ===");
        actualData.forEach(System.out::println);

        System.out.println("=== Expected Data (" + columnName + " - " + sortType.toUpperCase() + ") ===");
        expectedData.forEach(System.out::println);

        Assert.assertEquals(actualData, expectedData,
                "❌ Dữ liệu cột " + columnName + " chưa được sort " + sortType.toUpperCase() + " đúng");
    }

}
