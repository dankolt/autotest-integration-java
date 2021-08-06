package ru.testit.junit;

import org.junit.runners.model.*;
import com.nordstrom.automation.junit.*;
import org.junit.internal.*;
import org.junit.internal.runners.model.*;

public class JUnitEventListener implements RunnerWatcher, RunWatcher<FrameworkMethod>, MethodWatcher<FrameworkMethod>
{
    private static final RunningHandler HANDLER;
    
    public void runStarted(final Object runner) {
        JUnitEventListener.HANDLER.startLaunch();
    }
    
    public void runFinished(final Object runner) {
        JUnitEventListener.HANDLER.finishLaunch();
    }
    
    public void testStarted(final AtomicTest<FrameworkMethod> atomicTest) {
        JUnitEventListener.HANDLER.startTest(atomicTest);
    }
    
    public void testFailure(final AtomicTest<FrameworkMethod> atomicTest, final Throwable thrown) {
        JUnitEventListener.HANDLER.finishTest(atomicTest, thrown);
    }
    
    public void testAssumptionFailure(final AtomicTest<FrameworkMethod> atomicTest, final AssumptionViolatedException thrown) {
        JUnitEventListener.HANDLER.finishTest(atomicTest, (Throwable)thrown);
    }
    
    public void testIgnored(final AtomicTest<FrameworkMethod> atomicTest) {
    }
    
    public void testFinished(final AtomicTest<FrameworkMethod> atomicTest) {
        JUnitEventListener.HANDLER.finishTest(atomicTest, null);
    }
    
    public void beforeInvocation(final Object runner, final FrameworkMethod method, final ReflectiveCallable callable) {
        if (JUnitEventListener.HANDLER.isJunitMethod(method)) {
            JUnitEventListener.HANDLER.startUtilMethod(runner, method, callable);
        }
    }
    
    public void afterInvocation(final Object runner, final FrameworkMethod method, final ReflectiveCallable callable, final Throwable thrown) {
        if (JUnitEventListener.HANDLER.isJunitMethod(method)) {
            JUnitEventListener.HANDLER.finishUtilMethod(method, thrown);
        }
    }
    
    public Class<FrameworkMethod> supportedType() {
        return FrameworkMethod.class;
    }
    
    static {
        HANDLER = new RunningHandler();
    }
}
