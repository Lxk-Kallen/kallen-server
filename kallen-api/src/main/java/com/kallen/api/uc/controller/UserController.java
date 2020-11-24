package com.kallen.api.uc.controller;

import com.kallen.api.core.annotation.ApiVersion;
import com.kallen.api.core.constant.ApiConstant;
import com.kallen.api.uc.entity.req.SendCodeReq;
import com.kallen.api.uc.service.UserService;
import com.kallen.common.utils.Result;
import com.kallen.common.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: UserController</p >
 * <p>Description: 用户-对外接口层</p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/11/24    Kallen    Created
 * </pre>
 * @mail LXK00515@163.com
 */
@RestController
@RequestMapping("/user")
@Api(tags = "平台用户")
@ApiVersion(ApiConstant.KALLEN_VERSION)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("sendCode")
    @ApiOperation("发送验证码")
    public Result sendCode(@RequestBody SendCodeReq sendCodeReq, HttpServletRequest request) {
        ValidatorUtils.validateEntity(sendCodeReq);

        userService.sendCode(sendCodeReq);
        return new Result();
    }
}
