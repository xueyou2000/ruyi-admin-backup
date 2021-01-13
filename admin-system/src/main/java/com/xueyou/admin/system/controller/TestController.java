package com.xueyou.admin.system.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author chendong
 * @version V1.0.0
 * @since 2020/9/29 1:55 下午
 */
@Slf4j
@RestController
@RequestMapping("/test")
@Api(value = "TestController", tags = "测试接口")
public class TestController {

//    @Resource
//    private CustomerService customerService;
//
//    @ApiOperation(value = "测试",  httpMethod = "POST")
//    @RequestMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response<String> hello() {
//        RedisUtils.setEx("xy-test","hello",20);
//        return Response.ok("Hello World");
//    }
//
//    /**
//     * 查询全部商户
//     */
//    @ApiOperation(value = "查询全部商户",  httpMethod = "POST")
//    @RequestMapping(value = "/queryAllCustomer", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response<List<Customer>> queryAllCustomer() {
//        return Response.ok(customerService.getBaseMapper().selectList(null));
//    }
//
//    /**
//     * 分页查询商户
//     */
//    @ApiOperation(value = "分页查询商户",  httpMethod = "POST")
//    @PostMapping(value = "/findByPage/{pageNumber}/{pageSize}", produces = "application/json;charset=UTF-8")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageNumber", value = "页码", paramType = "path",required = true),
//            @ApiImplicitParam(name = "pageSize", value = "每页数量", paramType = "path",required = true)
//    })
//    public Response<IPage<Customer>> findByPage(@PathVariable Integer pageNumber, @PathVariable Integer pageSize, @RequestBody CustomerQueryDto queryDto) {
//        IPage<Customer> page = new Page<>(pageNumber, pageSize);
//        return Response.ok(customerService.queryByPage(page,queryDto.getCustomer(), queryDto.getQueryBaseDto()));
//    }

}
