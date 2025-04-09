package com.bridgelabz.beginnerlevel;
import java.lang.annotation.*;
import java.lang.reflect.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface ImportantMethod {
    String level() default "HIGH";
}
class TaskProcessor {
    @ImportantMethod
    public void runCriticalTask() {
        System.out.println("Running critical task");
    }
    @ImportantMethod(level = "LOW")
    public void logStatus() {
        System.out.println("Logging status");
    }
    public void helper() {
        System.out.println("Helper method");
    }
}
public class ImportantMethods {
    public static void main(String[] args) {
        Class<TaskProcessor> clazz = TaskProcessor.class;
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ImportantMethod.class)) {
                ImportantMethod annotation = method.getAnnotation(ImportantMethod.class);
                System.out.println("Method: " + method.getName() +
                        " | Importance Level: " + annotation.level());
            }
        }
    }
}
