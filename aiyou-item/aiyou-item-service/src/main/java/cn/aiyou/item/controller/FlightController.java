package cn.aiyou.item.controller;

import cn.aiyou.common.entity.PageResult;
import cn.aiyou.common.entity.Result;
import cn.aiyou.item.pojo.Flight_Info1;

import cn.aiyou.item.service.FlightService;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@Api(tags = "航班管理相关接口")
@RequestMapping("flight")
public class FlightController {

    @Autowired
    private FlightService flightService;

    //按条件查询航班信息
    @PostMapping("querys")
    @ApiOperation("按条件查询航班信息的接口")
    public ResponseEntity<PageInfo<Flight_Info1>> queryFlights(@RequestBody Flight_Info1 flight_info, @RequestParam("page")int page) throws ParseException {
        System.out.println(flight_info.getFlightDate());
        System.out.println(page);
//        DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        String format = sdf.format(flight_info.getFlightDate());
//        Date date=sdf.parse(format);
//        System.out.println(format);
//        Date date=sdf.parse(format);
//        flight_info.setFlightDate(date);
//        System.out.println(date);
//        Date date=new Date(flight_info.getFlightDate().getTime());
//        flight_info.setFlightDate(date);
      // System.out.println(date);
        Date date = new Date(flight_info.getFlightDate().getTime());
        System.out.println(date);
        flight_info.setFlightDate(date);
       PageInfo<Flight_Info1> flight_infos = this.flightService.queryFlights(flight_info,page);

      // return  ResponseEntity.ok(new Result(true,200,"查询成功",flight_infos));
       return  ResponseEntity.ok(flight_infos);
    }


    //新增航班信息
    @PostMapping("insert")
    @ApiOperation("新增航班信息")
    public ResponseEntity<Result> insertFlight(@RequestBody Flight_Info1 flight_info1){
        System.out.println(flight_info1.toString());
        System.out.println(flight_info1.getFlightDate());
        System.out.println(flight_info1.getFromTime());
        Date date = new Date(flight_info1.getFlightDate().getTime());
        Date date1 = new Date(flight_info1.getFromTime().getTime());
        Timestamp timestamp=new Timestamp(flight_info1.getFromTime().getTime());
        Boolean flag= this.flightService.insert(flight_info1);
        System.out.println(date);
        System.out.println(timestamp);
        if(flag) {
            return ResponseEntity.ok(new Result());
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    //新增航班信息
    @PostMapping("update/{flightNo}")
    @ApiOperation("修改航班信息")
    public ResponseEntity<Result> updateFlight(@RequestBody Flight_Info1 flight_info1,@PathVariable Long flightNo){
        System.out.println(flight_info1.toString());
        System.out.println(flight_info1.getFlightDate());
        System.out.println(flight_info1.getFromTime());
        Date date = new Date(flight_info1.getFlightDate().getTime());
        Date date1 = new Date(flight_info1.getFromTime().getTime());
        Timestamp timestamp=new Timestamp(flight_info1.getFromTime().getTime());
        this.flightService.update(flight_info1,flightNo);
        System.out.println(date);
        System.out.println(timestamp);
        return ResponseEntity.ok(new Result());
    }
    //根据航班编号查询航班信息
    @GetMapping("flightInfo/{flightNo}")
    @ApiOperation("根据航班编号查询航班信息")
    @ApiImplicitParam(name = "flightNo", value = "航班号",  required = true)
    public  ResponseEntity<Flight_Info1>  queryFlightInfoByNo(@PathVariable("flightNo") Long flightno){

       Flight_Info1 flight_info1= this.flightService.queryFlightInfoByNo(flightno);
        return ResponseEntity.ok(flight_info1);
    }


    @GetMapping("findAllFlights/{page}")
    @ApiOperation("查询所有航班")
    public  ResponseEntity<PageInfo<Flight_Info1>> queryAllFlights(@PathVariable Integer page){
         PageInfo<Flight_Info1> pageInfo=this.flightService.queryAllFlights(page);
         return ResponseEntity.ok(pageInfo);

    }

    @GetMapping("delete/{flightNo}")
    @ApiOperation("删除航班")
    public  ResponseEntity<Result> queryAllFlights(@PathVariable Long  flightNo){
        this.flightService.deleteFlights(flightNo);
        return ResponseEntity.ok(new Result(true,200,"删除成功",null));

    }
}
