package com.TaskManagement.Security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.TaskManagement.Enum.Permission;
import com.TaskManagement.Enum.Role;

@Component
public class PermissionConfig {

    public static Map<Role, Set<Permission>> getRolePermission() {

        Map<Role, Set<Permission>> map = new HashMap<>();

        map.put(Role.ADMIN, new HashSet<>(Arrays.asList(
                Permission.ISSUE_VIEW,
                Permission.ISSUE_CREATE,
                Permission.ISSUE_EDIT,
                Permission.ISSUE_DELETE,
                Permission.COMMENT_ADD,
                Permission.COMMENT_DELETE,
                Permission.USER_MANAGE
        )));

        map.put(Role.MANAGER, new HashSet<>(Arrays.asList(
                Permission.ISSUE_VIEW,
                Permission.ISSUE_CREATE,
                Permission.ISSUE_EDIT,
                Permission.COMMENT_ADD
        )));

        map.put(Role.DEVELOPER, new HashSet<>(Arrays.asList(
                Permission.ISSUE_VIEW,
                Permission.ISSUE_CREATE,
                Permission.ISSUE_EDIT,
                Permission.COMMENT_ADD
        )));

        map.put(Role.TESTER, new HashSet<>(Arrays.asList(
                Permission.ISSUE_VIEW,
                Permission.COMMENT_ADD
        )));

        return map;
    }
}
