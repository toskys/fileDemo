package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@RestController
public class FileController {

    @PostMapping("/upFile")
    public String upFile(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return "文件为空";
        }
        String fileName = multipartFile.getOriginalFilename();
        File localFile = new File(System.getProperty("user.dir") + "\\src\\main\\fileData", new Date().getTime() + fileName.substring(fileName.lastIndexOf(".")));
        try {
            multipartFile.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localFile.getAbsolutePath();
    }

    @GetMapping("/loadFile")
    public String loadFile(HttpServletResponse response) {
        File localFile = new File(System.getProperty("user.dir") + "\\src\\main\\fileData", "1561692984873.txt");
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;fileName=" + "1561692984873.txt");
        byte[] buffer = new byte[1024];
        FileInputStream fis = null; //文件输入流
        BufferedInputStream bis = null;
        OutputStream os = null; //输出流
        try {
            os = response.getOutputStream();
            fis = new FileInputStream(localFile);
            bis = new BufferedInputStream(fis);
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer,0,i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            os.close();
            bis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
