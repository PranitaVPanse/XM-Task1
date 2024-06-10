Feature: XM Website Screen resolution

  Scenario Outline: Verify xm website with screen resolution as Maximum
    Given navigate to XM website homepage
    And set screen resolution to Maximum
    And click on Research and Education link
    And click on Economic Calendar link
    When user moves the slider to "today" and checks the date
    And user moves the slider to "tomorrow" and checks the date
    And click on Research and Education link
    And click on Educational Videos link
    And click on Lesson 1_1 video for max screen resolution
    Then video should be played for minimum 5 seconds

  Scenario Outline: Verify xm website with screen resolution as 1024_768
    Given navigate to xm website homepage
    And set screen resolution to 1024_768
    And click on Research and Education link
    And click on Economic Calendar link
    When user moves the slider to "today" and checks the date
    And user moves the slider to "tomorrow" and checks the date
    And click on Research and Education link
    And click on Educational Videos link
    And click on Lesson 1_1 video for 1024_768
    Then video should be played for minimum 5 seconds

  Scenario Outline: Verify xm website with screen resolution as 800_600
    Given navigate to xm website homepage
    And set screen resolution to 800_600
    And click on Research and Education link
    And click on Economic Calendar link
    When user moves the slider to "today" and checks the date
    And user moves the slider to "tomorrow" and checks the date
    And click on Research and Education link
    And click on Educational Videos link
    And click on Lesson 1_1 Introduction to the Financial Markets video
    Then video should played for minimum 5seconds
