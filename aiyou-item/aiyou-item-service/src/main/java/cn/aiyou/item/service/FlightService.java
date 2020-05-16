package cn.aiyou.item.service;


import cn.aiyou.item.mapper.FlightMapper;

import cn.aiyou.item.pojo.Flight_Info1;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightMapper flightMapper;

//    public List<Flight_Info1> queryFlights(Flight_Info1 flight_info) {
//        Example example=new Example(Flight_Info1.class);
//        Example.Criteria criteria=example.createCriteria();
//        criteria.andEqualTo("fromCity",flight_info.getFromCity()).andEqualTo("toCity",flight_info.getToCity()).andEqualTo("flightDate",flight_info.getFlightDate());
//        return  this.flightMapper.selectByExample(example);
//
//    }

    public PageInfo<Flight_Info1> queryFlights(Flight_Info1 flight_info, int page) {
//        Flight_Info result=new Flight_Info();
//        result.setFromCity(flight_info.getFromCity());
//        result.setToCity(flight_info.getToCity());
//        result.setFlightDate(flight_info.getFlightDate());
       // PageHelper.startPage(page,10);
        System.out.println(flight_info.getFlightDate().getTime());
        Example example=new Example(Flight_Info1.class);
        Example.Criteria criteria=example.createCriteria();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // criteria.andEqualTo("fromCity",flight_info.getFromCity()).andEqualTo("toCity",flight_info.getToCity()).andEqualTo("flightDate",flight_info.getFlightDate());
       criteria.andEqualTo("fromCity",flight_info.getFromCity()).andEqualTo("toCity",flight_info.getToCity()).andEqualTo("flightDate",simpleDateFormat.format(flight_info.getFlightDate()));
//       criteria.andEqualTo("fromCity",flight_info.getFromCity()).andEqualTo("toCity",flight_info.getToCity()).andEqualTo("flightDate",flight_info.getFlightDate());
       // List<Flight_Info1> flight_info1s = this.flightMapper.selectByExample(example);
        List<Flight_Info1> flight_info1s = this.flightMapper.selectByExample(example);
        PageInfo<Flight_Info1> result=new PageInfo<>(flight_info1s);
//       result.setList(flight_info1s);
//       // result.setItems(flight_info1s);
//        System.out.println(result.getTotal());
//
//       //result.setPageNum(page);
//      result.setTotal(result.getTotal());
       return result;
    }

    public Flight_Info1 queryFlightInfoByNo(Long flightno) {

        return this.flightMapper.selectByPrimaryKey(flightno);
    }

    public PageInfo<Flight_Info1> queryAllFlights(Integer page) {

        PageHelper.startPage(page,10);
        Example example=new Example(Flight_Info1.class);
        Example.Criteria criteria=example.createCriteria();
        example.setOrderByClause("flight_date DESC");
       // List<Flight_Info1> flight_info1s = this.flightMapper.selectAll();
       List<Flight_Info1> flight_info1s = this.flightMapper.selectByExample(example);
        PageInfo<Flight_Info1> pageInfo=new PageInfo<>(flight_info1s);
        return  pageInfo;
    }

    public Boolean insert(Flight_Info1 flight_info1) {
        Flight_Info1 reslut=new Flight_Info1();
        Date date = new Date(flight_info1.getFlightDate().getTime());
        Timestamp fromTime=new Timestamp(flight_info1.getFromTime().getTime());
        Timestamp toTime=new Timestamp(flight_info1.getToTime().getTime());
        reslut.setFlightDate(date);
        reslut.setFromTime(fromTime);
        reslut.setToTime(toTime);
        reslut.setFromCity(flight_info1.getFromCity());
        reslut.setToCity(flight_info1.getToCity());
        reslut.setEconomyNum(flight_info1.getEconomyNum());
        reslut.setEconomyPrice(flight_info1.getEconomyPrice());
        reslut.setHeadNum(flight_info1.getHeadNum());
        reslut.setHeadPrice(flight_info1.getHeadPrice());
        reslut.setFlightName(flight_info1.getFlightName());

        Boolean flag=this.flightMapper.insertSelective(reslut)==1;
        return flag;
    }

    public void deleteFlights(Long flightNo) {
        this.flightMapper.deleteByPrimaryKey(flightNo);
    }

    public void update(Flight_Info1 flight_info1,Long flightNo) {
        Flight_Info1 reslut=new Flight_Info1();
        Date date = new Date(flight_info1.getFlightDate().getTime());
        Timestamp fromTime=new Timestamp(flight_info1.getFromTime().getTime());
        Timestamp toTime=new Timestamp(flight_info1.getToTime().getTime());
        reslut.setFlightNo(flightNo);
        reslut.setFlightDate(date);
        reslut.setFromTime(fromTime);
        reslut.setToTime(toTime);
        reslut.setFromCity(flight_info1.getFromCity());
        reslut.setToCity(flight_info1.getToCity());
        reslut.setEconomyNum(flight_info1.getEconomyNum());
        reslut.setEconomyPrice(flight_info1.getEconomyPrice());
        reslut.setHeadNum(flight_info1.getHeadNum());
        reslut.setHeadPrice(flight_info1.getHeadPrice());
        reslut.setFlightName(flight_info1.getFlightName());
        this.flightMapper.updateByPrimaryKeySelective(reslut);
    }
}
