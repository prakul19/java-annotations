package com.bridgelabz.intermediatelevel;
import java.lang.annotation.*;
import java.lang.reflect.*;

// annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}
// Apply annotation in a class
class User {
    @MaxLength(10)
    private String username;
    public User(String username) {
        this.username = username;
        validate();
    }
    private void validate() {
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength annotation = field.getAnnotation(MaxLength.class);
                    String value = (String) field.get(this);
                    if (value != null && value.length() > annotation.value()) {
                        throw new IllegalArgumentException(field.getName() + " exceeds max length of " + annotation.value());
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Validation error", e);
        }
    }
    public void display() {
        System.out.println("Username: " + username);
    }
}
public class FieldValidation {
    public static void main(String[] args) {
        try {
            User u1 = new User("Prakul123");
            u1.display();
            User u2 = new User("VeryLongUsername123");
            u2.display();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }
}
