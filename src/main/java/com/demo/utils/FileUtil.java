package com.demo.utils;

import com.demo.entity.Consumer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
    Consumer consumer = new Consumer();
    public  void readBio(String file) {
        ObjectOutputStream objectOutputStream=null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeBio(String file) {
        ObjectInputStream objectInputStream=null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            consumer=(Consumer) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readNio() {
        String pathName = "";
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(pathName));
            FileChannel fileChannel = fileInputStream.getChannel();
            int capacity=100;
            ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
            int length=-1;
            while ((length = fileChannel.read(byteBuffer)) != -1) {
                byteBuffer.clear();
                byte[] bytes = byteBuffer.array();
                System.out.write(bytes,0,length);
                System.out.println();
            }
            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeNio() {
        String fileName = "";
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream((new File(fileName)));
            FileChannel fileChannel = fileOutputStream.getChannel();
            ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode("你好");
            int length=0;
            while ((length = fileChannel.write(byteBuffer)) != 0) {
                System.out.println("写入长度："+length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void readAio() {
        Path path = Paths.get("");
        try {
            AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path);
            ByteBuffer byteBuffer = ByteBuffer.allocate(100_000);
            Future<Integer> future = asynchronousFileChannel.read(byteBuffer, 0);
            while (!future.isDone()) {
                ProfitCalculator profitCalculator = new ProfitCalculator();
                profitCalculator.calculateTax();
            }
            Integer integer = future.get();
            System.out.println("Bytes read ["+integer+"]");
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    class ProfitCalculator {
        public ProfitCalculator(){}
        public void calculateTax(){}
    }

    public void writeAio() {
        AsynchronousFileChannel asynchronousFileChannel = null;
        CompletionHandler<Integer, Object> completionHandler = null;
        try {
            asynchronousFileChannel = AsynchronousFileChannel.open(Paths.get(""), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            completionHandler=new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer result, Object attachment) {
                    System.out.println("Attachment:"+ attachment+" "+ result+" bytes written");
                    System.out.println("CompletionHandler Thread ID:"+ Thread.currentThread().getId());
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.err.println("Attachment:"+ attachment+" failed with:");
                    exc.printStackTrace();
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Main Thread ID:"+ Thread.currentThread().getId());
        asynchronousFileChannel.write(ByteBuffer.wrap("Sample".getBytes()), 0,"First Write", completionHandler);
        asynchronousFileChannel.write(ByteBuffer.wrap("Bos".getBytes()), 0, "Second Write",completionHandler);
    }


}
