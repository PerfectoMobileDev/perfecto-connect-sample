package com.perfecto.connect.sample;

import com.perfecto.connect.sample.retry.Retry;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

public class PerfectoConnectWeb extends PerfectoConnectBase {


    @Test(groups = {"all", "web"})
    public void sample() throws IOException, InterruptedException, ExecutionException {
        runSeleniumTest("Windows", "10", "Chrome");
    }

    private void runSeleniumTest(String os, String osVersion, String browserName) throws MalformedURLException {
        RemoteWebDriver driver = null;
        String [] locations = {"US East", "EU Frankfurt", "AP Sydney"};
        for (String location: locations) {
            System.out.println("Running in " + location);
            try {
                driver = createSeleniumDriver(os, osVersion, browserName, location);
                String host = server.getHost();
                System.out.println("Navigate to " + host);
                driver.get(host);

                WebElement element = driver.findElement(By.xpath("/html/body/pre"));
                Assert.assertEquals(message, element.getText());
                driver.getScreenshotAs(OutputType.FILE);
                String reportURL = (String) driver.getCapabilities().getCapability("testGridReportUrl");
                System.out.println("Done, Report URL: " + reportURL);
            } finally {
                if (driver != null) {
                    driver.quit();
                }
            }
        }
    }
}
