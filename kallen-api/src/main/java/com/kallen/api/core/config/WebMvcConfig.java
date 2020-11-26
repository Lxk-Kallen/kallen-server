package com.kallen.api.core.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: WebMvcConfig</p >
 * <p>Description: </p >
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
public class WebMvcConfig implements WebMvcConfigurer {

    /***
     * 复写WebMvcConfigurationSupport 会造成静态资源配置丢失  坑~
     * 貌似WebMvcConfigurationSupport的权重高于WebMvcConfigurer 所以需要二次配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
                .maxAge(3600);
    }
//
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new ByteArrayHttpMessageConverter());
//        converters.add(new StringHttpMessageConverter());
//        converters.add(new ResourceHttpMessageConverter());
//        converters.add(new AllEncompassingFormHttpMessageConverter());
//        converters.add(new StringHttpMessageConverter());
//        converters.add(jackson2HttpMessageConverter());
//    }
//
//    @Bean
//    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper mapper = new ObjectMapper();
//
//        //日期格式转换
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.setDateFormat(new SimpleDateFormat(DateUtils.DATE_TIME_PATTERN));
//        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//
//        //Long类型转String类型
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//        mapper.registerModule(simpleModule);
//
//        converter.setObjectMapper(mapper);
//        return converter;
//    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));// @ResponseBody 解决乱码
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new AllEncompassingFormHttpMessageConverter());
        converters.add(fastJsonHttpMessageConverter());
    }


    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        //Long类型转String类型
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        // ToStringSerializer 是这个包 com.alibaba.fastjson.serializer.ToStringSerializer
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        config.setSerializeConfig(serializeConfig);
        config.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue, // 保留map空的字段
                SerializerFeature.WriteNullStringAsEmpty, // 将String类型的null转成""
                SerializerFeature.WriteNullNumberAsZero, // 将Number类型的null转成0
                SerializerFeature.WriteNullListAsEmpty, // 将List类型的null转成[]
                SerializerFeature.WriteNullBooleanAsFalse, // 将Boolean类型的null转成false
                SerializerFeature.WriteDateUseDateFormat,  //日期格式转换
                SerializerFeature.DisableCircularReferenceDetect // 避免循环引用
        );
        config.setSerializeFilters(valueFilter);
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        // 解决中文乱码问题，相当于在Controller上的@RequestMapping中加了个属性produces = "application/json"
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypeList);
        return converter;
    }

    /**
     * FastJson过滤器将null值转换为字符串
     * obj 是class
     * s 是key值
     * o1 是value值
     */
    public static final ValueFilter valueFilter = (obj, s, v) -> {
        if (v == null) {
            return "";
        }
        return v;
    };

}
