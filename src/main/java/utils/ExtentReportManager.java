package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ExtentReportManager {

    private static ExtentReports extentReports;

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    private static final String REPORT_DIRECTORY =
            "target/ExtentReports";

    private static final String REPORT_PATH =
            REPORT_DIRECTORY + "/RegressionReport.html";

    private static final String CONFIG_PATH =
            "src/main/java/config/reporter.json";

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private ExtentReportManager() {
    }

    public static synchronized ExtentReports initialize() {

        if (extentReports != null) {
            return extentReports;
        }

        try {
            Path reportDirectory = Paths.get(REPORT_DIRECTORY);
            Files.createDirectories(reportDirectory);

            ExtentSparkReporter reporter =
                    new ExtentSparkReporter(REPORT_PATH);

            Path configFile = Paths.get(CONFIG_PATH);

            if (Files.exists(configFile)) {
                try {
                    reporter.loadJSONConfig(configFile.toString());
                } catch (Exception e) {
                    System.out.println(
                            "WARNING: Could not load reporter.json: "
                                    + e.getMessage()
                    );
                }
            }

            extentReports = new ExtentReports();

            extentReports.attachReporter(reporter);

            extentReports.setSystemInfo(
                    "Execution Date",
                    LocalDateTime.now().format(DATE_FORMAT)
            );

            extentReports.setSystemInfo(
                    "OS",
                    System.getProperty("os.name")
            );

            extentReports.setSystemInfo(
                    "OS Version",
                    System.getProperty("os.version")
            );

            extentReports.setSystemInfo(
                    "Java",
                    System.getProperty("java.version")
            );

            extentReports.setSystemInfo(
                    "Browser",
                    System.getProperty(
                            "browser",
                            "chrome"
                    )
            );

            extentReports.setSystemInfo(
                    "Environment",
                    System.getProperty(
                            "environment",
                            "qa"
                    )
            );

            extentReports.setSystemInfo(
                    "Headless",
                    System.getProperty(
                            "headless",
                            "false"
                    )
            );

            return extentReports;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Could not initialize ExtentReports",
                    e
            );
        }
    }

    public static ExtentTest startTest(String testName) {

        if (extentReports == null) {
            initialize();
        }

        ExtentTest test =
                extentReports.createTest(testName);

        extentTest.set(test);

        return test;
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void info(String message) {

        if (getTest() != null) {
            getTest().info(message);
        }
    }

    public static void pass(String message) {

        if (getTest() != null) {
            getTest().pass(message);
        }
    }

    public static void warning(String message) {

        if (getTest() != null) {
            getTest().warning(message);
        }
    }

    public static void fail(String message) {

        if (getTest() != null) {
            getTest().fail(message);
        }
    }

    public static void fail(Throwable throwable) {

        if (getTest() != null) {
            getTest().fail(throwable);
        }
    }

    public static synchronized void flush() {

        if (extentReports != null) {
            extentReports.flush();

            System.out.println(
                    "Report generated at: "
                            + Paths.get(REPORT_PATH)
                            .toAbsolutePath()
            );
        }
    }

    public static void removeTest() {
        extentTest.remove();
    }

    public static String getReportPath() {
        return REPORT_PATH;
    }
}
