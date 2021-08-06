package ru.testit.junit;

import java.lang.annotation.*;
import org.junit.runners.model.*;
import org.junit.*;

public enum MethodType
{
    TEST((Class<? extends Annotation>)Test.class), 
    BEFORE_METHOD((Class<? extends Annotation>)Before.class), 
    AFTER_METHOD((Class<? extends Annotation>)After.class), 
    BEFORE_CLASS((Class<? extends Annotation>)BeforeClass.class), 
    AFTER_CLASS((Class<? extends Annotation>)AfterClass.class);
    
    private Class<? extends Annotation> clazz;
    
    private MethodType(final Class<? extends Annotation> markerAnnotation) {
        this.clazz = markerAnnotation;
    }
    
    static MethodType detect(final FrameworkMethod method) {
        for (final MethodType type : values()) {
            if (null != method.getAnnotation((Class)type.clazz)) {
                return type;
            }
        }
        return null;
    }
}
