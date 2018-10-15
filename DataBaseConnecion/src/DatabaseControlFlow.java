import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DatabaseControlFlow {

	WebDriver driver;
	String un, pwd;
	Connection con = null;
	ResultSet res = null;
	Statement stmt = null;

	@BeforeClass
	void beforeClass() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE", "system", "AKASH");
			String s = "SELECT * FROM TEST";
			stmt = con.createStatement();
			res = stmt.executeQuery(s);
			while (res.next()) {
				un = res.getString(1);
				pwd = res.getString(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	void beforeMethod() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	void posCredentials1() {
		String url = "http://www.magento.com";
		driver.get(url);
		driver.findElement(By.linkText("My Account")).click();
		driver.findElement(By.id("email")).sendKeys(un);
		driver.findElement(By.id("pass")).sendKeys(pwd);
		driver.findElement(By.id("send2")).click();
		driver.findElement(By.linkText("Log Out")).click();
	}

	@AfterMethod
	void afterMethod() {
		driver.quit();
	}

	@AfterClass
	void afterClass() {
		try {
			con.close();
			stmt.close();
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
