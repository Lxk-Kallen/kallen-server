package com.kallen.generator.controller;

import com.kallen.generator.service.GeneratorService;
import com.kallen.generator.utils.PageUtils;
import com.kallen.generator.utils.Query;
import com.kallen.generator.utils.R;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <p>Title: GeneratorController</p >
 * <p>Description: 代码生成器-对外接口层</p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/11/20    Kallen    Created
 * </pre>
 * @mail LXK00515@163.com
 */
@Controller
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    /**
     * <p>列表</p>
     *
     * @param params        参数
     * @return {@link R}    分页信息
     * @author Kallen
     * @since 2020/11/23 15:52
    */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils pageUtils = generatorService.queryList(new Query(params));

        return R.ok().put("page", pageUtils);
    }

    /**
     * <p>生成代码</p>
     *
     * @param tables
     * @param response
     * @author Kallen
     * @since 2020/11/23 15:57
    */
    @RequestMapping("/code")
    public void code(String tables, HttpServletResponse response) throws IOException {
        byte[] data = generatorService.generatorCode(tables.split(","));

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"kallen.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
