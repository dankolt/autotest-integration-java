package ru.testit.utils;

import java.lang.reflect.*;
import ru.testit.services.*;
import ru.testit.junit5.*;
import ru.testit.model.request.*;
import ru.testit.annotations.*;
import java.util.*;

public class CreateTestItemRequestFactory
{
    private Map<String, CreateTestItemRequest> createTestItemRequests;
    
    public CreateTestItemRequestFactory() {
        this.createTestItemRequests = new HashMap<String, CreateTestItemRequest>();
    }
    
    public void processTest(final Method method) {
        final CreateTestItemRequest createTestItemRequest = new CreateTestItemRequest();
        final String externalId = this.extractExternalID(method);
        createTestItemRequest.setExternalId(externalId);
        createTestItemRequest.setProjectId(TestITClient.getProjectID());
        createTestItemRequest.setName(this.extractDisplayName(method));
        createTestItemRequest.setClassName(method.getDeclaringClass().getSimpleName());
        createTestItemRequest.setNameSpace((method.getDeclaringClass().getPackage() == null) ? null : method.getDeclaringClass().getPackage().getName());
        createTestItemRequest.setTestPlanId(this.extractTestPlanId(method));
        createTestItemRequest.setLinks(this.extractLinks(method));
        createTestItemRequest.setLabels(this.extractLabels(method));
        this.createTestItemRequests.put(externalId, createTestItemRequest);
    }
    
    public void processFinishLaunch(final HashMap<MethodType, StepNode> utilsMethodSteps, final HashMap<String, StepNode> includedTests) {
        for (final String externalId : this.createTestItemRequests.keySet()) {
            final CreateTestItemRequest createTestItemRequest = this.createTestItemRequests.get(externalId);
            final StepNode testParentStepNode = includedTests.get(externalId);
            createTestItemRequest.setOutcome(Outcome.getByValue(testParentStepNode.getOutcome()));
            this.processTestSteps(createTestItemRequest, testParentStepNode);
            this.processUtilsSteps(createTestItemRequest, utilsMethodSteps);
        }
    }
    
    private void processTestSteps(final CreateTestItemRequest createTestItemRequest, final StepNode parentStep) {
        createTestItemRequest.setTitle(parentStep.getTitle());
        createTestItemRequest.setDescription(parentStep.getDescription());
        this.processStep(parentStep.getChildrens(), createTestItemRequest.getSteps());
    }
    
    private void processUtilsSteps(final CreateTestItemRequest createTestItemRequest, final HashMap<MethodType, StepNode> utilsMethodSteps) {
        for (final MethodType methodType : utilsMethodSteps.keySet()) {
            if (methodType == MethodType.BEFORE_CLASS || methodType == MethodType.BEFORE_METHOD) {
                this.processSetUpSteps(createTestItemRequest, utilsMethodSteps.get(methodType));
            }
            else {
                this.processTearDownSteps(createTestItemRequest, utilsMethodSteps.get(methodType));
            }
        }
    }
    
    private void processSetUpSteps(final CreateTestItemRequest createTestItemRequest, final StepNode stepNode) {
        final InnerItem setUp = new InnerItem();
        setUp.setTitle(stepNode.getTitle());
        setUp.setDescription(stepNode.getDescription());
        this.processStep(stepNode.getChildrens(), setUp.getSteps());
        createTestItemRequest.getSetUp().add(setUp);
    }
    
    private void processTearDownSteps(final CreateTestItemRequest createTestItemRequest, final StepNode stepNode) {
        final InnerItem tearDown = new InnerItem();
        tearDown.setTitle(stepNode.getTitle());
        tearDown.setDescription(stepNode.getDescription());
        this.processStep(stepNode.getChildrens(), tearDown.getSteps());
        createTestItemRequest.getTearDown().add(tearDown);
    }
    
    private void processStep(final List<StepNode> childrens, final List<InnerItem> steps) {
        for (final StepNode children : childrens) {
            final InnerItem step = new InnerItem();
            step.setTitle(children.getTitle());
            step.setDescription(children.getDescription());
            steps.add(step);
            if (!children.getChildrens().isEmpty()) {
                this.processStep(children.getChildrens(), step.getSteps());
            }
        }
    }
    
    private String extractExternalID(final Method atomicTest) {
        final ExternalId annotation = atomicTest.getAnnotation(ExternalId.class);
        return (annotation != null) ? annotation.value() : null;
    }
    
    private String extractDisplayName(final Method atomicTest) {
        final DisplayName annotation = atomicTest.getAnnotation(DisplayName.class);
        return (annotation != null) ? annotation.value() : null;
    }
    
    private String extractTestPlanId(final Method method) {
        final WorkItemId annotation = method.getAnnotation(WorkItemId.class);
        return (annotation != null) ? annotation.value() : null;
    }
    
    private List<InnerLink> extractLinks(final Method method) {
        final List<InnerLink> links = new LinkedList<InnerLink>();
        final Links linksAnnotation = method.getAnnotation(Links.class);
        if (linksAnnotation != null) {
            for (final Link link : linksAnnotation.links()) {
                links.add(this.makeInnerLink(link));
            }
        }
        else {
            final Link linkAnnotation = method.getAnnotation(Link.class);
            if (linkAnnotation != null) {
                links.add(this.makeInnerLink(linkAnnotation));
            }
        }
        return links;
    }
    
    private InnerLink makeInnerLink(final Link linkAnnotation) {
        final InnerLink innerLink = new InnerLink();
        innerLink.setTitle(linkAnnotation.title());
        innerLink.setDescription(linkAnnotation.description());
        innerLink.setUrl(linkAnnotation.url());
        innerLink.setType(linkAnnotation.type().getValue());
        return innerLink;
    }
    
    private List<Label> extractLabels(final Method method) {
        final List<Label> labels = new LinkedList<Label>();
        final Labels annotation = method.getAnnotation(Labels.class);
        if (annotation != null) {
            for (final String s : annotation.value()) {
                final Label label = new Label();
                label.setName(s);
                labels.add(label);
            }
        }
        return labels;
    }
    
    public Collection<CreateTestItemRequest> getCreateTestRequests() {
        return this.createTestItemRequests.values();
    }
}
