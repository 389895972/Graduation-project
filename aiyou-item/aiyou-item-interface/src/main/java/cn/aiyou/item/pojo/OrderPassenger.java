package cn.aiyou.item.pojo;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * (TbOrderPassenger)实体类
 *
 * @author makejava
 * @since 2020-03-26 16:21:29
 */
@Table(name="tb_order_passenger")
public class OrderPassenger implements Serializable {
    private static final long serialVersionUID = 650259031978044445L;

    private Long orderId;
    /**
    * 乘客名字
    */
    private String passengerName;
    /**
    * 乘客手机号
    */
    private String passengerTel;
    /**
    * 乘客身份证信息
    */
    private String passengerIdcard;
    /**
    * 表id
    */
    private Long id;
    /**
     *座位号
     */
    private Integer seatNo;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerTel() {
        return passengerTel;
    }

    public void setPassengerTel(String passengerTel) {
        this.passengerTel = passengerTel;
    }

    public String getPassengerIdcard() {
        return passengerIdcard;
    }

    public void setPassengerIdcard(String passengerIdcard) {
        this.passengerIdcard = passengerIdcard;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Integer seatNo) {
        this.seatNo = seatNo;
    }

    @Override
    public String toString() {
        return "OrderPassenger{" +
                "orderId=" + orderId +
                ", passengerName='" + passengerName + '\'' +
                ", passengerTel='" + passengerTel + '\'' +
                ", passengerIdcard='" + passengerIdcard + '\'' +
                ", id=" + id +
                ", seatNo=" + seatNo +
                '}';
    }
}
