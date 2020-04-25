package com.lhy.clock_in.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.bson.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelToMongo {

    public static String getDataFromExcel(InputStream inputStream) {

        Integer PORT = 27017;                    //端口号
        String IP = "localhost";                 //Ip
        String DATABASE = "java";            //数据库名称
        String USERNAME = "pmj";            //用户名
        String PASSWORD = "123456";            //密码
        String COLLECTION = "student";              //文档名称

        try {

            // 根据输入流导入Excel产生Workbook对象
            Workbook workbook = null;
            try {
                workbook = new HSSFWorkbook(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // IP，端口
            ServerAddress serverAddress = new ServerAddress(IP, PORT);
            List<ServerAddress> address = new ArrayList<ServerAddress>();
            address.add(serverAddress);



            // 用户名，数据库，密码
            MongoCredential credential = MongoCredential.createCredential(USERNAME, DATABASE, PASSWORD.toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);
            // 通过验证获取连接
            MongoClient mongoClient = new MongoClient(address, credentials);
            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE);
            // 连接文档
            MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION);
            System.out.println("连接成功");
            List<Document> documents = new ArrayList<Document>();
            List<String> fieldList = new ArrayList<String>();
            // 获取Excel文档中第一个表单
            Sheet sheet = workbook.getSheetAt(0);
            Row row0 = sheet.getRow(0);
            for (Cell cell : row0) {
                fieldList.add(cell.toString());
            }
            int rows = sheet.getLastRowNum() + 1;
            int cells = fieldList.size();
            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                Document document = new Document();
                for (int j = 0; j < cells; j++) {
                    Cell cell = row.getCell(j);
                    document.append(fieldList.get(j), cell.toString());
                }
                documents.add(document);
            }
            collection.insertMany(documents);
            System.out.println("插入成功");
            return "插入成功";
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return "插入失败";
        }
    }

}
