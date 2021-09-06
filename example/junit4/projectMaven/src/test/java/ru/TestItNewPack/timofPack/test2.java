package ru.TestItNewPack.timofPack;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.testit.annotations.*;
import ru.testit.services.LinkItem;
import ru.testit.services.TestITClient;

public class test2 {


        @BeforeClass
        @Title("TitleBeforeClass")
        @Description("DescriptionBeforeClass")
        public static void init() {
            doBeforeSt();
        }

        @Before
        @Title("TitleBefore")
        @Description("DescriptionBefore")
        public void setUp() {
            doBefore();
        }

        @ExternalId("14")
        @Test
        @WorkItemId("")
        @DisplayName("new TestMavenLib")
        @Title("new TestMavenLib")
        @Description("N3")
        @Links(links = {@Link(url = "www.1.ru", title = "NEWTitle", description = "firstLinkDesc", type = LinkType.RELATED),
                @Link(url = "www.2.ru", title = "NEWSecondTitle", description = "secondLinkDesc", type = LinkType.DEFECT),
                @Link(url = "www.3.ru", title = "3", description = "secondLinkDesc", type = LinkType.ISSUE)})
        @Labels({"test", "newTag"})
        public void itsTrueReallyTrue() {
            doSecond();
                doSecond();
                doSecond();
            Assert.assertTrue(false);
        }

        @Step
        @Title("doSomething")
        @Description("NewDoSomethingDesc")
        private void doSomething() {
            doSecond();
            doThird();
        }

        @Step
        @Title("doSecond")
        @Description("doSecondDesc")
        private void doSecond() {
            TestITClient.addLink(new LinkItem("NewDoSecondLink", "www.test.com", "second test", LinkType.RELATED));
        }

        @Step
        @Title("doThird")
        @Description("doThirdDesc")
        private void doThird() {
            int x = 2 + 5 + 6;
        }

        @ExternalId("12")
        @Test
        @WorkItemId("")
        @DisplayName("'false' equals 'false' awesome!")
        @Title("NewNewSecond Test")
        @Description("Second Test Description")
        public void itsFalseReallyFalse() {
            TestITClient.addLink(new LinkItem("test", "www.test.com", "testDesc", LinkType.RELATED));
            doSecond();
            Assert.assertFalse(false);
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
        @Title("NewTitleAfter")
        @Description("NewDescriptionAfter")
        public void tearDown() {

                doAfter();
        }

        @AfterClass
        @Title("NewTitleAfterClass")
        @Description("NewDescriptionAfterClass")
        public static void finish() {

                doAfterSt();
        }

}
