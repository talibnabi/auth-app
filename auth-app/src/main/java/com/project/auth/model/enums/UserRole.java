package com.project.auth.model.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum UserRole {

    ADMIN(1), USER(2), UNKNOWN(0);

    private final Integer id;
    private static final Map<Integer, UserRole> userRoleMap = new HashMap<>();

    static {
        for (UserRole role : values()) {
            userRoleMap.put(role.id, role);
        }
    }

    public static UserRole fromId(Integer id) {
        return userRoleMap.getOrDefault(id, UNKNOWN);
    }

}
