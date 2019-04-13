package com.tensquare.service;

import com.tensquare.dao.LabelDao;
import com.tensquare.entity.Label;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
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
     * 动态封装查询条件方法, 返回封装的查询条件
     *
     * @param label 查询条件
     * @return 封装后的查询条件
     */
    private Specification<Label> createSpecification(Label label) {
        //根据传入的标签对象, 创建一个动态的查询条件
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                // 创建条件对象集合, 以便最终拼接
                ArrayList<Predicate> predicateList = new ArrayList<>();
                //动态拼接查询方法, 拼接id查询
                if (label.getId() != null && label.getId() != "") {
                    Path<Object> id = root.get("id"); //获取查询的属性
                    Predicate predicateId = criteriaBuilder.equal(id, label.getId());//拼接查询条件
                    predicateList.add(predicateId);
                }
                //动态拼接查询方法, 拼接labelname查询
                if (label.getLabelname() != null && label.getLabelname() != "") {
                    Predicate predicate = criteriaBuilder.equal
                            (root.get("labelname"), label.getLabelname());//拼接查询条件
                    predicateList.add(predicate);
                }
                //动态拼接查询方法, 拼接state查询
                if (label.getState() != null && label.getState() != "") {
                    Predicate predicate = criteriaBuilder.equal
                            (root.get("state"), label.getState());//拼接查询条件
                    predicateList.add(predicate);
                }
                //动态拼接查询方法, 拼接count查询
                if (label.getCount() != null) {
                    Predicate predicate = criteriaBuilder.equal
                            (root.get("count"), label.getCount());//拼接查询条件
                    predicateList.add(predicate);
                }
                //动态拼接查询方法, 拼接recommend查询
                if (label.getRecommend() != null && label.getRecommend() != "") {
                    Predicate predicate = criteriaBuilder.equal
                            (root.get("recommend"), label.getCount());//拼接查询条件
                    predicateList.add(predicate);
                }

                Predicate[] predicateArr = predicateList.toArray(new Predicate[predicateList.size()]);
                return criteriaBuilder.and(predicateArr);
            }
        };
    }


    /**
     * 查找所有标签方法
     *
     * @return 标签列表
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据Id 查找标签方法
     *
     * @param id 标签id
     * @return 对应的标签
     */
    public Label findById(String id) {
        Optional<Label> labelOptional = labelDao.findById(id);
        if (labelOptional == null) {
            return null;
        } else {
            return labelOptional.get();
        }
    }

    /**
     * 修改更新标签方法
     *
     * @param label 标签, 包含主键
     */
    @Transactional
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 保存新增标签方法
     *
     * @param label 传入的新标签, 无主键
     */
    public void save(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    /**
     * 根据id删除标签方法
     *
     * @param id 传入需删除标签的Id
     */
    @Transactional
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * 分页条件查询
     *
     * @param page  当前页码
     * @param size  每页大小
     * @param label 封装的查询条件对象
     * @return 分页对象
     */
    public Page<Label> findByPage(int page, int size, Label label) {
        // 使用PageRequest类的of方法, 直接创建分页查询条件对象, 传入参数页码, 从0开始
        PageRequest pageRequest = PageRequest.of(page-1,size);
        Specification<Label> specification = createSpecification(label);
        return labelDao.findAll(specification, pageRequest);
    }




    /**
     * /label/search
     * @param label
     * @return
     */
    public List<Label> search(Label label) {

        Specification<Label> specification = createSpecification(label);
       return labelDao.findAll(specification);
    }
}
