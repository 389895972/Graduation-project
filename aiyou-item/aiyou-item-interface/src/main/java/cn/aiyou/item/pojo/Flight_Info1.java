package cn.aiyou.item.pojo;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import java.sql.Date;
import java.util.Date;

@Table(name="tb_flight_info")
public class Flight_Info1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightNo;

    private String flightName;

    private String fromCity;

    private  String toCity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date flightDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date fromTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date toTime;

    private Integer economyPrice;

    private Integer headPrice;

    private Integer headNum;

    private Integer economyNum;

    public Integer getEconomyNum() {
        return economyNum;
    }

    public void setEconomyNum(Integer economyNum) {
        this.economyNum = economyNum;
    }

    public Integer getHeadNum() {
        return headNum;
    }

    public void setHeadNum(Integer headNum) {
        this.headNum = headNum;
    }


    private String remarks;

    public Long getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(Long flightNo) {
        this.flightNo = flightNo;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public Date getFlightDate() {
        return flightDate;
    }

//        public Date getFlightDate() {
//        if(flightDate!=null){
//            DateUtils.date2String(flightDate,"yyyy-MM-dd HH:mm");
//
//        }
//        return flightDate;
//    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public Integer getEconomyPrice() {
        return economyPrice;
    }

    public void setEconomyPrice(Integer economyPrice) {
        this.economyPrice = economyPrice;
    }

    public Integer getHeadPrice() {
        return headPrice;
    }

    public void setHeadPrice(Integer headPrice) {
        this.headPrice = headPrice;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Flight_Info1{" +
                "flightNo=" + flightNo +
                ", flightName='" + flightName + '\'' +
                ", fromCity='" + fromCity + '\'' +
                ", toCity='" + toCity + '\'' +
                ", flightDate=" + flightDate +
                ", fromTime=" + fromTime +
                ", toTime=" + toTime +
                ", economyPrice=" + economyPrice +
                ", headPrice=" + headPrice +
                ", economyNum=" + economyNum +
                ", headNum=" + headNum +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
