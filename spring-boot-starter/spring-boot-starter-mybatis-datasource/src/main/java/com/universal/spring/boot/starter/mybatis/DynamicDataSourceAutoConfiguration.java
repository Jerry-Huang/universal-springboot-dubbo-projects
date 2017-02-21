package com.universal.spring.boot.starter.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jerryh on 2017/1/17.
 */
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@ComponentScan(basePackages = {"com.universal.spring.boot.starter.mybatis"})
public class DynamicDataSourceAutoConfiguration {

    @Autowired
    private Environment environment;

    @Autowired
    private DynamicDataSourceProperties properties;
    
    private static final String DEFAULT_MAPPER_LOCATIONS = "classpath*:com/universal/**/mapper/xml/*.xml";

    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource() throws Exception {

        final Map<Object, Object> targetDataSources = new HashMap<>();
        final Set<String> dataSourceNames = properties.getUrl().keySet();

        final DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        final Class<? extends DataSource> dataSourceClass = dataSourceBuilder.findType();

        for (String datasourceName : dataSourceNames) {
            final String driverClassName = properties.getDriverClassName().get(datasourceName);
            final String userName = properties.getUserName().get(datasourceName);
            final String password = properties.getPassword().get(datasourceName);
            final String url = properties.getUrl().get(datasourceName);

            final DataSource dataSource = dataSourceBuilder.type(dataSourceClass).driverClassName(driverClassName).url(url).username(userName).password(password).build();

            targetDataSources.put(datasourceName, dataSource);
        }

        final DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);

        final String defaultTarget = properties.getDefaultTarget();

        if (StringUtils.hasText(defaultTarget) && targetDataSources.containsKey(defaultTarget)) {
            dynamicDataSource.setDefaultTargetDataSource(targetDataSources.get(defaultTarget));
        }

        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {

        final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dynamicDataSource);

        final String mapperLocations = environment.getProperty("mybatis.mapper-locations", DEFAULT_MAPPER_LOCATIONS);
        final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setMapperLocations(resourcePatternResolver.getResources(mapperLocations));
        
        return sqlSessionFactory.getObject();
    }
}
