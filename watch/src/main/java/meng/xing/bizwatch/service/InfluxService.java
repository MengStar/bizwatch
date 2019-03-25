package meng.xing.bizwatch.service;

import meng.xing.bizwatch.entity.Instance;
import meng.xing.bizwatch.entity.Measurement;
import meng.xing.bizwatch.repository.InfluxDBConnect;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Service
public class InfluxService {
    @Autowired
    InfluxDBConnect influxDBConnect;

    public List<Measurement> getMesurement() {
        influxDBConnect.influxDbBuild("cadvisor");
        QueryResult queryResult = influxDBConnect.query("SHOW MEASUREMENTS");
        QueryResult.Series result = queryResult.getResults().get(0).getSeries().get(0);
        return getMesurementData(result.getColumns(), result.getValues());

    }

    public List<Instance> getInstance() {
        influxDBConnect.influxDbBuild("cadvisor");
        QueryResult queryResult = influxDBConnect.query("select container_name  ,value from cpu_usage_per_cpu  group by container_name  order by time desc limit 1 ");
        List<QueryResult.Series> serieses = queryResult.getResults().get(0).getSeries();
        List<Instance> reult = new LinkedList<>();
        for (QueryResult.Series series : serieses) {
            Instance instance = new Instance();
            instance.setName(series.getValues().get(0).get(1).toString());
            ((LinkedList<Instance>) reult).addLast(instance);
        }
        return reult;
    }


    /***整理列名、行数据***/
    private List<Measurement> getMesurementData(List<String> columns, List<List<Object>> values) {
        List<Measurement> lists = new ArrayList<>();

        for (List<Object> list : values) {
            Measurement info = new Measurement();
            BeanWrapperImpl bean = new BeanWrapperImpl(info);
            for (int i = 0; i < list.size(); i++) {
                String propertyName = setColumns(columns.get(i));//字段名
                Object value = list.get(i);//相应字段值
                bean.setPropertyValue(propertyName, value);
            }

            lists.add(info);
        }

        return lists;
    }

    /***转义字段***/
    private String setColumns(String column) {
        String[] cols = column.split("_");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < cols.length; i++) {
            String col = cols[i].toLowerCase();
            if (i != 0) {
                String start = col.substring(0, 1).toUpperCase();
                String end = col.substring(1).toLowerCase();
                col = start + end;
            }
            sb.append(col);
        }
        return sb.toString();
    }
}

