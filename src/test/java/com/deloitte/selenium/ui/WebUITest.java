package com.deloitte.selenium.ui;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "server.port=9000", webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebUITest {

    private WebDriver driver;
    String host = "http://localhost:9000";
    String baseUrl = "/";

    @Before
    public void beforeTest(){
        System.out.println("Launching Chrome browser");
        driver = new ChromeDriver();
        driver.get(host + baseUrl);
    }

    @After
    public void afterTest(){
        driver.quit();
    }

    @Test
    public void verifyHomePageTitle(){
        System.out.println(driver.getTitle());
        Assert.assertEquals( "Deloitte DevOps Application", driver.getTitle());
    }

    @Test
    public void verifyTeamMemberListPageLayout(){
        //Go to Team Member List page by clicking on link
        driver.findElement(By.linkText("Team Members List")).click();

        // Ensure existence of text tags on the List page
        Assert.assertEquals("Add New TeamMembers", driver.findElement(By.partialLinkText("Add New")).getText() );
        Assert.assertEquals("Team Member Name" , driver.findElements(By.tagName("th")).get(0).getText());
        Assert.assertEquals("Team Member Role", driver.findElements(By.tagName("th")).get(1).getText() );
    }

    @Test
    public void verifyTeamMemberAddPageLayout(){
        //Go to Team Member List page by clicking on link
        driver.findElement(By.linkText("Team Members List")).click();
        //Go to Team Member Add page by clicking on link
        driver.findElement(By.linkText("Add New TeamMembers")).click();

        // Ensure existence of text tags on the Add page
        Assert.assertEquals("Team Member Name", driver.findElements(By.tagName("th")).get(0).getText());
        Assert.assertEquals("Team Member Role", driver.findElements(By.tagName("th")).get(1).getText() );
        Assert.assertEquals("Save", driver.findElement(By.tagName("button")).getText());
    }

}
