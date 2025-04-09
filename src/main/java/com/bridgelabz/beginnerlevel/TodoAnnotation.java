package com.bridgelabz.beginnerlevel;
import java.lang.annotation.*;
import java.lang.reflect.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Todo {
    String task();
    String assignedTo();
    String priority() default "MEDIUM";
}
class ProjectFeatures {
    @Todo(task = "Implement login feature", assignedTo = "Prakul")
    public void login() {}
    @Todo(task = "Integrate payment gateway", assignedTo = "parth", priority = "HIGH")
    public void payment() {}
    @Todo(task = "Add dark mode", assignedTo = "shreya", priority = "LOW")
    public void darkMode() {}
    public void completedFeature() {
        System.out.println("Feature already completed");
    }
}
// Use Reflection to print all pending tasks
public class TodoAnnotation {
    public static void main(String[] args) {
        Class<ProjectFeatures> clazz = ProjectFeatures.class;
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Todo.class)) {
                Todo todo = method.getAnnotation(Todo.class);
                System.out.println("Method: " + method.getName());
                System.out.println("  ➤ Task: " + todo.task());
                System.out.println("  ➤ Assigned To: " + todo.assignedTo());
                System.out.println("  ➤ Priority: " + todo.priority());
                System.out.println();
            }
        }
    }
}

