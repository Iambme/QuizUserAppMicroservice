package com.quiz.prototype;

import com.quiz.entity.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RolePrototype {

    public static Role getAdminRole(){
        return new Role(1,"ROLE_ADMIN");
    }
    public static Role getTutorRole(){
        return new Role(2,"ROLE_TUTOR");
    }
    public static Role getStudentRole(){
        return new Role(3,"ROLE_STUDENT");
    }
}
