package listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import utils.DriverManager;
import utils.ExtentReportManager;

import java.util.Base64;

public class TestListener
        implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite suite) {

        ExtentReportManager.initialize();

        System.out.println(
                "ExtentReports initialized for suite: "
                        + suite.getName()
        );
    }

    @Override
    public void onFinish(ISuite suite) {

        ExtentReportManager.flush();

        System.out.println(
                "ExtentReports generated at: "
                        + ExtentReportManager.getReportPath()
        );
    }

    @Override
    public void onTestStart(ITestResult result) {

        String testName =
                result.getMethod().getMethodName();

        String description =
                result.getMethod().getDescription();

        ExtentReportManager.startTest(testName);

        if (description != null
                && !description.trim().isEmpty()) {

            ExtentReportManager.info(
                    "Description: " + description
            );
        }

        ExtentReportManager.info(
                "Test started"
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        ExtentReportManager.getTest()
                .log(
                        Status.PASS,
                        "Test passed"
                );

        ExtentReportManager.removeTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentReportManager.getTest()
                .log(
                        Status.FAIL,
                        "Test failed: "
                                + result.getThrowable()
                );

        captureScreenshot(result);

        ExtentReportManager.removeTest();
        DriverManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentReportManager.getTest()
                .log(
                        Status.SKIP,
                        "Test skipped"
                );

        if (result.getThrowable() != null) {

            ExtentReportManager.getTest()
                    .log(
                            Status.SKIP,
                            result.getThrowable()
                    );
        }

        ExtentReportManager.removeTest();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(
            ITestResult result) {

        ExtentReportManager.getTest()
                .log(
                        Status.WARNING,
                        "Test failed within success percentage"
                );
    }

    @Override
    public void onStart(ITestContext context) {

        ExtentReportManager.info(
                "TestNG context started: "
                        + context.getName()
        );
    }

    @Override
    public void onFinish(ITestContext context) {

        ExtentReportManager.info(
                "TestNG context finished: "
                        + context.getName()
        );
    }

    /**
     * Captures the current browser screen and attaches
     * it directly to the Extent report.
     */
    private void captureScreenshot(
            ITestResult result) {

        WebDriver driver =
                getDriverFromTestInstance(result);

        if (driver == null) {
            return;
        }

        try {

            if (!(driver instanceof TakesScreenshot)) {
                return;
            }

            TakesScreenshot screenshot =
                    (TakesScreenshot) driver;

            String base64Screenshot =
                    screenshot.getScreenshotAs(
                            OutputType.BASE64
                    );

            ExtentReportManager.getTest()
                    .fail(
                            "Screenshot",
                            MediaEntityBuilder
                                    .createScreenCaptureFromBase64String(
                                            base64Screenshot
                                    )
                                    .build()
                    );

        } catch (Exception e) {

            ExtentReportManager.getTest()
                    .warning(
                            "Could not capture screenshot: "
                                    + e.getMessage()
                    );
        }
    }

    /**
     * Obtains WebDriver from the test class.
     *
     * Your BaseTest should expose the driver as a field.
     */
    private WebDriver getDriverFromTestInstance(
            ITestResult result) {

        Object instance =
                result.getInstance();

        try {

            java.lang.reflect.Field field =
                    findDriverField(
                            instance.getClass()
                    );

            if (field == null) {
                return null;
            }

            field.setAccessible(true);

            Object value =
                    field.get(instance);

            if (value instanceof WebDriver) {
                return (WebDriver) value;
            }

        } catch (Exception e) {

            System.out.println(
                    "Unable to retrieve WebDriver: "
                            + e.getMessage()
            );
        }

        return null;
    }

    private java.lang.reflect.Field findDriverField(
            Class<?> clazz) {

        while (clazz != null) {

            try {

                return clazz.getDeclaredField("driver");

            } catch (NoSuchFieldException ignored) {

                clazz = clazz.getSuperclass();
            }
        }

        return null;
    }
}
