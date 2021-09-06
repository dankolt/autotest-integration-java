package ru.testit.SimplePack;

import org.junit.jupiter.api.*;
import ru.testit.annotations.*;
import ru.testit.annotations.DisplayName;
import ru.testit.services.LinkItem;
import ru.testit.services.TestITClient;

public class SimpleTest {

    @BeforeAll
    @Title("Before")
    @Description("Before")
    public static void init() {
        doBeforeSt();

    }

    @BeforeEach
    @Title("Before")
    @Description("BeforeDesc")
    public void setUp() {
        doBefore();
    }

    @AfterAll
    @Title("AfterClass")
    @Description("AfterClassDesc")
    public static void finish() {
        doAfterSt();
    }

    @AfterEach
    @Title("After")
    @Description("AfterDesc")
    public void tearDown() {
        doAfter();
    }

    @ExternalId("DemoN1111")
    @Test
    @WorkItemId("100")
    @DisplayName("Demo")
    @Title("2")
    @Description("Description")
    @Links(links = {@Link(url = "www.1.ru", title = "firstLink", description = "firstLinkDesc", type = LinkType.RELATED),
            @Link(url = "www.3.ru", title = "thirdLink", description = "thirdLinkDesc", type = LinkType.ISSUE),
            @Link(url = "www.2.ru", title = "secondLink", description = "secondLinkDesc", type = LinkType.BLOCKED_BY)})
    public void itsTrueReallyTrue() {
        doSomething();
        Assertions.assertTrue(false);
    }

    @Step
    @Title("doSomething")
    @Description("doSomethingDesc")
    private void doSomething() {
        doThird();
        Assertions.assertTrue(true);
    }

    @Step
    @Title("doSecond")
    @Description("doSecondDesc")
    private void doSecond() {
        TestITClient.addLink(new LinkItem("doSecondLink", "www.test.com", "testDesc", LinkType.RELATED));
        doThird();
    }

    @Step
    @Title("doThird")
    @Description("doThirdDesc")
    private void doThird() {
        int x = 2 + 5 + 6;
    }

    @ExternalId("DemoN1222")
    @Test
    @WorkItemId("102")
    @DisplayName("'false' equals 'false'")
    @Title("Second")
    @Description("Description")
    public void itsFalseReallyFalse() {
        TestITClient.addLink(new LinkItem("test", "www.test.com", "testDesc", LinkType.DEFECT));
        doSecond();
        Assertions.assertTrue(true);
    }

    @Step
    @Title("doBeforeSt")
    @Description("doBeforeStDesc")
    private static void doBeforeSt() {
        int x = 2 + 5 + 6;
    }

    @Step
    @Title("doBefore")
    @Description("doBeforeDesc")
    private void doBefore() {
    }

    @Step
    @Title("do")
    @Description("do")
    private static void doAfterSt() {
        int x = 2 + 5 + 6;

    }

    @Step
    @Title("do")
    @Description("do")
    private void doAfter() {
        int x = 2 + 5 + 6;
    }
}