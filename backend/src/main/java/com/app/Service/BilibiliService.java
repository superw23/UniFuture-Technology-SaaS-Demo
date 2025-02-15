package com.app.Service;

import com.app.Entity.Bilibili;
import com.app.Repository.BilibiliRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
@Service
public class BilibiliService {
    @Autowired
    private BilibiliRepository repository;

    public void importExcelData(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);

        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        // 跳过表头
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            Bilibili bilibili = new Bilibili();
            bilibili.set转入时间(row.getCell(0).getStringCellValue());
            bilibili.set转入单号(row.getCell(1).getStringCellValue());
            bilibili.set贝壳(row.getCell(2).getStringCellValue());
            bilibili.set来源(row.getCell(3).getStringCellValue());
            bilibili.set状态(row.getCell(4).getStringCellValue());

            repository.save(bilibili);
        }
    }

    //获取数据库b站转入贝壳所有信息
    public List<Bilibili> getAllInfo(){
        return repository.findAll();
    }

    //获取数据库b站转入贝壳所有信息，并合并相同用户的信息
    public List<Bilibili> getSourceCombine(int offset,int pageSize){
        List<Bilibili> allInfo = repository.findAll();
        List<String> distinct来源 = repository.getDistinct来源();
        List<Bilibili> combinedRecords = new ArrayList<>();
        for(String 来源 : distinct来源){
            double total = 0;
            List<Bilibili> records = repository.findAllBy来源(来源);
            for(Bilibili record : records)
                total += Double.parseDouble(record.get贝壳());
            Bilibili combinedRecord = new Bilibili();
            combinedRecord.set来源(来源);
            combinedRecord.set贝壳(String.valueOf(new BigDecimal(total).setScale(2, RoundingMode.UP).doubleValue()));
            combinedRecords.add(combinedRecord);
        }
        return combinedRecords;
    }

    public List<Bilibili> getRecordsWithGivenSource(String source){
        return repository.findAllBy来源(source);
    }

    //按转入时间/转入单号/转入贝壳/来源/状态排序
    public List<Bilibili> getRecordsWithPaginationAndSort(int offset, int pageSize, String field){
        Page<Bilibili> records = repository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return records.getContent();
    }

    //获取给定用户的所有收入记录
    public List<Bilibili> getRecords(String userName){
        return repository.findAllBy来源(userName);
    }

    //分页
    public List<Bilibili> getRecordsWithPagination(int offset,int pageSize){
        return repository.findAll(PageRequest.of(offset, pageSize)).getContent();
    }

    //展示特定时间范围内的所有数据
    public List<Bilibili> recordsWithinGivenDates(String startDate, String endDate){
        return repository.findBy转入时间Between(startDate,endDate);
    }

    //获取b站总收益
    public double getBilibiliRevenue(){
        double total = 0;
        List<Bilibili> records = repository.findAll();

        for(Bilibili record : records){
            double income = Double.parseDouble(record.get贝壳());
            total += income;
        }
        return total;
    }
}
