package pages;

import org.testng.Assert;

import base.BaseClass;

public class TextBox_0001_Page extends BaseClass {
	
	
	public void textbox_Link_Click() throws Exception {
		clickJS("textboxbtn_xpath");
		Thread.sleep(1000);
	}
	
	public void textbox_Fill_The_Form_And_Print(String fname, String email, String cadd, String padd) throws Exception {
		
		clear("fullname_xpath");
		getElement("fullname_xpath").sendKeys(fname);
		clear("email_xpath");
		getElement("email_xpath").sendKeys(email);
		clear("caddress_xpath");
		getElement("caddress_xpath").sendKeys(cadd);
		clear("paddress_xpath");
		getElement("paddress_xpath").sendKeys(padd);
		
		clickJS("submit_xpath");
		Thread.sleep(1000);
		iteratorgetText1("printoutput_xpath");
		Thread.sleep(1000);
		Assert.assertFalse(true);
	}
	
	
}
