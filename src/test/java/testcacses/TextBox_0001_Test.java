package testcacses;

import java.util.Hashtable;

import org.testng.annotations.Test;

import base.BaseClass;
import pages.TextBox_0001_Page;
import utilities.Utility;

public class TextBox_0001_Test {
	
	TextBox_0001_Page tb = new TextBox_0001_Page();
	
	@Test(priority = 1, groups = {"smoke","regression"})
	public void textBox_Link_Click() throws Exception{
		tb.textbox_Link_Click();
	}
	
	@Test(priority = 2, groups = {"smoke","regression"}, dataProviderClass = Utility.class, dataProvider = "DemoQAdata")
	public void textBox_Fill_The_Form_And_Print(Hashtable<String, String> data) throws Exception{
		tb.textbox_Fill_The_Form_And_Print(data.get("fname"), data.get("email"), data.get("caddress"), data.get("paddress"));	
	}
	
	@Test(priority = 3, groups = {"smoke","regression"})
	public void teardown() {
		BaseClass.tear();
	}

}
