package com.tensquare.dao;

import com.tensquare.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * SpringDataJpa 数据访问接口
 */
public interface LabelDao extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label>{


}
