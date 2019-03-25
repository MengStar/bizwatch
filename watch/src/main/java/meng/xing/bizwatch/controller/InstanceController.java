package meng.xing.bizwatch.controller;

import meng.xing.bizwatch.entity.Instance;
import meng.xing.bizwatch.service.InfluxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/instance")
public class InstanceController {
    @Autowired
    InfluxService influxService;
    @GetMapping("/")
    public Mono<List<Instance>> list() {
        return Mono.just(influxService.getInstance());
    }
}
