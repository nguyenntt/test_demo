package pack.base;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	static ExtentReports extent;
    final static String filePath = "ExtentReport.html";
    
    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            extent = new ExtentReports(filePath, true);
        }
        
        return extent;
    }
}
