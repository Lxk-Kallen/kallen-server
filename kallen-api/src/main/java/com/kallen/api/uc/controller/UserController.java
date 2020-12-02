package com.kallen.api.uc.controller;

import com.kallen.api.core.annotation.ApiVersion;
import com.kallen.api.core.annotation.Login;
import com.kallen.api.core.constant.ApiConstant;
import com.kallen.api.uc.entity.req.BindPasswordReq;
import com.kallen.api.uc.entity.req.CodeLoginReq;
import com.kallen.api.uc.entity.req.PasswordLoginReq;
import com.kallen.api.uc.entity.req.SendCodeReq;
import com.kallen.api.uc.entity.vo.LoginSuccessVO;
import com.kallen.api.uc.service.UserService;
import com.kallen.common.utils.IpUtils;
import com.kallen.common.utils.Result;
import com.kallen.common.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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

    /**
     * <p>发送验证码</p>
     *
     * @param sendCodeReq
     * @param request
     * @author Kallen
     * @since 2020/12/2 15:21
    */
    @PostMapping("sendCode")
    @ApiOperation("发送验证码")
    public Result sendCode(@RequestBody SendCodeReq sendCodeReq, HttpServletRequest request) {
        ValidatorUtils.validateEntity(sendCodeReq);

        String code = userService.sendCode(sendCodeReq);
        return new Result().ok(code);
    }

    /**
     * <p>验证码登录</p>
     *
     * @param codeLoginReq
     * @param request
     * @return {@link Result< LoginSuccessVO>}
     * @author Kallen
     * @since 2020/12/2 17:06
    */
    @PostMapping("codeLogin")
    @ApiOperation("验证码登录")
    public Result<LoginSuccessVO> codeLogin(@RequestBody CodeLoginReq codeLoginReq, HttpServletRequest request) {
        ValidatorUtils.validateEntity(codeLoginReq);
        codeLoginReq.setIp(IpUtils.getIpAddr(request));
        return new Result<LoginSuccessVO>().ok(userService.codeLogin(codeLoginReq));
    }

    /**
     * <p>绑定密码</p>
     *
     * @param userId
     * @param bindPasswordReq
     * @param request
     * @return {@link Result< LoginSuccessVO>}
     * @author Kallen
     * @since 2020/12/2 17:07
    */
    @Login
    @PostMapping("updatePassword")
    @ApiOperation(("绑定密码"))
    public Result<LoginSuccessVO> bindPassword(@ApiIgnore @RequestAttribute("userId") Long userId, @RequestBody BindPasswordReq bindPasswordReq, HttpServletRequest request) {
        bindPasswordReq.setUserId(userId);
        ValidatorUtils.validateEntity(bindPasswordReq);
        bindPasswordReq.setIp(IpUtils.getIpAddr(request));

        userService.bindPassword(bindPasswordReq);

        return new Result<>();
    }

    /**
     * <p>密码登录</p>
     *
     * @param passwordLoginReq
     * @param request
     * @return {@link Result< LoginSuccessVO>}
     * @author Kallen
     * @since 2020/12/2 17:07
    */
    @PostMapping("passwordLogin")
    @ApiOperation("密码登录")
    public Result<LoginSuccessVO> passwordLogin(@RequestBody PasswordLoginReq passwordLoginReq, HttpServletRequest request) {
        ValidatorUtils.validateEntity(passwordLoginReq);
        passwordLoginReq.setIp(IpUtils.getIpAddr(request));

        return new Result<LoginSuccessVO>().ok(userService.passwordLogin(passwordLoginReq));
    }
}
