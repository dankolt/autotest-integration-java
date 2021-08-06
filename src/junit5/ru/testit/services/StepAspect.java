package ru.testit.services;

import ru.testit.annotations.*;
import org.aspectj.lang.*;
import org.aspectj.lang.reflect.*;
import java.util.*;
import ru.testit.utils.*;
import org.aspectj.lang.annotation.*;

@Aspect
public class StepAspect
{
    private static final InheritableThreadLocal<StepNode> currentStep;
    private static final InheritableThreadLocal<StepNode> previousStep;
    
    @Pointcut("@annotation(step)")
    public void withStepAnnotation(final Step step) {
    }
    
    @Pointcut("execution(* *.*(..))")
    public void anyMethod() {
    }
    
    @Before("anyMethod() && withStepAnnotation(step)")
    public void startNestedStep(final JoinPoint joinPoint, final Step step) {
        final MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        final StepNode currStep = StepAspect.currentStep.get();
        final StepNode newStep = StepUtils.makeStepNode(signature, currStep);
        newStep.setStartedOn(new Date());
        currStep.getChildrens().add(newStep);
        StepAspect.currentStep.set(newStep);
    }
    
    @AfterReturning(value = "anyMethod() && withStepAnnotation(step)", argNames = "step")
    public void finishNestedStep(final Step step) {
        final StepNode currStep = StepAspect.currentStep.get();
        currStep.setCompletedOn(new Date());
        currStep.setOutcome(Outcome.PASSED.getValue());
        StepAspect.currentStep.set(currStep.getParent());
    }
    
    @AfterThrowing(value = "anyMethod() && withStepAnnotation(step)", throwing = "throwable", argNames = "step,throwable")
    public void failedNestedStep(final Step step, final Throwable throwable) {
        final StepNode currStep = StepAspect.currentStep.get();
        currStep.setCompletedOn(new Date());
        currStep.setOutcome(Outcome.FAILED.getValue());
        StepAspect.currentStep.set(currStep.getParent());
    }
    
    @Pointcut("@annotation(addLink)")
    public void withAddLinkAnnotation(final AddLink addLink) {
    }
    
    @Pointcut("args(linkItem)")
    public void hasLinkArg(final LinkItem linkItem) {
    }
    
    @Before(value = "withAddLinkAnnotation(addLink) && hasLinkArg(linkItem) && anyMethod()", argNames = "addLink, link")
    public void startAddLink(final AddLink addLink, final LinkItem linkItem) {
        final StepNode stepNode = StepAspect.currentStep.get();
        stepNode.getLinkItems().add(linkItem);
    }
    
    public static void setStepNodes(final StepNode parentNode) {
        StepAspect.previousStep.set(StepAspect.currentStep.get());
        StepAspect.currentStep.set(parentNode);
    }
    
    public static void returnStepNode() {
        StepAspect.currentStep.set(StepAspect.previousStep.get());
        StepAspect.previousStep.set(StepAspect.currentStep.get());
    }
    
    static {
        currentStep = new InheritableThreadLocal<StepNode>();
        previousStep = new InheritableThreadLocal<StepNode>();
    }
}
