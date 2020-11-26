package com.kallen.api.tian.controller;

import com.kallen.api.core.annotation.Login;
import com.kallen.api.core.annotation.LoginUser;
import com.kallen.api.tian.service.TianService;
import com.kallen.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>Title: TianController</p >
 * <p>Description: </p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/11/23    Kallen    Created
 * </pre>
 * @mail LXK00515@163.com
 */
@RestController
@RequestMapping("/tian")
@Api(tags = "天气")
public class TianController {


    @Autowired
    private TianService tianService;

    @ApiOperation("朋友圈文案")
    @GetMapping("/pyqwenan")
    @Login
    public Result<Map<String, Object>> wechatContent() {
        return new Result<Map<String, Object>>().ok(tianService.wechatContent());
    }

    @ApiOperation("经典台词")
    @GetMapping("/dialogue")
    @Login
    public Result<Map<String, Object>> dialogue() {
        return new Result<Map<String, Object>>().ok(tianService.dialogue());
    }

    @ApiOperation("彩虹屁")
    @GetMapping("/caihp")
    @Login
    public Result<Map<String, Object>> caihp() {
        return new Result<Map<String, Object>>().ok(tianService.caihp());
    }

    @ApiOperation("舔狗文案")
    @GetMapping("/tiangou")
    @Login
    public Result<Map<String, Object>> tiangou() {
        return new Result<Map<String, Object>>().ok(tianService.tiangou());
    }

    @ApiOperation("网易云热评")
    @GetMapping("/hotreview")
    @Login
    public Result<Map<String, Object>> hotreview() {
        return new Result<Map<String, Object>>().ok(tianService.hotreview());
    }

    @ApiOperation("励志古文")
    @GetMapping("/lzmy")
    @Login
    public Result<Map<String, Object>> lzmy() {
        return new Result<Map<String, Object>>().ok(tianService.lzmy());
    }
}
