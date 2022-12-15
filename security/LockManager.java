package com.asecl.simdc.org.simdc_project.security;

import com.asecl.simdc.org.simdc_project.util.ILockCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;


@Service
@Scope("singleton")
@Slf4j
public class LockManager {
    @Autowired
    private RedisTemplate<String, String> mRedisTemplate;

    @Value("${lock.execute.timeout}")
    private int LOCK_EXE_TIMEOUT;

    @Value("${lock.timeout}")
    private int LOCK_TIMEOUT;

    private synchronized void unlock(String key){
        mRedisTemplate.delete(key);
    }

    private synchronized boolean isLock(String key) {
        try {
            Boolean result = mRedisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.set(key.getBytes(), "1".getBytes(), Expiration.seconds(LOCK_EXE_TIMEOUT) , RedisStringCommands.SetOption.ifAbsent());
                }
            });
            return result;
        } catch (Exception e) {
            log.error("set redis occured an exception", e);
        }
        return false;
//        String result = mRedisTemplate.execute(new RedisCallback<String>() {
//            @Override
//            public String doInRedis(@NotNull RedisConnection connection) throws DataAccessException {
//                Jedis conn = (Jedis) connection.getNativeConnection();
//                SetParams params = new SetParams();
//                params.ex(seconds);
//                params.nx();
//                return conn.set(key, "1", params);
//            }
//        });
//        return result != null && result.equalsIgnoreCase(LOCK_SUCCESS);
    }

    public <T> T TryLock(String key, ILockCallback<T> callback){
        boolean isLock = isLock(key);
        final int SLEEP_TIME = 1;
        final int RETRY_NUM = LOCK_TIMEOUT * 1000;
        int i;
        for (i = 0; i < RETRY_NUM; i++) {
            if (isLock) {
                break;
            }
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                log.warn("wait redis error {}", e.getMessage());
            }
            isLock = isLock(key);
        }

        if (!isLock) {
            throw new RuntimeException("Lock Timeout Error !!");
        }

        try {
            return callback.exec();
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }finally {
            unlock(key);
        }
    }
}
