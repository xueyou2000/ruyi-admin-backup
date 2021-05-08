package com.xueyou.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.system.auth.annotation.HasPermissions;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.system.domain.DictType;
import com.xueyou.admin.system.log.annotation.OperLog;
import com.xueyou.admin.system.log.enums.BusinessType;
import com.xueyou.admin.system.model.dto.DictTypeQuery;
import com.xueyou.admin.system.service.DictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 字典类型 控制器
 *
 * @author xueyou
 * @date 2020/12/21
 */
@RestController
@RequestMapping("/system/dict/type")
@Api(value = "DictTypeController", tags = "字典类型")
public class DictTypeController {

    @Resource
    private DictTypeService dictTypeService;

    /**
     * 查询字典类型
     */
    @ApiOperation(value = "查询字典类型",  httpMethod = "POST")
    @PostMapping("get/{dictId}")
    public DictType get(@PathVariable("dictId") Long dictId) {
        return dictTypeService.selectDictTypeById(dictId);
    }

    /**
     * 查询字典类型列表
     */
    @ApiOperation(value = "查询字典类型列表",  httpMethod = "POST")
    @PostMapping(value = "findByPage/{pageSize}/{pageNumber}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", paramType = "path", required = true)
    })
    public Response<IPage<DictType>> findByPage(@PathVariable Integer pageSize, @PathVariable Integer pageNumber, @RequestBody DictTypeQuery queryDto) {
        IPage<DictType> page = new Page<>(pageNumber, pageSize);
        return Response.ok(dictTypeService.queryByPage(page, queryDto.getDictType(), queryDto.getQueryBaseDto()));
    }

    /**
     * 新增字典类型
     */
    @ApiOperation(value = "新增字典类型",  httpMethod = "POST")
    @OperLog(title = "字典类型", businessType = BusinessType.INSERT)
    @HasPermissions("system:dict:add")
    @PostMapping("add")
    public Response<Boolean> add(@RequestBody DictType dictType) {
        return Response.ok(dictTypeService.save(dictType));
    }

    /**
     * 修改字典类型
     */
    @ApiOperation(value = "修改字典类型",  httpMethod = "POST")
    @OperLog(title = "字典类型", businessType = BusinessType.UPDATE)
    @HasPermissions("system:dict:update")
    @PostMapping("update")
    public Response<Boolean> update(@RequestBody DictType dictType) {
        return Response.ok(dictTypeService.updateDictType(dictType));
    }

    /**
     * 删除字典类型
     */
    @ApiOperation(value = "删除字典类型",  httpMethod = "POST")
    @OperLog(title = "字典类型", businessType = BusinessType.DELETE)
    @HasPermissions("system:dict:remove")
    @PostMapping("remove")
    public Response<Boolean> remove(@RequestParam String ids) throws Exception {
        return Response.ok(dictTypeService.deleteDictTypeByIds(ids));
    }

}
