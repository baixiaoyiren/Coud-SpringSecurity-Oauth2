package cn.itcast.dtx.notifydemo.bank2;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"cn.itcast.*"})

@MapperScan("cn.itcast.dtx.txmsgdemo.bank2.dao")
@EnableDiscoveryClient
public class TxMsgBank2Service {

	public static void main(String[] args) {
		SpringApplication.run(TxMsgBank2Service.class, args);
	}

}
