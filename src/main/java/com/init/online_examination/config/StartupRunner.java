package com.init.online_examination.config;

import com.init.online_examination.domain.Role;
import com.init.online_examination.service.TypeService;
import com.init.online_examination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


@Configuration
@Order(1)
public class StartupRunner implements CommandLineRunner {
    private UserService userService;
    private TypeService typeService;

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        addDefaultRole();
        addDefaultAdmin();
        addTypes();
    }

    public void addDefaultRole() {
        if (userService.countRole() == 0) {
            userService.createRole("TEACHER");
            userService.createRole("STUDENT");
        }
    }
    public void addTypes() {
        if (typeService.countTypes() == 0) {
            typeService.createType("单选题");
            typeService.createType("多选题");
            typeService.createType("判断题");
        }
    }

    public void addDefaultAdmin() throws Exception {
        if (userService.count() == 0) {
            Role roleAdmin = userService.getRole(1L);
            userService.create("管理员", "admin", "admin", roleAdmin);
        }
    }



}
