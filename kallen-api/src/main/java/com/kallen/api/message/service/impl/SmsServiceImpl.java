package com.kallen.api.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kallen.api.message.dao.SmsDao;
import com.kallen.api.message.dto.SmsDTO;
import com.kallen.api.message.entity.SmsEntity;
import com.kallen.api.message.service.SmsService;
import com.kallen.api.message.sms.AbstractSmsService;
import com.kallen.api.message.sms.SmsConfig;
import com.kallen.api.message.sms.SmsFactory;
import com.kallen.common.exception.KallenException;
import com.kallen.common.service.impl.CrudServiceImpl;
import com.kallen.common.utils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Title: SmsServiceImpl</p >
 * <p>Description: 短信-服务层接口实现类</p >
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
public class SmsServiceImpl extends CrudServiceImpl<SmsDao, SmsEntity, SmsDTO> implements SmsService {

    @Override
    public QueryWrapper<SmsEntity> getWrapper(Map<String, Object> params){
        String platform = (String)params.get("platform");

        QueryWrapper<SmsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(platform), "platform", platform);

        return wrapper;
    }

    @Override
    public SmsDTO get(Long id) {
        SmsEntity entity = baseDao.selectById(id);

        SmsDTO dto = ConvertUtils.sourceToTarget(entity, SmsDTO.class);
        dto.setConfig(JSON.parseObject(entity.getSmsConfig(), SmsConfig.class));

        return dto;
    }

    @Override
    public void send(String smsCode, String mobile, String params) {
        LinkedHashMap<String, String> map;
        try {
            map = JSON.parseObject(params, LinkedHashMap.class);
        }catch (Exception e){
//            throw new KallenException(ErrorCode.JSON_FORMAT_ERROR);
            throw new KallenException("JSON格式错误");
        }

        //短信服务
        AbstractSmsService service = SmsFactory.build(smsCode);
        if(service == null){
//            throw new KallenException(ErrorCode.SMS_CONFIG);
            throw new KallenException("短信配置错误");
        }

        //发送短信
        service.sendSms(smsCode, mobile, map);
    }

    @Override
    public SmsEntity getBySmsCode(String smsCode) {
        QueryWrapper<SmsEntity> query = new QueryWrapper<>();
        query.eq("sms_code", smsCode);

        return baseDao.selectOne(query);
    }

    @Override
    public void save(SmsDTO dto) {
        SmsEntity entity = ConvertUtils.sourceToTarget(dto, SmsEntity.class);
        entity.setSmsConfig(JSON.toJSONString(dto.getConfig()));
        baseDao.insert(entity);
    }

    @Override
    public void update(SmsDTO dto) {
        SmsEntity entity = ConvertUtils.sourceToTarget(dto, SmsEntity.class);
        entity.setSmsConfig(JSON.toJSONString(dto.getConfig()));
        baseDao.updateById(entity);
    }
}
