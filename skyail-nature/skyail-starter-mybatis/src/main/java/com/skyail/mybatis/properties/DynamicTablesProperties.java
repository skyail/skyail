package com.skyail.mybatis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * @Description: 动态表名配置
 * @Auther: zxing
 * @Date: 2020/10/30 14:27
 * @company：CTTIC
 */
@Data
@Configuration
@ConfigurationProperties(value = "mp")
@PropertySource(value = "classpath:/skyail-mybatis.yml")
public class DynamicTablesProperties {

    /**
       * @Description 年表名称列表
    	* @Author zxing
    	* @Date 2020/10/30 14:28
    	* @Company CTTIC
    	* @Param
    	* @return
    	**/
    private List<String> yearTableNames;

    /**
       * @Description 日表名称列表
    	* @Author zxing
    	* @Date 2020/10/30 14:29
    	* @Company CTTIC
    	* @Param
    	* @return
    	**/
    private List<String> dayTableNames;


	/**
	 * 月表名稱列表
	 */
	private List<String> monthTableNames;
}
