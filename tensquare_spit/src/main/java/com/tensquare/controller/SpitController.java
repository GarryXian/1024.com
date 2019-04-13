package com.tensquare.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.Spit;
import com.tensquare.entity.StatusCode;
import com.tensquare.service.SpitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;


    /**
     * /spit 增加吐槽
     * @param spit 吐槽实体
     * @return 结果集
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addSpit(@RequestBody Spit spit){
        spitService.add(spit);
        return new Result(true, StatusCode.OK,"增加成功");
    }

    /**
     * /spit 查询spit全部列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true,StatusCode.OK,"查询全部成功", spitService.findAll());
    }

    /**
     * /spit/{spitId} 根据ID查询吐槽, 如果找不到吐槽, 会跑异常
     * @param spitId 要查询的吐槽ID
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{spitId}")
    public Result findById(@PathVariable(value = "spitId") String spitId){
        return new Result(true,StatusCode.OK,"根据Id查询成功", spitService.findById(spitId));
    }

    /**
     * /spit/{spitId} 根据ID修改吐槽
     * @param spitId
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/{spitId}")
    public Result updateById(@PathVariable(value = "spitId") String spitId, @RequestBody Spit spit){
        spit.setId(spitId);
        spitService.updateById(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 根据id删除吐槽
     * @param spitId
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{spitId}")
    public Result deleteById(@PathVariable(value = "spitId") String spitId){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * /spit/thumbup/{spitId} 吐槽点赞
     * @param spitId
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/thumbup/{spitId}")
    public Result likeSpit(@PathVariable(value = "spitId") String spitId){
        String userId = "oli";
        return spitService.likeSpit(spitId, userId);
    }

    /**
     * 根据条件的基本搜索
     * @param spit
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/search")
    public Result search(@RequestBody Spit spit){
        return new Result(true,StatusCode.OK,"搜索成功", spitService.search(spit));
    }

    /**
     * /search/{page}/{size} 分页查询(无条件)
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/search/{page}/{size}")
    public Result findByPage(@PathVariable(value = "page") int page,
                             @PathVariable(value = "size") int size){

        Page<Spit> byPage = spitService.findByPage(page, size);

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("total", byPage.getTotalElements());
        resMap.put("rows", byPage.getContent());
        return new Result(true,StatusCode.OK,"分页查询成功",resMap);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/comment/{parentid}/{page}/{size}")
    public Result findByPageAndParentId(@PathVariable String parentid,
                                        @PathVariable int page,
                                        @PathVariable int size){

        Page<Spit> byPage = spitService.findByPageAndParentId(parentid, page, size);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("total", byPage.getTotalElements());
        resMap.put("rows", byPage.getContent());
        return new Result(true,StatusCode.OK,"分页查询成功",resMap);
    }

}