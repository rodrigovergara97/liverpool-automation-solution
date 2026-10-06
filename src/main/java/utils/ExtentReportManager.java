package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import reports.ExtenReport;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ExtentReportManager {

    private static ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static Map<Long, WebDriver> driverMap = new HashMap<>();
    private static final String reporterConfigPath = System.getProperty("user.dir")+"src/main/java/config/reporter.json";

    public static ExtentReports initialize(){
        if(Objects.isNull(extentReports)){
            ExtentSparkReporter reporter = new ExtentSparkReporter("target/RegressionReport.html");
            try {
                reporter.loadJSONConfig(reporterConfigPath);
                extentReports = new ExtentReports();
                extentReports.attachReporter(reporter);
                extentReports.setSystemInfo("OS version",System.getProperty("os.name"));
                extentReports.setSystemInfo("Environment",System.getProperty("env"));
                extentReports.setSystemInfo("Java version","java.version");
                extentReports.setSystemInfo("Username ","user.name");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return extentReports;
    }

    public static ExtentTest startTest(String testName){
        ExtentTest extentTest = extentReports.createTest(testName);
        test.set(extentTest);
        return extentTest;
    }

    public static void endTest(){
        extentReports.flush();
    }

    public static ExtentTest getTest(){
        return test.get();
    }

    public static ExtentTest sendTestTest(String testName){
        return extentReports.createTest(testName);
    }
}
