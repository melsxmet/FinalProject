package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import static utils.PageInitializer.initializePageObject;

public class CommonMethods {
WebDriver driver;
    public void openBrowserAndLaunchApplication(){
        switch (ConfigReader.read("browser")){
            case "Chrome":
                driver=new ChromeDriver();
                break;
            case "FireFox":
                driver=new FirefoxDriver();
                break;
            case "Edge":
                driver=new EdgeDriver();
                break;
            case "Safari":
                driver=new SafariDriver();
                break;
            default:
                System.out.println("Invalid browser name");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(ConfigReader.read("url"));
        initializePageObject();
    }

    public void closeBrowser(){
        if(driver!=null){
            driver.quit();
        }
    }

    public void sendText(String text, WebElement element){
        element.clear();
        element.sendKeys(text);
    }

    public void selectFromDropDown(WebElement dropDown, String visibleText){
        Select sel=new Select(dropDown);
        sel.selectByVisibleText(visibleText);
    }

    public void selectFromDropDown(String value,WebElement dropDown){
        Select sel=new Select(dropDown);
        sel.deselectByValue(value);
    }

    public void selectFromDropDown(WebElement dropDown, int index){
        Select sel=new Select(dropDown);
        sel.selectByIndex(index);
    }

    public WebDriverWait getWait(){
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
        return wait;
    }
    public void waitForElementToBeClickable(WebElement element){
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }
    public void click(WebElement element){
        waitForElementToBeClickable(element);
        element.click();
    }

    public JavascriptExecutor getJSExecuter(){
        JavascriptExecutor js=(JavascriptExecutor)  driver;
        return js;
    }

    public void jsClick(WebElement element){
        getJSExecuter().executeScript("aruments[0].click();",element);
    }

    public String getTimeStamp(String pattern){
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public byte[] takeScreenshot(String fileName){
        TakesScreenshot ts=(TakesScreenshot) driver;
        byte[] picByte=ts.getScreenshotAs(OutputType.BYTES);
        File sourceFile=ts.getScreenshotAs(OutputType.FILE);

        try{
            FileUtils.copyFile(sourceFile, new File(Constants.SCREENSHOT_FILEPATH+ fileName+" "+getTimeStamp("yyyy-MM-dd HH:mm:ss")+".png"));
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
        return picByte;
    }

}
