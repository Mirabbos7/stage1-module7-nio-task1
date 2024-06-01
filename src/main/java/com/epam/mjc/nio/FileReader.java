package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {
    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        Profile profile = fileReader.getDataFromFile(new File("src/main/resources/Profile.txt"));
        System.out.println(profile);
    }

    public Profile getDataFromFile(File file) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            FileChannel inChanel = randomAccessFile.getChannel();
            {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                while (inChanel.read(byteBuffer) > 0) {
                    byteBuffer.flip();
                    for (int i = 0; i < byteBuffer.limit(); i++) {
                        System.out.print((char) byteBuffer.get());
                    }
                    byteBuffer.clear();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Profile();
    }
}
