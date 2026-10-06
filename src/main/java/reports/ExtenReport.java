package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.Objects;

public final class ExtenReport {

    private ExtenReport(){}
    private static ExtentReports extentReports;
    private static ExtentTest extentTest;

//The ExtentSparkReporter is used to create an HTMLQ!~ file and accepts a file path to the directory where the output should be saved.


    private static void initReports(){
        if(Objects.isNull(extentReports)){
            extentReports = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReports/SparkReport.html");
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("Automation Test Report");
            spark.config().setReportName("Execution Summary");
            extentReports = new ExtentReports();
            extentReports.attachReporter(spark);

        }
    }
}
