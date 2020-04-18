package cn.aiyou.item.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_flight_seat")
public class FlightSeat {
    @Id
    private Integer id;

    private Long flightNo;

    private Integer seatNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(Long flightNo) {
        this.flightNo = flightNo;
    }

    public Integer getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Integer seatNo) {
        this.seatNo = seatNo;
    }

    @Override
    public String toString() {
        return "FlightSeat{" +
                "id=" + id +
                ", flightNo=" + flightNo +
                ", seatNo=" + seatNo +
                '}';
    }
}
