package cn.itcast.dtx.notifydemo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-10-29-00:03
 * Description:
 * @author admin
 */
@Configuration
@ComponentScan("cn.itcast")
public class DataBaseConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource durid(){
        return new DruidDataSource();
    }

}