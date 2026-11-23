package com.jaesung.auction.user;

import org.apache.ibatis.annotations.Mapper;

/** SQL은 resources/mapper/UserMapper.xml에 있다. 메서드 이름 = XML의 id. */
@Mapper
public interface UserMapper {
    void insert(User user);

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
