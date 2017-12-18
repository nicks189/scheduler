package com.nicksteger.scheduler.data.repository;

import com.nicksteger.scheduler.data.entity.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Long> {
    List<UserRole> findByUserRoleId(long id);

    List<UserRole> findByUserId(long id);
}
