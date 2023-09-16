package utilities;

import java.io.File;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	
	private static ExtentReports extent;
	
	
	public static ExtentReports getInstance(){
		
		if(extent==null){
			
		
			extent = new ExtentReports(System.getProperty("user.dir")+"\\target\\extentreports\\extent.html",true,DisplayOrder.OLDEST_FIRST);
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\src\\main\\java\\utilities\\ReportsConfig.xml"));
			
		}
		
		return extent;
		
	}

}

