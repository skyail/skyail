package com.skyail.tool.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.skyail.tool.properties.AutoTrimConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 接口参数自动trim
 */
@Configuration
@EnableConfigurationProperties({AutoTrimConfigurationProperties.class})
public class WebMvcStringTrimAutoConfiguration {

    //接口参数自动trim开关
    //@Value("${auto-trim}")
    //private boolean autoTrim;

    @Resource
    private AutoTrimConfigurationProperties properties;

    public class  Bind {

        @InitBinder
        public void initBinder(WebDataBinder binder) {

            if(!properties.getEnable()){
                return ;
            }

            // 构造方法中 boolean 参数含义为如果是空白字符串,是否转换为null
            // 即如果为true,那么 " " 会被转换为 null,否者为 ""
            StringTrimmerEditor propertyEditor = new StringTrimmerEditor(false);
            // 为 String 类对象注册编辑器
            binder.registerCustomEditor(String.class, propertyEditor);
        }
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

        if(!properties.getEnable()){
            return null;
        }

        return new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder
                        .deserializerByType(String.class, new StdScalarDeserializer<String>(String.class) {
                            @Override
                            public String deserialize(JsonParser jsonParser, DeserializationContext ctx)
                                    throws IOException {
                                return StringUtils.trimWhitespace(jsonParser.getValueAsString());
                            }
                        });
            }
        };
    }
}