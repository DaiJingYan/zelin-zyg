package com.zyg.manager.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.zyg.manager.entity.vo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zyg.manager.entity.SpecificationEntity;
import com.zyg.manager.service.SpecificationService;
import com.zyg.common.utils.PageUtils;
import com.zyg.common.utils.R;



/**
 * 
 *
 * @author hbxfwf
 * @email 2568783935@qq.com
 * @date 2021-11-23 10:27:07
 */
@RestController
@RequestMapping("manager/specification")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    /**
     * 列表（分页）
     */
    @GetMapping("/list")
    //@RequiresPermissions("manager:specification:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = specificationService.queryPage(params);

        return R.ok().put("page", page);
    }
    //2. 查询所有的规格列表（不分页）
    @GetMapping("/findAll")
    public R findAll(){
        List<SpecificationEntity> list = specificationService.list();
        return R.ok().put("list",list);
    }
    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("manager:specification:info")
    public R info(@PathVariable("id") String id){
		Specification specification = specificationService.findById(id);

        return R.ok().put("specification", specification);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("manager:specification:save")
    public R save(@RequestBody Specification specification){
        System.out.println("specification = " + specification);
		specificationService.save(specification);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody Specification specification){
		specificationService.update(specification);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("manager:specification:delete")
    public R delete(@RequestBody String[] ids){
		specificationService.delete(Arrays.asList(ids));

        return R.ok();
    }

}
