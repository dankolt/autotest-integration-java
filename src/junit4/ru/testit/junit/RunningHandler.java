package ru.testit.junit;

import com.nordstrom.automation.junit.*;
import org.junit.runners.model.*;
import java.util.*;
import ru.testit.services.*;
import ru.testit.utils.*;
import org.junit.internal.runners.model.*;
import ru.testit.annotations.*;

public class RunningHandler
{
    private TestITClient testITClient;
    private CreateTestItemRequestFactory createTestItemRequestFactory;
    private TestResultRequestFactory testResultRequestFactory;
    private LinkedHashMap<MethodType, StepNode> utilsMethodSteps;
    private HashMap<String, StepNode> includedTests;
    private List<String> alreadyFinished;
    
    public RunningHandler() {
        this.createTestItemRequestFactory = new CreateTestItemRequestFactory();
        this.testResultRequestFactory = new TestResultRequestFactory();
        this.utilsMethodSteps = new LinkedHashMap<MethodType, StepNode>();
        this.includedTests = new HashMap<String, StepNode>();
        this.alreadyFinished = new LinkedList<String>();
        this.testITClient = new TestITClient();
    }
    
    public void startLaunch() {
        this.testITClient.startLaunch();
    }
    
    public void finishLaunch() {
        this.createTestItemRequestFactory.processFinishLaunch(this.utilsMethodSteps, this.includedTests);
        this.testITClient.sendTestItems(this.createTestItemRequestFactory.getCreateTestRequests());
        this.testResultRequestFactory.processFinishLaunch(this.utilsMethodSteps, this.includedTests);
        this.testITClient.finishLaunch(this.testResultRequestFactory.getTestResultRequest());
    }
    
    public void startTest(final AtomicTest<FrameworkMethod> atomicTest) {
        this.createTestItemRequestFactory.processTest((FrameworkMethod)atomicTest.getIdentity());
        final StepNode parentStep = new StepNode();
        parentStep.setTitle(this.extractTitle((FrameworkMethod)atomicTest.getIdentity()));
        parentStep.setDescription(this.extractDescription((FrameworkMethod)atomicTest.getIdentity()));
        parentStep.setStartedOn(new Date());
        this.includedTests.put(this.extractExternalID((FrameworkMethod)atomicTest.getIdentity()), parentStep);
        StepAspect.setStepNodes(parentStep);
    }
    
    public void finishTest(final AtomicTest<FrameworkMethod> atomicTest, final Throwable thrown) {
        final String externalId = this.extractExternalID((FrameworkMethod)atomicTest.getIdentity());
        if (this.alreadyFinished.contains(externalId)) {
            return;
        }
        final StepNode parentStep = this.includedTests.get(externalId);
        if (parentStep != null) {
            parentStep.setOutcome((thrown == null) ? Outcome.PASSED.getValue() : Outcome.FAILED.getValue());
            parentStep.setFailureReason(thrown);
            parentStep.setCompletedOn(new Date());
        }
        this.alreadyFinished.add(externalId);
    }
    
    public void startUtilMethod(final Object runner, final FrameworkMethod method, final ReflectiveCallable callable) {
        final MethodType currentMethod = MethodType.detect(method);
        if (currentMethod == null || currentMethod == MethodType.TEST) {
            return;
        }
        final StepNode parentStep = new StepNode();
        parentStep.setTitle(this.extractTitle(method));
        parentStep.setDescription(this.extractDescription(method));
        parentStep.setStartedOn(new Date());
        this.utilsMethodSteps.putIfAbsent(currentMethod, parentStep);
        StepAspect.setStepNodes(parentStep);
    }
    
    public void finishUtilMethod(final FrameworkMethod method, final Throwable thrown) {
        final MethodType currentMethod = MethodType.detect(method);
        if (currentMethod == null || currentMethod == MethodType.TEST) {
            return;
        }
        final StepNode parentStep = this.utilsMethodSteps.get(currentMethod);
        parentStep.setOutcome((thrown == null) ? Outcome.PASSED.getValue() : Outcome.FAILED.getValue());
        parentStep.setCompletedOn(new Date());
        if (currentMethod == MethodType.BEFORE_METHOD) {
            StepAspect.returnStepNode();
        }
    }
    
    private String extractDescription(final FrameworkMethod atomicTest) {
        final Description annotation = (Description)atomicTest.getAnnotation((Class)Description.class);
        return (annotation != null) ? annotation.value() : null;
    }
    
    private String extractTitle(final FrameworkMethod atomicTest) {
        final Title annotation = (Title)atomicTest.getAnnotation((Class)Title.class);
        return (annotation != null) ? annotation.value() : null;
    }
    
    private String extractExternalID(final FrameworkMethod atomicTest) {
        final ExternalId annotation = (ExternalId)atomicTest.getAnnotation((Class)ExternalId.class);
        return (annotation != null) ? annotation.value() : null;
    }
    
    public boolean isJunitMethod(final FrameworkMethod method) {
        return MethodType.detect(method) != null;
    }
}
