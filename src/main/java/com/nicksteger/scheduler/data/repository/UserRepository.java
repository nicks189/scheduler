package com.nicksteger.scheduler.data.repository;

import com.nicksteger.scheduler.data.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findById(long userId);

    User findByUsername(String username);
}
