package cn.itcast.dtx.notifydemo.bank1;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author admin
 *
 * mq实现最大努力通知方案一： 接收方监听MQ，适用于内部系统
 */
@SpringBootApplication(scanBasePackages = {"cn.itcast.*"})
@EnableDiscoveryClient
@EnableScheduling // 开启定时任务
@MapperScan(basePackages = {"cn.itcast.dtx.txmsgdemo.bank1.dao"})
public class TxMsgBank1Service {

	public static void main(String[] args) {
		SpringApplication.run(TxMsgBank1Service.class, args);
	}

}
