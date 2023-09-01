package com.kap.core.config;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * <pre>
 * 마이바티스 Config 설정
 * </pre>
 *
 * @ClassName		    : MybatisConfig.java
 * @Description		: 마이바티스 Config 설정
 * @author 박주석
 * @since 2022.01.10
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 * 		 since		  author	            description
 *    ==========    ==========    ==============================
 *    2022.01.10	  박주석	             최초 생성
 * </pre>
 */
@Configuration
@Slf4j
//JAVA 파일 SCAN 위치
@MapperScan(basePackages= {"com.kap.service.dao"}, sqlSessionFactoryRef = "SqlSessionFactory", sqlSessionTemplateRef = "SessionTemplate")
//트랜젝션 사용
@EnableTransactionManagement
public class MybatisConfig {

    @Value("${mybatis-config.config-location}")
    private String mybatisLocation;

    @Value("${mybatis-config.mapper-location}")
    private String mapperLocation;

    //DATABASE
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.db.datasource")
    public DataSource DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory SqlSessionFactory(@Qualifier("dataSource") DataSource DataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        log.error("mybatisLocation : {}", mybatisLocation);
        log.error("mapperLocation : {}", mapperLocation);
        log.error("DataSource : {}", DataSource);
        sqlSessionFactoryBean.setDataSource(DataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResources(mybatisLocation)[0]);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(mapperLocation));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.kap.core.dto");
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "SessionTemplate")
    public SqlSessionTemplate SqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory firstSqlSessionFactory) {
        return new SqlSessionTemplate(firstSqlSessionFactory);
    }
}