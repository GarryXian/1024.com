package com.tensquare.service;

import com.tensquare.dao.LabelDao;
import com.tensquare.entity.Label;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 标签服务类, 包含对标签的CRUD基本操作
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 查找所有标签方法
     * @return 标签列表
     */
    public List<Label> findAll(){
       return labelDao.findAll();
    }

    /**
     * 根据Id 查找标签方法
     * @param id 标签id
     * @return 对应的标签
     */
    public Label findById(String id){
        Optional<Label> labelOptional = labelDao.findById(id);
        if (labelOptional==null) {
            return null;
        }else{
            return labelOptional.get();
        }
    }

    /**
     * 修改更新标签方法
     * @param label 标签, 包含主键
     */
   public void update(Label label){
        labelDao.save(label);
   }

    /**
     * 保存新增标签方法
     * @param label 传入的新标签, 无主键
     */
   public void save(Label label){
       label.setId(idWorker.nextId()+"");
       labelDao.save(label);
   }

    /**
     * 根据id删除标签方法
     * @param id 传入需删除标签的Id
     */
   public void deleteById(String id){
       labelDao.deleteById(id);
   }
}
