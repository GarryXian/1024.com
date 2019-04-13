package com.tensquare.service;

import com.tensquare.dao.SpitDao;
import com.tensquare.entity.Result;
import com.tensquare.entity.Spit;
import com.tensquare.entity.StatusCode;
import com.tensquare.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 吐槽服务类, 实现基本的CRUD操作
 */
@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 增加吐槽方法
     *
     * @param spit 吐槽实体
     */
    public void add(Spit spit) {
        spit.setId(idWorker.nextId() + "");
        spit.setPublishtime(new Date());
        if (spit.getParentid() != null || !"".equals(spit.getParentid())) {
            //父级吐槽评论数加1
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        //增加的吐槽没有父级ID, 直接增加即可
        spitDao.save(spit);
    }

    /**
     * 查询spit全部列表
     *
     * @return
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据Id查询吐槽
     *
     * @param spitId ID
     * @return
     */
    public Spit findById(String spitId) {
        return spitDao.findById(spitId).get();
    }

    /**
     * 修改吐槽
     *
     * @param spit
     */
    public void updateById(Spit spit) {
        spitDao.save(spit);
    }

    /**
     * 根据id删除吐槽
     *
     * @param spitId
     */
    public void deleteById(String spitId) {
        spitDao.deleteById(spitId);
    }

    /**
     * 点赞吐槽, 一个用户仅可点赞一次, 使用redis解决
     *
     * @param spitId
     * @param userId
     * @return
     */
    public Result likeSpit(String spitId, String userId) {

        String flag = (String) redisTemplate.boundValueOps("spit_" + userId + spitId).get();

        if (flag == null || "".equals(flag)) {
            // 用户未点过此吐槽的赞
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spitId));
            Update update = new Update();
            update.inc("thumbup", 1);
            mongoTemplate.updateFirst(query, update, "spit");
            redisTemplate.boundValueOps("spit_" + userId + spitId).set("1");
            return new Result(true, StatusCode.OK, "点赞成功");
        } else {
            //用户已点过赞
            return new Result(false, StatusCode.REPEAT_ERROR, "不能重复点赞哦");
        }
    }

    /**
     * 搜索方法, 动态精准搜索, 后续处理, 将数据同步到索引库中, 进行搜索
     *
     * @param spit
     * @return
     */
    public List<Spit> search(Spit spit) {
        Query query = new Query();
        if (spit.getUserid() != null && !"".equals(spit.getUserid())) {
            query.addCriteria(Criteria.where("userid").is(spit.getUserid()));
        }
        return mongoTemplate.find(query, Spit.class);
    }

    /**
     * 分页查询方法
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return spitDao.findAll(pageRequest);
    }

    /**
     * 根据父id 分页查找
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByPageAndParentId(String parentid, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentid, pageRequest);
    }
}
