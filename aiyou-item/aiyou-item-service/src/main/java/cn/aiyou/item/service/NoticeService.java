package cn.aiyou.item.service;

import cn.aiyou.item.mapper.NoticeMapper;
import cn.aiyou.item.pojo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;


    public List<Notice> getNotice() {
        Example example=new Example(Notice.class);
        Example.Criteria criteria=example.createCriteria();
        example.setOrderByClause("time DESC Limit 5");
        List<Notice> notices = this.noticeMapper.selectByExample(example);
        System.out.println(notices.get(1).getTime());
        System.out.println(notices.get(2).getTime());
        System.out.println(notices.get(0).getTime());
        return notices;
    }

    public List<Notice> getAllNotices() {
        Example example=new Example(Notice.class);
        Example.Criteria criteria=example.createCriteria();
        example.setOrderByClause("time DESC");
        List<Notice> notices = this.noticeMapper.selectByExample(example);
        System.out.println(notices.get(1).getTime());
        System.out.println(notices.get(2).getTime());
        System.out.println(notices.get(0).getTime());
        return notices;
    }

    public int insertNotice(Notice notice) {
        int i = this.noticeMapper.insertSelective(notice);
        return i;
    }
}
