package ru.Steps;

import ru.testit.annotations.Description;
import ru.testit.annotations.LinkType;
import ru.testit.annotations.Step;
import ru.testit.annotations.Title;
import ru.testit.services.LinkItem;
import ru.testit.services.TestITClient;

public class SimplSteps {

    private String loginFieldTag = "app-form-field-label app-control-container app-input";
    private String loginPasswordTag = "app-control-container app-padded-container app-input";
    private String loginButtonTag = "app-button-action button";
    private String checkboxRememberMeTag = "app-checkbox input";


    @Step
    @Title("Enter user login")
    @Description("The login field is filled in correctly")
    private void Step_01() {
        TestITClient.addLink(new LinkItem("If you see this, then the autotest was successful", "www.Great.com", "You're a hottie", LinkType.RELATED));
    }

    @Step
    @Title("Enter user password")
    @Description("Entered password is displayed by *-key")
    private void Step_02() {
        int x = 2 + 5 + 6;
    }

    @Step
    @Title("Go to the Shopping History module")
    @Description("Shopping History module is open")
    private void Step_1() {
        int x = 2 + 5 + 6;
    }

    @Step
    @Title("Select the number of users")
    @Description("The field shows the selected number of users. The license price has been changed according to the selected number of users.")
    private void Step_2() {
        int x = 2 + 5 + 6;
    }

    @Step
    @Title("Select a license commencement date")
    @Description("The selected date is displayed in the required field")
    private void Step_3() {
        int x = 2 + 5 + 6;
    }

    @Step
    @Title("Set a checkbox to accept the license agreement")
    @Description("The check box was selected")
    private void Step_4() {
        int x = 2 + 5 + 6;
    }

    @Step
    @Title("Click on Buy button")
    @Description("A dialog window appeared with a message about successful license purchase.")
    private void Step_5() {
        int x = 2 + 5 + 6;
    }

    @Step
    @Title("Close the dialog box on a cross OR on the button Clearly")
    @Description("The purchasing history table was updated. Added a line with information about the license you are purchasing. Status - pending payment")
    private void Step_6() {
        int x = 2 + 5 + 6;
    }

    @Step
    @Title("Go to AMO CRM")
    @Description("In the Issue invoice column a transaction for the amount of the requested license appeared")
    private void Step_7() {
        int x = 2 + 5 + 6;
    }

    @Step
    @Title("In CRM drag a deal to the 'Not implemented' column and go to the site in the history of purchases")
    @Description("The line with information about the license being purchased has been updated. Status - Cancelled")
    private void Step_8() {
        int x = 2 + 5 + 6;
    }
}
