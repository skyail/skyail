package com.skyail.mybatis.config;

import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.skyail.mybatis.properties.DynamicTablesProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: TODO
 * @Auther: zxing
 * @Date: 2020/9/16 10:12
 * @company：CTTIC
 */
@Configuration
public class DynamicTablesConfiguration {

	private SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private SimpleDateFormat sdfDay = new SimpleDateFormat("yyyyMMdd");


	@Autowired
	private DynamicTablesProperties tableNames;
	/**
	 * 分页插件
	 *
	 * @return
	 */
	@Bean(name = "dynamicTableNameInterceptor")
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
		dynamicTableNameParser.setTableNameHandlerMap(new HashMap<String, ITableNameHandler>(2){{
			//年表列表
			List<String> yearTables = tableNames.getYearTableNames();
			//日表列表
			List<String> dayTables = tableNames.getDayTableNames();
			//动态表规则 初始表名+_+日期
			yearTables.forEach(tableTitle -> put(tableTitle,(metaObject, sql, tableName) -> tableName + "_" + sdfYear.format(new Date())));
			dayTables.forEach(tableTitle -> put(tableTitle,(metaObject, sql, tableName) -> tableName + "_" + sdfDay.format(new Date())));
		}});
		paginationInterceptor.setSqlParserList(Collections.singletonList(dynamicTableNameParser));
		return paginationInterceptor;
	}


}
