package com.kallen.api.core.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: IdGeneratorConfig</p >
 * <p>Description: 全局唯一ID生成器</p >
 * <p>Copyright: Kallen. Copyright(c) 2020</p >
 * <link>http://www.buqu.icu</link>
 * <p>版权所有，侵权必究！</p >
 *
 * @author Kallen
 * @version 1.0.0
 * <pre>History:
 *       2020/11/26    Kallen    Created
 * </pre>
 * @mail LXK00515@163.com
 */
@Configuration
public class IdGeneratorConfig {

    @Value("${Snowflake.workerId:1}")
    private Long workerId;

    @Value("${Snowflake.workerId:1}")
    private Long dateCenterId;

    @Bean
    Snowflake snowflake() {
        return new Snowflake(workerId, dateCenterId);
    }

    @Bean
    String codeGenerator() {
        return UUID.fastUUID().toString().replace("-", "");
    }
}
