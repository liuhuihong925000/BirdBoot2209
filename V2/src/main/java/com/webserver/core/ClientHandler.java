package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket) {

        this.socket = socket;
    }

    public void run(){
       try {
           //1. 解析请求
           //1.1 读取请求行
           //测试读取一行字符串的操作
        InputStream in = socket.getInputStream();
        StringBuilder builder = new StringBuilder(); //
        char cur = 'a', pre='a'; // cur 记录本次读取的字符，pre 记录上次读取的字符
        int d; //每次读取的字字节

        while ((d=in.read())!=-1) {
            cur = (char)d;   //將本次讀取的字節转换为char 记录在cur 上
            if (pre ==13&& cur==10){ //如果上次读取的为回车符，本次读取的是换行符
               break; //停止读取（一行结束了）
           }
            builder.append(cur); //将本次读取的字符拼接到一家读取的字符串中
            pre = cur;// 在喜爱读取前将本次读取的字符记作“上次读取的字符”

        }

        String line = builder. toString().trim();
           System.out.println("请求行：" + line);

           //将请求行当内容空格拆分为三个部分，并赋值给下面三个变量
           String method;
           String uri;
           String protocol;


           String[] data = line.split(" ");
           method = data[0];
           uri = data[1];
           protocol=data[2];

           System.out.println(method);
           System.out.println(uri);
           System.out.println(protocol);
           //2.处理请求

           //3.



       } catch (IOException e) {
           e.printStackTrace();
       }  finally {
           try {
               //一问一答后断开TCP 连接
               socket.close();
           }  catch (IOException e){
               e.printStackTrace();
           }
       }

    };

}
