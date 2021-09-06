package ru.TestItNewPack.timofPack;

import org.junit.*;
import ru.testit.annotations.*;
import ru.testit.services.LinkItem;
import ru.testit.services.TestITClient;

public class newTestLibClass {


        @BeforeClass
        @Title("TitleBeforeClassNEW")
        @Description("DescriptionBeforeClassNEW")
        public static void init() {
            doBeforeSt();
        }

        @Before
        @Title("TitleBeforeNEW")
        @Description("DescriptionBeforeNEW")
        public void setUp() {
            doBefore();
        }

        @ExternalId("8")
        @Test
        @WorkItemId("29")
        @DisplayName("itsTrueReallyTrue")
        @Title("1241241343")
        @Description("N3")
        @Links(links = {@Link(url = "www.1.ru", title = "NEWTitle", description = "firstLinkDesc", type = LinkType.RELATED),
                @Link(url = "www.2.ru", title = "NEWSecondTitle", description = "secondLinkDesc", type = LinkType.DEFECT),
                @Link(url = "www.2.ru", title = "NEWSecondTitle", description = "secondLinkDesc", type = LinkType.REQUIREMENT),
                @Link(url = "www.3.ru", title = "3", description = "secondLinkDesc", type = LinkType.ISSUE)})
        @Labels({"itsRealyNewLable"})
        public void itsTrueReallyTrue() {
            doSecond();
            Assert.assertTrue(true);
        }

        @Step
        @Title("doSomethingNEW")
        @Description("NewDoSomethingDesc")
        private void doSomething() {
            doSecond();
            doThird();
        }

        @Step
        @Title("doSecond")
        @Description("doSecondDescNEW")
        private void doSecond() {
            TestITClient.addLink(new LinkItem("NewDoSecondLinkNEW", "www.test.com", "second test", LinkType.RELATED));
        }

        @Step
        @Title("doThird")
        @Description("doThirdDesc")
        private void doThird() {
            int x = 2 + 5 + 6;
        }

        @ExternalId("9")
        @Test
        @WorkItemId("30")
        @DisplayName("'false' equals 'false' awesome!")
        @Title("NewNewSecond Test")
        @Description("шарабадам")
        public void itsFalseReallyFalse() {
            TestITClient.addLink(new LinkItem("test", "www.test.com", "testDesc", LinkType.RELATED));
            doSecond();
            Assert.assertFalse(false);
        }

        @Step
        @Title("doBeforeSt")
        @Description("doBeforeStDescNEW")
        private static void doBeforeSt() {
            int x = 2 + 5 + 6;
        }

        @Step
        @Title("doBefore")
        @Description("doBeforeDescNEW")
        private void doBefore() {
        }

        @Step
        @Title("doAfterSt")
        @Description("doAfterStDesc")
        private static void doAfterSt() {
            int x = 2 + 5 + 6;
        }

        @Step
        @Title("doAfter")
        @Description("doAfterDesc")
        private void doAfter() {
            int x = 2 + 5 + 6;
        }

        @After
        @Title("NewTitleAfterNEW")
        @Description("NewDescriptionAfterNEW")
        public void tearDown() {

                doAfter();
        }

        @AfterClass
        @Title("NewTitleAfterClassNEW")
        @Description("NewDescriptionAfterClassNEW")
        public static void finish() {

                doAfterSt();
        }

}
