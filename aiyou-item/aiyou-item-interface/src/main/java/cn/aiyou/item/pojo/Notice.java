package cn.aiyou.item.pojo;



import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * (TbOrder)实体类
 *
 * @author makejava
 * @since 2020-03-20 16:35:47
 */
@Table(name="tb_notice")
public class Notice implements Serializable {
    private static final long serialVersionUID = 197603183032722988L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String content;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;

}