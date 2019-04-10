package com.tensquare.controller;

import com.tensquare.entity.Label;
import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import com.tensquare.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 标签的控制器类, 包含基本的CRUD操作方法,使用REST风格的请求
 */
@RestController
@RequestMapping("/label")
@CrossOrigin("*") //接受所有不同域的跨域请求
public class LabelController {

    @Autowired
    private LabelService labelService;


    /**
     * 查询所有标签方法
     * @return 结果集
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll());
    }

    /**
     * 保存新增标签方法
     * @param label 新增标签
     * @return 结果集
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result saveLabel(@RequestBody Label label){
        labelService.save(label);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 根据id 查找标签
     * @param id 传入的id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/{id}")
    public Result findById(@PathVariable(value = "id") String id){
        return new Result(true,StatusCode.OK,"查询成功",labelService.findById(id));
    }

    /**
     * 更新标签方法
     * @param id 更新的标签对应的id
     * @param label 新的标签
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/{id}")
    public Result update(@PathVariable(value = "id") String id, @RequestBody Label label){
        label.setId(id);
        labelService.update(label);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/{id}")
    public Result deleteById(@PathVariable(value = "id") String id){
        labelService.deleteById(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }
}
