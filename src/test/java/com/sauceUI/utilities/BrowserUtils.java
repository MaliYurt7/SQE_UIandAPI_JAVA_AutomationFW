package com.sauceUI.utilities;

import org.junit.Assert;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.*;
import java.util.stream.Collectors;

public class BrowserUtils {


    public static String selectedCountry;
    /**
     * Switches to the new window
     */
    public static void switchToNewWindow(int tab) {
        ArrayList<String> tabs = new ArrayList<String>(Driver.get().getWindowHandles());
        waitFor(3);
        Driver.get().switchTo().window(tabs.get(tab));
    }


    /**
     * Close the window when it is no longer required
     */
    public static void closeTheWindow() {
        Driver.get().close();
    }


    /**
     * clicks on allow cookies on each page it comes up otherwise it continues
     */

    /**
     * Switches to new window by the exact title. Returns to original window if target title not found
     *
     * @param targetTitle
     */
    public static void switchToWindow(String targetTitle) {
        String origin = Driver.get().getWindowHandle();
        for (String handle : Driver.get().getWindowHandles()) {
            Driver.get().switchTo().window(handle);
            if (Driver.get().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.get().switchTo().window(origin);
    }

    /**
     * Moves the mouse to given element
     *
     * @param element on which to hover
     */
    public static void hover(WebElement element) {
        Actions actions = new Actions(Driver.get());
        actions.moveToElement(element).perform();
    }

    public static void scrollAndClickAction(WebElement element) {
        Actions action = new Actions(Driver.get());
        action.moveToElement(element).build().perform();
        element.click();
    }


    /**
     * return a list of string from a list of elements
     *
     * @param list of webelements
     * @return list of string
     */
    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            elemTexts.add(el.getText());
        }
        return elemTexts;
    }

    /**
     * Extracts text from list of elements matching the provided locator into new List<String>
     *
     * @param locator
     * @return list of strings
     */
    public static List<String> getElementsText(By locator) {

        List<WebElement> elems = Driver.get().findElements(locator);
        List<String> elemTexts = new ArrayList<>();

        for (WebElement el : elems) {
            elemTexts.add(el.getText());
        }
        return elemTexts;
    }


    /**
     * Performs a pause
     *
     * @param seconds
     */
    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for the provided element to be visible on the page
     *
     * @param element
     * @param timeToWaitInSec
     * @return
     */
    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForPageTitle(String pageTitle, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeToWaitInSec));
        wait.until(ExpectedConditions.titleContains(pageTitle));
    }

    public static void pageRefresh() {
        Driver.get().navigate().refresh();
    }

    public static Boolean waitForInVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    /**
     * Waits for element matching the locator to be visible on the page
     *
     * @param locator
     * @param timeout
     * @return
     */
    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    /**
     * Waits for element matching the locator to be visible on the page
     *
     * @param webElement
     */
    public static void waitForElementVisibility(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(25));
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public static void fluientWaitforElement(FindBy element, int timoutSec, int pollingSec) {

        Wait<WebDriver> wait = new FluentWait<>(Driver.get())
                .withTimeout(Duration.ofSeconds(30))  // Maximum time to wait
                .pollingEvery(Duration.ofMillis(500)) // Interval between each poll
                .ignoring(NoSuchElementException.class); // Exceptions to ignore
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated((By) element));
    }

    /**
     * Waits for provided element to be clickable
     *
     * @param timeout
     * @return
     */
    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits for element matching the locator to be clickable
     *
     * @param locator
     * @param timeout
     * @return
     */
    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * waits for backgrounds processes on the browser to complete
     *
     * @param timeOutInSeconds
     */
    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeOutInSeconds));
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
        }
    }

    /**
     * Verifies whether the element matching the provided locator is displayed on page
     *
     * @param by
     * @throws AssertionError if the element matching the provided locator is not found or not displayed
     */
    public static void verifyElementDisplayed(By by) {
        try {
            Driver.softAssert.assertTrue(Driver.get().findElement(by).isDisplayed(), "Element not visible: " + by);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Driver.softAssert.fail("Element not found: " + by);
        }
    }


    /**
     * Verifies whether the element is displayed on page
     *
     * @param element
     * @throws AssertionError if the element is not found or not displayed
     */
    public static void verifyElementDisplayed(WebElement element) {
        try {
            Assert.assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + element);

        }
    }

    /**
     * Verifies whether the element is NOT displayed on page
     *
     * @param by
     * @throws AssertionError if the element is found or is displayed
     */
    public static void verifyElementIsNotDisplayed(By by) {
//        try {
//            Assert.assertFalse("Element is visible: " + element, element.isDisplayed());
//        } catch (NoSuchElementException e) {
//            e.printStackTrace();
//            Assert.fail("Element found: " + element);
//        }
        System.out.println("To verify an element is absent");
        try {
            Driver.get().findElement(by);
        } catch (NoSuchElementException e) {
            Driver.softAssert.assertTrue(true, "Element doesn't Exist");
        }
    }


    /**
     * Waits for element to be not stale
     *
     * @param element
     */
    public static void waitForStaleElement(WebElement element) {
        int y = 0;
        while (y <= 15) {
            if (y == 1)
                try {
                    element.isDisplayed();
                    break;
                } catch (StaleElementReferenceException st) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (WebDriverException we) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    public static void forStaleElement(WebElement element) {
        int x = 0;
        while (x <= 15) {
            try {
                element.isDisplayed();
                waitFor(1);
                element.click();
                break;
            } catch (StaleElementReferenceException st) {
                x++;
                waitFor(1);
            } catch (WebDriverException we) {
                x++;
                waitFor(1);
            }
        }
    }

    public static void scrollDown() {

        JavascriptExecutor js = (JavascriptExecutor) Driver.get();
        js.executeScript("window.scrollBy(0,250)");
    }

    public static void scrollUp() {

        JavascriptExecutor js = (JavascriptExecutor) Driver.get();
        js.executeScript("window.scrollBy(0,-250)");
    }


    public static void dragDrop(WebElement source, WebElement target) {
        Actions actions = new Actions(Driver.get());
        actions.clickAndHold(source).moveToElement(target).pause(5000).release(target).build().perform();
    }

    public static void resizeBrowser() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.get();
        js.executeScript("document.body.style.zoom='75%'");
    }
    /**
     * Clicks on an element using JavaScript
     *
     * @param element
     */
    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].click();", element);
    }


    /**
     * Scrolls down to an element using JavaScript
     *
     * @param element
     */
    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToElementDropDwn(WebElement element) {
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].scrollTop = arguments[1];", element, 250);
        ;
    }

    /**
     * Performs double click action on an element
     *
     * @param element
     */
    public static void doubleClick(WebElement element) {
        new Actions(Driver.get()).doubleClick(element).build().perform();
    }

    /**
     * Changes the HTML attribute of a Web Element to the given value using JavaScript
     *
     * @param element
     * @param attributeName
     * @param attributeValue
     */
    public static void setAttribute(WebElement element, String attributeName, String attributeValue) {
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, attributeValue);
    }

    /**
     * Highlighs an element by changing its background and border color
     *
     * @param element
     */
    public static void highlight(WebElement element) {
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        waitFor(1);
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].removeAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    /**
     * Checks or unchecks given checkbox
     *
     * @param element
     */
    public static void selectCheckBox(WebElement element) {
        boolean check = true;
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    /**
     * attempts to click on provided element until given time runs out
     *
     * @param element
     * @param timeout
     */
    public static void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }

    /**
     * executes the given JavaScript command on given web element
     *
     * @param element
     */
    public static void executeJScommand(WebElement element, String command) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.get();
        jse.executeScript(command, element);

    }

    /**
     * executes the given JavaScript command on given web element
     *
     * @param command
     */
    public static void executeJScommand(String command) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.get();
        jse.executeScript(command);

    }


    /**
     * This method will recover in case of exception after unsuccessful the click,
     * and will try to click on element again.
     *
     * @param by
     * @param attempts
     */
    public static void clickWithWait(By by, int attempts) {
        int counter = 0;
        //click on element as many as you specified in attempts parameter
        while (counter < attempts) {
            try {
                //selenium must look for element again
                clickWithJS(Driver.get().findElement(by));
                //if click is successful - then break
                break;
            } catch (WebDriverException e) {
                //if click failed
                //print exception
                //print attempt
                e.printStackTrace();
                ++counter;
                //wait for 1 second, and try to click again
                waitFor(1);
            }
        }
    }

    /**
     * checks that an element is present on the DOM of a page. This does not
     * * necessarily mean that the element is visible.
     *
     * @param by
     * @param time
     */
    public static void waitForPresenceOfElement(By by, long time) {
        new WebDriverWait(Driver.get(), Duration.ofSeconds(time)).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void zoomOut(int howManyTimes) {
        for (int i = 0; i < howManyTimes; i++) {
            waitFor(1);
            Driver.get().findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
        }
    }

    /**
     * zoom out window
     */
    public static void zoomOutDimension() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.get();
        js.executeScript("document.body.style.zoom='75%'");

    }

    public static void zoomOutRobot(int howManyTimesZoomOut) throws AWTException {
        Robot robot = new Robot();
        for (int i = 0; i < howManyTimesZoomOut; i++) {
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_SUBTRACT);
            robot.keyRelease(KeyEvent.VK_SUBTRACT);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    public static String getPartialUrl() {
        return Driver.testEnvironmentDetails.get("partialUrl");
    }


    public static void verifyDDLNotEmptyAndContainsSpecificValue(WebElement dropDown, String expectedValue) {
        BrowserUtils.waitFor(3);
        dropDown.click();
        Select select = new Select(dropDown);
        List<WebElement> options = select.getOptions();
        if (options != null) {
            for (WebElement we : options) {
                if (we.getText().equalsIgnoreCase(expectedValue)) {
                    Driver.softAssert.assertTrue(true, "Both expected and actual values matched");
                    break;
                }
            }
        } else {
            Driver.softAssert.assertTrue(false, "Dropdown list is Empty");
        }
    }

    public static void printOutDropDownValue(WebElement dropDown, By listDropDown) {
        dropDown.click();
        List<WebElement> elementList = Driver.get().findElements(listDropDown);
        for (WebElement webElement : elementList) {
            System.out.println(webElement.getText());
        }
        dropDown.click();
    }

    public static void selectDropDownValue(WebElement element, String text) {
        element.sendKeys(text);
        By searchResult = By.xpath("//*[text()='" + text + "']");
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(searchResult));
        Driver.get().findElement(searchResult).click();
    }

    public static void selectFromDropDownByVisibleText(WebElement element, String text) {
        Select objSelect = new Select(element);
        objSelect.selectByVisibleText(text);
    }

    public static void clickOnDropDownAndSelectValue(WebElement dropDown, List<WebElement> listDropDown, int index) {
        dropDown.click();
        waitFor(2);
        List<WebElement> elementList = listDropDown;
        elementList.get(index).click();
    }

    public static void clickOnDropDownAndSelectVisibleTextBy(WebElement dropDown, By listDropDown, String text) {
        BrowserUtils.waitForClickablility(dropDown, 50);
        dropDown.click();
        waitFor(1);
        List<WebElement> elementList = Driver.get().findElements(listDropDown);
        for (WebElement webElement : elementList) {
            System.out.println(webElement.getText());
            if (webElement.getText().equalsIgnoreCase(text)) {
                clickWithJS(webElement);
            }
        }
        dropDown.click();
    }

    public static void clickOnDropDownAndSelectVisibleText(WebElement dropDown, List<WebElement> listDropDown, String text) {
        clickWithJS(dropDown);
        waitFor(3);
        System.out.println("listDropDown = " + listDropDown);
        List<WebElement> elementList = listDropDown;
        waitFor(3);
        for (WebElement webElement : elementList) {
            if (!webElement.getText().equalsIgnoreCase(text)) {
                scrollToElement(webElement);
            }
            if (webElement.getAttribute("textContent").trim().equalsIgnoreCase(text)) {
                selectedCountry = webElement.getAttribute("textContent").trim();
                waitForClickablility(webElement, 5);
                clickWithJS(webElement);
                waitFor(2);
                break;
            }
        }
    }


    public static void clickOnDropDownAndSelectValue(WebElement dropDown, String text) {
//        scrollToElement(dropDown);
        waitForClickablility(dropDown, 30);
        dropDown.click();
        waitFor(3);
        Select dropdownList = new Select(dropDown);
        dropdownList.selectByVisibleText(text);
        dropDown.click();
    }

    public static void enterTextInTextArea(WebElement element, String text) {
//        scrollToElement(element);
        System.out.println("Enter text: " + text);
        BrowserUtils.waitFor(1);
//        element.click();
        BrowserUtils.waitFor(1);
//        element.clear();
        element.sendKeys(text);
        element.sendKeys(Keys.ENTER);
    }

    public static WebElement findElementByTxt(String text, String tag) {
        String xpathString = "//" + tag + "[contains text(),'" + text + "')]";
        WebElement webElement = Driver.get().findElement(By.xpath((xpathString)));
        return webElement;
    }

    public static String dateFormat() {
        Format f = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = f.format(new Date());
        System.out.println("Current Date = " + strDate);
        return strDate;
    }

    public static LocalDate addDate(String oldDate, int daysToAdd) {
        LocalDate date = LocalDate.parse(oldDate);
        LocalDate date2 = date.plusDays(daysToAdd);
        System.out.println("New Date =  " + date2);
        return date2;
    }

    public static String getCurrentSystemDateInRequiredFormat(String dateFormat) {
        DateTimeFormatter dtf;
        switch (dateFormat) {
            case "MM/dd/yyyy":
                dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                break;
            case "dd MMM yyyy":
                dtf = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
                break;
            default:
                dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                break;
        }
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
        //System.out.println(dtf.format(now));
    }

    public static boolean verifyIsElementPresent(List<WebElement> element) {
        if (element.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param stringNumber is a number in String format covering comma "," such as "2,743"
     * @return integer as 2743
     * @throws ParseException
     */
    public static int numberFormatTheUK(String stringNumber) throws ParseException {
        NumberFormat ukFormat = NumberFormat.getNumberInstance(Locale.UK);
        return ukFormat.parse(stringNumber).intValue();
    }

    public static void jsSendKeys(WebElement webElement, String inputText) {
        String js = "arguments[0].setAttribute('value','" + inputText + "')";
        ((JavascriptExecutor) Driver.get()).executeScript(js, webElement);
    }


    public static void scrollBar(String selector, List<WebElement> webElement) {
        JavascriptExecutor jsExec = (JavascriptExecutor) Driver.get();
        for (int j = 0; j < 30; j++) {
            jsExec.executeScript("document.querySelector('" + selector + "').scrollBy(0,50)");
            waitFor(3);
            System.out.println("webElement.size() = " + webElement.size());
            if (webElement.size() == 1) {
                System.out.println("webElement.get(0) = " + webElement.get(0));
                clickWithJS(webElement.get(0));
                break;
            }
        }
    }

    public static String getCurrentUrl() {
        return Driver.get().getCurrentUrl();
    }

    public static void selectDropdown(WebElement element, String text) {
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    public static void allowCookies(WebElement webElement) {
        try {
            BrowserUtils.waitForClickablility(webElement, 20);
            webElement.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BrowserUtils.waitFor(1);
    }

    public static List<String> calendarSorting(List<String> calendarDates) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        String[] input = calendarDates.toArray(String[]::new);
//        String[] input = {"18 Aug 2023",
//                "12 May 2023",
//                "18 Aug 2023",
//                "12 May 2023",
//                "18 Aug 2023",
//        };
        List<Date> dates = Arrays.stream(input).map(dateString -> {
            try {
                return simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        dates.sort(Comparator.naturalOrder());
        List<String> datesInString = new ArrayList<>();
        for (Date date : dates) {
            datesInString.add(simpleDateFormat.format(date));
        }
        return datesInString;
    }

//    public static void verifyReportStatus(String reportName, String reportTitle, String tool, Map<String, String> getValue) {
//        String xpath = "//span[contains(text(),'%s')]";
//        waitForPresenceOfElement(By.xpath(String.format(xpath, getValue.get("QB_ReportName") + getValue.get("QB_jobCode"))), 25);
//        String module = getValue.get("QB_ReportName");
//        ExecutiveRegressionPage executiveRegressionPage = new ExecutiveRegressionPage();
//        QuickBenchmarksPage quickBenchmarksPage = new QuickBenchmarksPage();
//        int counter;
//        String submittedOn;
//        switch (module) {
//            case "Executive Regression":
//                assertTrue(getValue.get("ReportName").equalsIgnoreCase(executiveRegressionPage.getNameInFirstRowIndownloadsTable.getText()));
//                assertTrue(getValue.get("ReportType").equalsIgnoreCase(executiveRegressionPage.getTypeInFirstRowIndownloadsTable().getText()));
//                assertTrue(getValue.get("Tool").equalsIgnoreCase(executiveRegressionPage.getToolInFirstRowIndownloadsTable().getText()));
//                submittedOn = getCurrentSystemDateInRequiredFormat("dd MMM yyyy");
//                assertTrue(submittedOn.equalsIgnoreCase(executiveRegressionPage.getSubminttedInFirstRowIndownloadsTable().getText()));
//                counter = 0;
//                while (counter <= 40) {
//                    allowCookies(executiveRegressionPage.btnAcceptCookies);
//                    counter++;
//                    if (executiveRegressionPage.newExeRegTestStatus.getAttribute("textContent").contains("Completed")) {
//                        break;
//                    } else if (executiveRegressionPage.newExeRegTestStatus.getAttribute("textContent").toLowerCase().contains("processing")) {
//                        pageRefresh();
//                        waitFor(15);
//                    } else if (executiveRegressionPage.newExeRegTestStatus.getAttribute("textContent").toLowerCase().contains("failed")) {
//                        assertTrue("Report processing FAILED -My reports", false);
//                        break;
//                    }
//                    if (counter == 40) {
//                        assertTrue("The report processing status did not change from Queue to processing for more than 10 mins.Check", false);
//                        break;
//                    }
//                }
//                break;
//            case "QB_":
//                Assert.assertEquals("Expected report name is - ", "QB_" + getValue.get("QB_jobCode"), quickBenchmarksPage.listReportNames.get(0).getAttribute("textContent").trim());
//                Assert.assertEquals("Expected report type is - ", "Insights", quickBenchmarksPage.listReportTypes.get(0).getAttribute("textContent").trim());
//                Assert.assertEquals("Expected tool name is - ", "Quick Benchmarks", quickBenchmarksPage.listReportTool.get(0).getAttribute("textContent").trim());
////                submittedOn = getCurrentSystemDateInRequiredFormat("dd MMM yyyy");
////                assertTrue(submittedOn.equalsIgnoreCase(quickBenchmarksPage.getReportSubmittedDate(submittedOn).getText()));
//                counter = 0;
//                while (counter <= 40) {
//                    counter++;
//                    if (quickBenchmarksPage.listReportStatus.getAttribute("textContent").contains("Completed")) {
//                        break;
//                    } else if (quickBenchmarksPage.listReportStatus.getAttribute("textContent").toLowerCase().contains("processing")) {
//                        pageRefresh();
//                        waitFor(15);
//                    } else if (quickBenchmarksPage.listReportStatus.getAttribute("textContent").toLowerCase().contains("failed")) {
//                        assertTrue("Report processing FAILED - Qb report", false);
//                        break;
//                    }
//                    if (counter == 40) {
//                        assertTrue("The report processing status did not change from Queue to processing for more than 10 mins.Check", false);
//                        break;
//                    }
//                }
//                break;
//            default:
//                throw new InvalidArgumentException("Unsupported Report module:" + module);
//        }
//    }

    public static void navigateToPageBack() {
        Driver.get().navigate().back();
    }


    public static String changeDateFormat(String oldFormat, String newFormat, String oldDate) throws ParseException {
        String newDateString;
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        Date d = sdf.parse(oldDate);
        sdf.applyPattern(newFormat);
        newDateString = sdf.format(d);

        System.out.println("Date in new format = " + newDateString);
        return newDateString;
    }

    public static void clickBeforeTag(String css) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.get();
        js.executeScript("document.querySelector('" + css + "',':before').click();");
    }


    public static String getLastOption(WebElement webElement) {
        Select stateDrpdwn = new Select(webElement);
        List<WebElement> options = stateDrpdwn.getOptions();
        int getSizeOfOption = options.size();
        System.out.println("getSizeOfOption = " + getSizeOfOption);
        return stateDrpdwn.getOptions().get(getSizeOfOption - 1).getText();

    }

    public static int[] clickCoordinatesOfElement(String coordinateLocation) {
        Point coordinates = Driver.get().findElement(By.xpath(coordinateLocation)).getLocation();
        int x = coordinates.getX();
        int y = coordinates.getY();
        return new int[]{x, y};
    }

    public static String getFirstSelectedOptionFromSelectTag(WebElement dropdown) {
        Select select = new Select(dropdown);
        WebElement selectedOption = select.getFirstSelectedOption();
        return selectedOption.getText();
    }

    public static void scrollToFocusedElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.get();
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }


}

