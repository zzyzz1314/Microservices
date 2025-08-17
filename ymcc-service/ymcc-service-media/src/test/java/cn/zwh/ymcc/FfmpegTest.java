package cn.zwh.ymcc;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FfmpegTest {
    @Test
    public void test() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        List<String> commond = new ArrayList<>();
        commond.add("D:\\develop\\headend\\ffmpeg\\bin\\ffmpeg");
        commond.add("-i");
        commond.add("D:\\9\\8\\983234c4bccd832b3664449f03d7d720\\983234c4bccd832b3664449f03d7d720.mp4");
        commond.add("-hls_time");
        commond.add("10");
        commond.add("-hls_list_size");
        commond.add("0");
        commond.add("-hls_segment_filename");
        commond.add("D:\\9\\8\\983234c4bccd832b3664449f03d7d720\\xy\\habor%05d.ts");
        commond.add("D:\\9\\8\\983234c4bccd832b3664449f03d7d720\\xy\\habor.m3u8");
        processBuilder.command(commond);
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try {
            //启动进程
            Process start = processBuilder.start();
            //获取输入流
            InputStream inputStream = start.getInputStream();
            //转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            int len = -1;
            char[] c = new char[1024];
            StringBuffer outputString = new StringBuffer();
            //读取进程输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                outputString.append(s);
                System.out.print(s);
            }
            inputStream.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
