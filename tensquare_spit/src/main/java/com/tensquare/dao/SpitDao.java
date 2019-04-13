package com.tensquare.dao;

import com.tensquare.entity.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoDB持久层标准接口
 */
public interface SpitDao extends MongoRepository<Spit, String> {

    //利用标准命名实现条件查询
    public Page<Spit> findByParentid(String parentid, Pageable pageable);
}
