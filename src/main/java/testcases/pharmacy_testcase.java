package tests;

from pages.pharmacy_Pages import pharmacy_HomePage;
from coreUtilities.testbase import TestBase;
from coreUtilities.utils import FileOperations;

public class pharmacy_testcase {
    private WebDriver driver;
    private String pageUrl =null;
    privae excelFilePath = System.getProperty("user.dir") + "\\resources\\config.xlsx";


    @BeforeClass
    public void setUp() {
        fileOps = new FileOperations();
        pageUrl = fileOps.readExcelPOI(excelFilePath, "healthApp").get("pageUrl");
        pharmacy_HomePage homePage = new pharmacy_HomePage(driver);
        homepage.navigateToUrl();
       
    }

    

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}