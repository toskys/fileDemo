package com.demo.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReadStreamUtil {

    //普通输入流
    public static void inputStream(Path filename) {
        try (InputStream is = Files.newInputStream(filename)) {
            int c;
            while((c = is.read()) != -1) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //带缓冲的输入流
    public static void bufferedInputStream(Path filename) {
        try (InputStream is = new BufferedInputStream(Files.newInputStream(filename))) {
            int c;
            while((c = is.read()) != -1) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //随机访问文件
    public static void randomAccessFile(Path filename) {
        try (RandomAccessFile randomAccessFile  = new RandomAccessFile(filename.toFile(), "r")) {
            for (long i = 0; i < randomAccessFile.length(); i++) {
                randomAccessFile.seek(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //内存映射文件
    public static void mappedFile(Path filename) {
        try (FileChannel fileChannel = FileChannel.open(filename)) {
            long size = fileChannel.size();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);
            for (int i = 0; i < size; i++) {
                mappedByteBuffer.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
