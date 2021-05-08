package com.xueyou.admin.system.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyou.admin.system.auth.annotation.HasPermissions;
import com.xueyou.admin.common.core.vo.Response;
import com.xueyou.admin.system.domain.DictData;
import com.xueyou.admin.system.log.annotation.OperLog;
import com.xueyou.admin.system.log.enums.BusinessType;
import com.xueyou.admin.system.model.dto.DictDataQuery;
import com.xueyou.admin.system.service.DictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 字典数据 控制器
 *
 * @author xueyou
 * @date 2020/12/21
 */
@RestController
@RequestMapping("/system/dict/data")
@Api(value = "DictDataController", tags = "字典数据")
public class DictDataController {

    @Resource
    private DictDataService dictDataService;

    /**
     * 查询字典类型列表
     */
    @ApiOperation(value = "查询字典数据列表",  httpMethod = "POST")
    @PostMapping(value = "findByPage/{pageSize}/{pageNumber}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "path", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", paramType = "path", required = true)
    })
    @HasPermissions("system:dict:view")
    public Response<IPage<DictData>> findByPage(@PathVariable Integer pageSize, @PathVariable Integer pageNumber, @RequestBody DictDataQuery queryDto) {
        IPage<DictData> page = new Page<>(pageNumber, pageSize);
        return Response.ok(dictDataService.queryByPage(page, queryDto.getDictData(), queryDto.getQueryBaseDto()));
    }

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     */
    @ApiOperation(value = "根据字典类型查询字典数据信息",  httpMethod = "POST")
    @PostMapping("type")
    public List<DictData> getType(@RequestParam String dictType) {
        return dictDataService.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @ApiOperation(value = "根据字典类型和字典键值查询字典数据信息",  httpMethod = "POST")
    @PostMapping("label")
    public String getLabel(@RequestParam String dictType, @RequestParam String dictValue) {
        return dictDataService.selectDictLabel(dictType, dictValue);
    }

    /**
     * 新增字典数据
     */
    @ApiOperation(value = "新增字典数据",  httpMethod = "POST")
    @OperLog(title = "字典数据", businessType = BusinessType.INSERT)
    @HasPermissions("system:dict:add")
    @PostMapping("add")
    public Response<Boolean> add(@RequestBody DictData dictData) {
        return Response.ok(dictDataService.save(dictData));
    }

    /**
     * 修改字典数据
     */
    @ApiOperation(value = "修改字典数据",  httpMethod = "POST")
    @OperLog(title = "字典数据", businessType = BusinessType.UPDATE)
    @HasPermissions("system:dict:update")
    @PostMapping("update")
    public Response<Boolean> update(@RequestBody DictData dictData) {
        return Response.ok(dictDataService.updateById(dictData));
    }

    /**
     * 删除字典数据
     */
    @ApiOperation(value = "删除字典数据",  httpMethod = "POST")
    @OperLog(title = "字典数据", businessType = BusinessType.DELETE)
    @HasPermissions("system:dict:remove")
    @PostMapping("remove")
    public Response<Boolean> remove(@RequestParam String ids) {
        return Response.ok(dictDataService.removeByIds(Arrays.asList(Convert.toStrArray(ids))));
    }

    /**
     * 获取字典数据
     */
    @ApiOperation(value = "获取字典数据",  httpMethod = "POST")
    @PostMapping("findByTypes")
    public Response<Map<String, List<DictData>>> findByTypes(@RequestParam String types) {
        return Response.ok(dictDataService.findByTypes(Convert.toStrArray(types)));
    }


}
