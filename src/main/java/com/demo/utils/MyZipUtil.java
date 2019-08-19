package com.demo.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MyZipUtil {
    //使用buffer压缩
    public static void zipFileBuffer() {
        String ZIP_FILE = "";
        File zipFile = new File(ZIP_FILE);
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOut)) {
            //开始时间
            long beginTime = System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                File JPG_FILE = new File("");
                try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(JPG_FILE))) {
                    String FILE_NAME = JPG_FILE.getName();
                    zipOut.putNextEntry(new ZipEntry(FILE_NAME + i));
                    int temp = 0;
                    while ((temp = bufferedInputStream.read()) != -1) {
                        bufferedOutputStream.write(temp);
                    }
                }
            }
//            System.out.println(beginTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //使用NIO channel
    public static void zipFileChannel() {
        //开始时间
        long beginTime = System.currentTimeMillis();
        String ZIP_FILE = "";
        File zipFile = new File(ZIP_FILE);
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut)) {
            for (int i = 0; i < 10; i++) {
                File JPG_FILE = new File("");
                try (FileChannel fileChannel = new FileInputStream(JPG_FILE).getChannel()) {
                    String SUFFIX_FILE = JPG_FILE.getName();
                    zipOut.putNextEntry(new ZipEntry(i + SUFFIX_FILE));
                    long FILE_SIZE = JPG_FILE.length();
                    fileChannel.transferTo(0, FILE_SIZE, writableByteChannel);
                }
            }
//            printInfo(beginTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //使用NIO map映射文件
    public static void zipFileMap() {
        //开始时间
        long beginTime = System.currentTimeMillis();
        String ZIP_FILE = "";
        File zipFile = new File(ZIP_FILE);
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOut)) {
            for (int i = 0; i < 10; i++) {
                String SUFFIX_FILE = "";
                zipOut.putNextEntry(new ZipEntry(i + SUFFIX_FILE));

                //内存中的映射文件
                String JPG_FILE_PATH = "";
                File file = new File(JPG_FILE_PATH);
                long FILE_SIZE = file.length();
                MappedByteBuffer mappedByteBuffer = new RandomAccessFile(JPG_FILE_PATH, "r").getChannel()
                        .map(FileChannel.MapMode.READ_ONLY, 0, FILE_SIZE);

                writableByteChannel.write(mappedByteBuffer);
            }
//            printInfo(beginTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipFilePip() {

        long beginTime = System.currentTimeMillis();
        File ZIP_FILE = new File("");
        try(WritableByteChannel out = Channels.newChannel(new FileOutputStream(ZIP_FILE))) {
            Pipe pipe = Pipe.open();
            //异步任务
            CompletableFuture.runAsync(()->runTask(pipe));

            //获取读通道
            ReadableByteChannel readableByteChannel = pipe.source();
            long FILE_SIZE = ZIP_FILE.length();
            ByteBuffer buffer = ByteBuffer.allocate(((int) FILE_SIZE)*10);
            while (readableByteChannel.read(buffer)>= 0) {
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        printInfo(beginTime);

    }

    //异步任务
    public static void runTask(Pipe pipe) {

        try(ZipOutputStream zos = new ZipOutputStream(Channels.newOutputStream(pipe.sink()));
            WritableByteChannel out = Channels.newChannel(zos)) {
            System.out.println("Begin");
            for (int i = 0; i < 10; i++) {
                String SUFFIX_FILE = "";
                zos.putNextEntry(new ZipEntry(i+SUFFIX_FILE));
                String JPG_FILE_PATH = "";
                FileChannel jpgChannel = new FileInputStream(new File(JPG_FILE_PATH)).getChannel();
                long FILE_SIZE = new File(JPG_FILE_PATH).length();
                jpgChannel.transferTo(0, FILE_SIZE, out);

                jpgChannel.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
