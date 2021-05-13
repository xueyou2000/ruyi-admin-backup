package com.xueyou.admin;

import com.xueyou.admin.common.core.utils.RandomUtils;
import com.xueyou.admin.system.domain.User;
import com.xueyou.admin.system.mapper.UserMapper;
import com.xueyou.admin.system.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author xueyou
 * @date 2021/5/12
 */
@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class SystemApplicationTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void createPassword() {
        // salt=[T3F2YZ], password=[425740ca6414a6ef758f816f34d1031e]
        String salt = RandomUtils.randomStr(6);
        String password = PasswordUtils.encryptPassword("admin", "admin", salt);
        log.info("salt=[{}], password=[{}]", salt, password);
    }

    @Test
    public void findUser() {
        User user = userMapper.selectUserById("0");
        log.info("user=[{}]", user);
    }

}
