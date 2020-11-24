package com.kallen.api.tian.controller;

import com.kallen.api.tian.service.TianService;
import com.kallen.common.utils.Result;
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
public class TianController {


    @Autowired
    private TianService tianService;

    @GetMapping("/pyqwenan")
    public Result<Map<String, Object>> wechatContent() {
        return new Result<Map<String, Object>>().ok(tianService.wechatContent());
    }

    @GetMapping("/dialogue")
    public Result<Map<String, Object>> dialogue() {
        return new Result<Map<String, Object>>().ok(tianService.dialogue());
    }

    @GetMapping("/caihp")
    public Result<Map<String, Object>> caihp() {
        return new Result<Map<String, Object>>().ok(tianService.caihp());
    }

    @GetMapping("/tiangou")
    public Result<Map<String, Object>> tiangou() {
        return new Result<Map<String, Object>>().ok(tianService.tiangou());
    }

    @GetMapping("/hotreview")
    public Result<Map<String, Object>> hotreview() {
        return new Result<Map<String, Object>>().ok(tianService.hotreview());
    }

    @GetMapping("/lzmy")
    public Result<Map<String, Object>> lzmy() {
        return new Result<Map<String, Object>>().ok(tianService.lzmy());
    }
}
