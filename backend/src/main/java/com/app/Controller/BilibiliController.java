package com.app.Controller;

import com.app.Entity.Bilibili;
import com.app.Service.BilibiliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
public class BilibiliController {
    @Autowired
    private BilibiliService service;

    @PostMapping("/upload")
    public String uploadFile(@RequestBody Map<String, String> data) throws IOException {
        String fileDataUrl = data.get("file");

        // 解析数据 URL，提取文件内容
        String base64Data = fileDataUrl.substring(fileDataUrl.indexOf(",") + 1);
        byte[] fileBytes = Base64.getDecoder().decode(base64Data);

        // 将文件内容保存到临时文件
        File tempFile = File.createTempFile("uploaded_file", ".xlsx");
        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(fileBytes);
        }

        // 处理临时文件
        service.importExcelData(tempFile);

        // 删除临时文件
        tempFile.delete();

        return "redirect:/success";
    }

    //获取给定用户的所有收入记录
    @GetMapping("/records/{userName}")
    public List<Bilibili> getRecords(@PathVariable String userName){
        return service.getRecords(userName);
    }

    //获取数据库b站转入贝壳所有信息
    @GetMapping("/bilibili")
    public List<Bilibili> getAllInfo(){
        return service.getAllInfo();
    }

    //获取数据库b站转入贝壳所有信息，并合并相同用户的信息
    @GetMapping("/bilibiliCombine/{offset}/{pageSize}")
    public List<Bilibili> getSourceCombine(@PathVariable int offset, @PathVariable int pageSize){
        return service.getSourceCombine(offset, pageSize);
    }

    //获取指定达人的所有数据
    @GetMapping("/bilibiliWithGivenSource/{source}")
    public List<Bilibili> getRecordsWithGivenSource(@PathVariable String source){
        System.out.println(source);
        return service.getRecordsWithGivenSource(source);
    }

    //按转入时间/转入单号/转入贝壳/来源/状态排序
    @GetMapping("/bilibiliPaginationAndSort/{offset}/{pageSize}/{field}")
    public List<Bilibili> getRecordsWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
        return service.getRecordsWithPaginationAndSort(offset, pageSize, field);
    }

    //分页
    @GetMapping("/bilibiliPagination/{offset}/{pageSize}")
    public List<Bilibili> getRecordsWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        return service.getRecordsWithPagination(offset, pageSize);
    }

    //展示特定时间范围内的所有数据
    @GetMapping("/bilibiliWithinGivenDates/{startDate}/{endDate}")
    public List<Bilibili> recordsWithinGivenDates(@PathVariable String startDate, @PathVariable String endDate){return service.recordsWithinGivenDates(startDate, endDate);}

    //获取b站总收益
    @GetMapping("/bilibiliRevenue")
    public double getBilibiliRevenue(){
        return service.getBilibiliRevenue();
    }
}
