package cn.aiyou.item.controller;

import cn.aiyou.item.pojo.Notice;
import cn.aiyou.item.service.NoticeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("notice")
@Api(tags = "公告管理相关接口")
@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("getNotice")
    public ResponseEntity<List<Notice>> getNotice(){

       List<Notice> list= this.noticeService.getNotice();
       return ResponseEntity.ok(list);
    }

    @GetMapping("getAllNotices")
    public ResponseEntity<List<Notice>> getAllNotices(){

        List<Notice> list= this.noticeService.getAllNotices();
        return ResponseEntity.ok(list);
    }

    @PostMapping("insertNotice")
    public ResponseEntity<Integer> insertNotice(@RequestBody Notice notice){
         int i=this.noticeService.insertNotice(notice);
         return ResponseEntity.ok(i);
    }
}
