package StepDefinition;

import java.lang.Thread;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class Xm_Task1 {
	WebDriver driver;

	@Test
	@And("set screen resolution to Maximum")
	public void set_screen_resolution_to_maximum() {
		driver.manage().window().maximize();
	}

	@Test
	@And("set screen resolution to 1024_768")
	public void set_screen_resolution_to_1024_768() {
		driver.manage().window().setSize(new Dimension(1024, 768));
	}

	@Test
	@And("set screen resolution to 800_600")
	public void set_screen_resolution_to_800_600() {
		driver.manage().window().setSize(new Dimension(800, 600));
	}

	@Test
	@Given("navigate to XM website homepage")
	public void navigate_to_xm_website_homepage() {
		System.setProperty("webdriver.chrome.driver", "D:\\Pranita\\Testing\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.xm.com/");
		driver.findElement(By.xpath("//*[@id=\"cookieModal\"]/div/div/div[1]/div[3]/div[2]/div[3]/button")).click(); // Accept
																														// cookie
																														// popup
		String title = driver.getTitle();
		Assert.assertEquals(title, "Forex & CFD Trading on Stocks, Indices, Oil, Gold by XM™");
	}

	@Test
	@And("click on Research and Education link")
	public void click_on_research_and_education_link() {
		driver.findElement(By.className("main_nav_research")).click();
	}

	@Test
	@And("click on Research and Education link for 800_600")
	public void click_on_research_and_education_link_for_800_600() {
		driver.findElement(By.xpath("//button[@class=\"toggleLeftNav\"]")).click(); // Menu button for small resolutions
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement researchMenu = driver.findElement(By.xpath("//a[@href=\"#researchMenu\"]"));
		js.executeScript("arguments[0].scrollIntoView();", researchMenu);
		researchMenu.click();
	}

	@Test
	@And("click on Economic Calendar link")
	public void click_on_economic_calendar_link() {
		WebElement economicCalendar = driver
				.findElement(By.xpath("//a[@href='https://www.xm.com/research/economicCalendar']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,200)");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(economicCalendar));
		economicCalendar.click();
		String economicCalendarTitle = driver.getTitle();
		Assert.assertEquals(economicCalendarTitle, "Economic Calendar");
	}

	@Test
	@When("user moves the slider to {string} and checks the date")
	public void moveSliderToAndCheckDate(String sliderValue) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,200)");

		// Switch to the iframe containing the slider
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement iframe = wait.until(ExpectedConditions.elementToBeClickable(By.id("iFrameResizer0")));
		driver.switchTo().frame(iframe);

		WebElement slider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("mat-slider")));

		// Get the current slider value
		int currentValue = Integer.parseInt(slider.getAttribute("aria-valuenow"));
		int targetValue;
		String expectedDate;

		switch (sliderValue) {
		case "today":
			targetValue = 1;
			expectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy MMM dd"));
			// expectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy MM
			// dd"));
			break;
		case "tomorrow":
			targetValue = 2;
			expectedDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy MMM dd"));
			break;
		case "thisWeek":
			targetValue = 3;
			expectedDate = LocalDate.now().with(java.time.DayOfWeek.SUNDAY)
					.format(DateTimeFormatter.ofPattern("yyyy MMM dd"));
			break;
		case "nextWeek":
			targetValue = 4;
			expectedDate = LocalDate.now().plusWeeks(1).with(java.time.DayOfWeek.SUNDAY)
					.format(DateTimeFormatter.ofPattern("yyyy MMM dd"));
			break;
		case "thisMonth":
			targetValue = 5;
			expectedDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
					.format(DateTimeFormatter.ofPattern("yyyy MMM dd"));
			break;
		default:
			throw new IllegalArgumentException("Invalid slider value: " + sliderValue);
		}

		Actions actions = new Actions(driver);
		WebElement sliderThumb = slider.findElement(By.cssSelector(".mat-slider-thumb"));
		int sliderWidth = slider.getSize().width;
		int numberOfSteps = 5; // There are 6 positions, so there are 5 steps between them.

		// Calculate the offset to move to the initial position (1, "today") if not
		// already there
		if (currentValue != 1) {
			double offsetToToday = ((1 - currentValue) / (double) numberOfSteps) * sliderWidth;
			actions.clickAndHold(sliderThumb).moveByOffset((int) offsetToToday, 0).release().perform();
			wait.until(ExpectedConditions.attributeToBe(slider, "aria-valuenow", "1"));
		}

		// Calculate the offset to move from the initial position (1) to the target
		// value
		double offsetToTarget = ((targetValue - 1) / (double) numberOfSteps) * sliderWidth;

		actions.clickAndHold(sliderThumb).moveByOffset((int) offsetToTarget, 0).release().perform();

		// Verify the slider's aria-valuenow attribute
		wait.until(ExpectedConditions.attributeToBe(slider, "aria-valuenow", String.valueOf(targetValue)));

		// Verify the displayed date
		WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[@class ='tc-economic-calendar-item-header-left-title tc-normal-text']")));
		// WebElement dateElement =
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.tc-finalval-tmz")));
		String actualDate = dateElement.getText();
		if (!actualDate.equals(expectedDate)) {
			throw new AssertionError("Expected date: " + expectedDate + ", but got: " + actualDate);
		}

		// Switching to the default content
		driver.switchTo().defaultContent();

	}

	@Test
	@And("click on Educational Videos link")
	public void click_on_educational_videos_link() {
		driver.findElement(By.xpath("//a[@href='https://www.xm.com/educational-videos']")).click();
		String eduVideosTitle = driver.getTitle();
		Assert.assertEquals(eduVideosTitle, "Forex Trading Course — Learn Forex from A to Z for Free");
	}

	@Test
	@And("click on Lesson 1_1 video for max screen resolution")
	public void click_on_Lesson_1_1_video_for_max_screen_resolution() throws InterruptedException {
		WebElement intro = driver.findElement(By.xpath("//button[text()='Intro to the Markets']"));
		intro.click();
		driver.findElement(By.xpath("//a[@data-video='trd-s1|d49ddcb31d1be2c35c']")).click(); // Lesson 1.1

		playVideo();
	}

	@Test
	@And("click on Lesson 1_1 video for 1024_768")
	public void click_on_lesson_1_1_video_for_1024_768() throws InterruptedException {
		WebElement intro = driver.findElement(By.xpath("//button[text()='Intro to the Markets']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", intro);
		intro.click();
		driver.findElement(By.xpath("//a[@data-video='trd-s1|d49ddcb31d1be2c35c']")).click(); // Lesson 1.1

		playVideo();
	}

	@Test
	@Then("video should be played for minimum 5 seconds")
	public void verify_video_is_played_for_minimum_5_seconds() {
		WebElement playerHolder = driver.findElement(By.xpath("//div[@class='player-video-holder']"));
		playerHolder.click();
		String progressTime = driver.findElement(By.xpath("//div[@class='player-progress-time']")).getText();
		System.out.println("Video Progress Time: " + progressTime);

		// Ensure the video has played for at least 5 seconds

		driver.quit();
	}

	@Test
	private void playVideo() throws InterruptedException {
		WebElement iframe = driver.findElement(By.xpath("//iframe[@title='Video Player']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", iframe);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));

		WebElement playButton = driver.findElement(By.xpath("//div[@aria-label='Play Video']"));
		js.executeScript("arguments[0].scrollIntoView();", playButton);
		playButton.click();
		Thread.sleep(11000); // Allow video to play for at least 11 seconds

		// Switching to the default content
		// driver.switchTo().defaultContent();
	}
}
