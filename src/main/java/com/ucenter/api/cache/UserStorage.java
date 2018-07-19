package com.ucenter.api.cache;

import com.ucenter.api.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wangfeng on 2018/7/19.
 */
@Component
public class UserStorage {
    @Autowired
    private RedisClient redisClient;

    public boolean setUser(String userId) {
        String result = redisClient.set("user_key+" + userId, userId);
        if ("OK".equals(result)) {
            return true;
        }
        return false;
    }

    public String getUser(String userId) {
        return redisClient.get("user_key+" + userId);
    }

}
