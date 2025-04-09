package com.bridgelabz.advancedlevel;
import java.lang.annotation.*;
import java.lang.reflect.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface RoleAllowed {
    String value();
}
class SecurityContext {
    static String currentUserRole = "USER";
}
// service class with restricted methods
class AdminService {
    @RoleAllowed("ADMIN")
    public void deleteAllRecords() {
        System.out.println("All records deleted by admin.");
    }
    @RoleAllowed("USER")
    public void viewRecords() {
        System.out.println("User is viewing records.");
    }
}

// Role-based access control
public class RoleBasedAccessControl {
    public static void main(String[] args) {
        AdminService service = new AdminService();
        invokeIfAllowed(service, "viewRecords");
        invokeIfAllowed(service, "deleteAllRecords");
    }
    public static void invokeIfAllowed(Object obj, String methodName) {
        try {
            Method method = obj.getClass().getMethod(methodName);
            if (method.isAnnotationPresent(RoleAllowed.class)) {
                RoleAllowed roleAllowed = method.getAnnotation(RoleAllowed.class);
                if (SecurityContext.currentUserRole.equals(roleAllowed.value())) {
                    method.invoke(obj);
                } else {
                    System.out.println("Access Denied! Method: " + methodName);
                }
            } else {
                method.invoke(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

