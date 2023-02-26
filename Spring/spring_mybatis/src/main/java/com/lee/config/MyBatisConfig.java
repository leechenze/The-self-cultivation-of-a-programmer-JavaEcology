package com.lee.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class MyBatisConfig {

    /**
     * 用以配置
     *  <typeAliases>
     *      <package name="com.lee.domain"/>
     *  </typeAliases>
     *  <environments default="mysql">
     *      <environment id="mysql">
     *          <transactionManager type="JDBC"></transactionManager>
     *          <dataSource type="POOLED">
     *              <property name="driver" value="${jdbc.driver}"></property>
     *              <property name="url" value="${jdbc.url}"></property>
     *              <property name="username" value="${jdbc.username}"></property>
     *              <property name="password" value="${jdbc.password}"></property>
     *          </dataSource>
     *      </environment>
     *  </environments>
     * @param dataSource
     * @return
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setTypeAliasesPackage("com.lee.domain");
        ssfb.setDataSource(dataSource);
        return ssfb;
    }

    /**
     * 用以配置
     *      <mapper>
     *          <package name="com.lee.dao"></package>
     *      </mapper>
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.lee.dao");
        return msc;
    }
}
