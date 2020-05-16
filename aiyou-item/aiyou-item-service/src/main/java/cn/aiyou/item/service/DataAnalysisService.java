package cn.aiyou.item.service;


import cn.aiyou.item.mapper.FlightMapper1;
import cn.aiyou.item.mapper.HotCityMapper;
import cn.aiyou.item.mapper.NullRateMapper;
import cn.aiyou.item.pojo.Flight_Info;
import cn.aiyou.item.pojo.Flight_Info1;
import cn.aiyou.item.pojo.HotCity;

import cn.aiyou.item.pojo.NullRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataAnalysisService {
    @Autowired
    private HotCityMapper hotCityMapper;


    @Autowired
    private NullRateMapper nullRateMapper;


    @Autowired
    private FlightMapper1 flightMapper;

    public HotCity hotcitys() {
        Example example=new Example(HotCity.class);
        Example.Criteria criteria=example.createCriteria();
        example.setOrderByClause("date Desc limit 1");
        HotCity hotCity = this.hotCityMapper.selectOneByExample(example);
        return hotCity;
    }

    public List<Flight_Info> queryNullRateFlightNo() {
        Example example=new Example(NullRate.class);
        Example.Criteria criteria=example.createCriteria();
        example.setOrderByClause("null_rate Desc limit 6");


        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);


        Example example1=new Example(Flight_Info1.class);
        Example.Criteria criteria1=example1.createCriteria();
        criteria1.andGreaterThan("flightDate",format);
        List<Flight_Info> flight_infos = this.flightMapper.selectByExample(example1);
        List<Long> collect = flight_infos.stream().map(Flight_Info::getFlightNo).collect(Collectors.toList());
        criteria.andIn("flightNo",collect);
        example.setDistinct(true);
        List<NullRate> nullRates = this.nullRateMapper.selectByExample(example);

        List<Long>  flightNos=new ArrayList<>();
        nullRates.forEach(nullRate -> {
           flightNos.add(nullRate.getFlightNo());
        });
        Flight_Info flight_info=new Flight_Info();
        List<Flight_Info> list=new ArrayList<>();
        flightNos.forEach(flightNo->{
            flight_info.setFlightNo(flightNo);
          list.add( this.flightMapper.selectByPrimaryKey(flight_info));
        });
        return list;
    }
}
