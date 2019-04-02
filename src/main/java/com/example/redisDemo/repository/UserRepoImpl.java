package com.example.redisDemo.repository;

import com.example.redisDemo.model.User;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserRepoImpl implements UserRepository {

    private RedisTemplate<String, User> redisTemplate;//to provide redis server a template to fetch data

    private HashOperations hashOperations;

    public UserRepoImpl(RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(User user) {
        hashOperations.put("USER",user.getId(),user);

    }

    @Override
    public Map<String,User> findAll() {
        return hashOperations.entries("USER");
    }

    @Override
    public void update(User user) {
        save(user);
    }

    @Override
    public void delete(String id) {
        hashOperations.delete("USER",id);
    }

    @Override
    public User findById(String id) {
        return (User) hashOperations.get("USER",id);
    }
}
