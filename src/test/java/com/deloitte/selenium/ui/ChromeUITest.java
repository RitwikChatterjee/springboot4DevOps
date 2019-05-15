package com.deloitte.selenium.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ChromeUITest {
    private WebDriver driver;
    String host = "http://10.0.57.70:8080";
    String baseUrl = "/devops-demo";

    @BeforeTest
    public void beforeTest(){
        System.out.println("Launching Chrome browser");
        driver = new ChromeDriver();
        driver.get(host + baseUrl);
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
    }

    @Test
    public void verifyHomePageTitle(){
        Assert.assertEquals(driver.getTitle(), "Deloitte DevOps Application");
    }

    @Test(priority = 1)
    public void verifyTeamMemberListPageLayout(){
        //Go to Team Member List page by clicking on link
        driver.findElement(By.linkText("Team Members List")).click();

        // Ensure existence of text tags on the List page
        Assert.assertEquals(driver.findElement(By.partialLinkText("Add New")).getText(), "Add New TeamMembers");
        Assert.assertEquals(driver.findElements(By.tagName("th")).get(0).getText(),"Team Member Name" );
        Assert.assertEquals(driver.findElements(By.tagName("th")).get(1).getText(),"Team Member Role" );
    }

    @Test(priority = 2)
    public void verifyTeamMemberAddPageLayout(){
        //Go to Team Member Add page by clicking on link
        driver.findElement(By.linkText("Add New TeamMembers")).click();

        // Ensure existence of text tags on the Add page
        Assert.assertEquals(driver.findElements(By.tagName("th")).get(0).getText(),"Team Member Name" );
        Assert.assertEquals(driver.findElements(By.tagName("th")).get(1).getText(),"Team Member Role" );
        Assert.assertEquals(driver.findElement(By.tagName("button")).getText(), "Save");
    }
}
