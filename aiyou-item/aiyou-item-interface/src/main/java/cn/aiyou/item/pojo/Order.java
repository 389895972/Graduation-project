package cn.aiyou.item.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * (TbOrder)实体类
 *
 * @author makejava
 * @since 2020-03-20 16:35:47
 */
@Table(name="tb_order")
public class Order implements Serializable {
    private static final long serialVersionUID = 197603183032722988L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    
    private Long uesrId;
    private Long flightNo;

    private String fromCity;
    
    private String toCity;
    
    private Integer ticketPrice;

//    @Transient
//    private List<String> passengers;

    @Transient
    private Map<String,Integer> passengers;
//    public List<String> getPassengers() {
//        return passengers;
//    }

//    public void setPassengers(List<String> passengers) {
//        this.passengers = passengers;
//    }

    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date flightDate;

    //@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date fromTime;

    //@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date toTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getFlightDate() {
        return flightDate;
    }

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUesrId() {
        return uesrId;
    }

    public void setUesrId(Long uesrId) {
        this.uesrId = uesrId;
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

    public Integer getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Integer ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Map<String, Integer> getPassengers() {
        return passengers;
    }

    public void setPassengers(Map<String, Integer> passengers) {
        this.passengers = passengers;
    }

    public Long getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(Long flightNo) {
        this.flightNo = flightNo;
    }
}