package meng.xing.bizwatch;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import meng.xing.bizwatch.entity.Measurement;
import meng.xing.bizwatch.repository.InfluxDBConnect;
import meng.xing.bizwatch.service.InfluxService;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitCommandLineRunner implements CommandLineRunner {
    @Autowired
    InfluxService influxService;

    @Override
    public void run(String... args) throws Exception {
        influxService.getMesurement().stream().forEach(measurement -> System.out.println(measurement.getName()));
       influxService.getInstance().stream().forEach(instance -> System.out.println(instance.getName()));
    }


}
