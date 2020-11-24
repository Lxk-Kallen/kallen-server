package com.kallen.api.message.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kallen.api.message.dao.SmsLogDao;
import com.kallen.api.message.dto.SmsLogDTO;
import com.kallen.api.message.entity.SmsLogEntity;
import com.kallen.api.message.service.SmsLogService;
import com.kallen.common.service.impl.CrudServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Title: SmsLogServiceImpl</p >
 * <p>Description: 短信日志-服务层接口实现类</p >
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
@Service
public class SmsLogServiceImpl extends CrudServiceImpl<SmsLogDao, SmsLogEntity, SmsLogDTO> implements SmsLogService {

    @Override
    public QueryWrapper<SmsLogEntity> getWrapper(Map<String, Object> params){
        String smsCode = (String)params.get("smsCode");
        String mobile = (String)params.get("mobile");
        String status = (String)params.get("status");

        QueryWrapper<SmsLogEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(smsCode), "sms_code", smsCode);
        wrapper.like(StringUtils.isNotBlank(mobile), "mobile", mobile);
        wrapper.eq(StringUtils.isNotBlank(status), "status", status);

        return wrapper;
    }

    @Override
    public void save(String smsCode, Integer platform, String mobile, LinkedHashMap<String, String> params, Integer status) {

        SmsLogEntity smsLog = new SmsLogEntity();
        smsLog.setSmsCode(smsCode);
        smsLog.setPlatform(platform);
        smsLog.setMobile(mobile);

        //设置短信参数
        if(MapUtil.isNotEmpty(params)){
            int index = 1;
            for(String value : params.values()){
                if(index == 1){
                    smsLog.setParams1(value);
                }else if(index == 2){
                    smsLog.setParams2(value);
                }else if(index == 3){
                    smsLog.setParams3(value);
                }else if(index == 4){
                    smsLog.setParams4(value);
                }
                index++;
            }
        }

        smsLog.setStatus(status);

        baseDao.insert(smsLog);
    }
}