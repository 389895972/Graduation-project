package cn.aiyou.item.controller;



import cn.aiyou.item.pojo.Flight_Info;
import cn.aiyou.item.pojo.HotCity;
import cn.aiyou.item.service.DataAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("data")
public class DataAnalysis {

    @Autowired
    private DataAnalysisService dataService;

    @GetMapping("hotcity")
    public ResponseEntity<HotCity> hotcity(){
        HotCity hotCity=  this.dataService.hotcitys();
        return ResponseEntity.ok(hotCity);
    }

    @GetMapping("nullrate")
    public ResponseEntity<List<Flight_Info>> nullrate(){
      List<Flight_Info>   list=this.dataService.queryNullRateFlightNo();
        return ResponseEntity.ok(list);
    }
}
