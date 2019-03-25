package meng.xing.bizwatch;

import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


import java.util.concurrent.TimeUnit;


@SpringBootApplication
@EnableConfigurationProperties
public class BizwatchApplication {

    public static void main(String[] args) {
        BizwatchApplication bizwatchApplication= new BizwatchApplication();

        SpringApplication.run(BizwatchApplication.class, args);
    }
;
}
