package com.webserver.core;

import com.webserver.http.HttpServletRequest;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

public class ClientHandler implements Runnable{

    /**
     * Socket工作过程包含以下四个步骤；
     * 1. 创建Socket;
     * 2. 打开链接到Socket的输入/输出流；
     * 3. 按照一定的协议对Socket 进行读写操作；
     * 4. 关闭Socket
     *
     * Socket 类组合要包括两个： Socket（客户端） 和 ServerSocket(服务器端）
     */
    private Socket socket;

    public ClientHandler(Socket socket) { //构造方法

        this.socket = socket;
    }
    //Commencal Ramones 20
    public void run(){

       try {
           //1. 解析请求
            HttpServletRequest request = new HttpServletRequest(socket);
             String method = request.getMethod();

           out.println("获取方法："+ method);

          String header = request.getHeaders("host");
           out.println(header);
           //2.处理请求

           /**
            * 和后期开发中常用的相对路径：类加载路径（）
            * 在Maven 项目中将我们项目编译后会把/src/main/java中的全部内容哦全部编译并且
            * 和src/main/resources下的内容合并存放在target/classes中。
            * 因此target/classses目录就是类加载路径
            *
            * 定位类加载路径，固定写法：
            * 当前项目中的类的类名（通常在那个类中需要访问类加载路径就用哪个类）
            * 类名.class.getClassLoader().getResource(".")
            */
            //类加载路径：踏入歌坛/classes
           File root = null;

               root = new File(
                       ClientHandler.class.getClassLoader().getResource(".").toURI()
               );


           //定位target/class.static目录（通常在哪个类中需要访问类架站路径就用哪个类）
               File staticDir = new File(root,"static");

               //
               File file = new File(staticDir, "index.html");


           //3. 发送响应

           OutputStream out = socket.getOutputStream();

           //3.1 发送状态行
           String line = "HTTP/1.1 200 OK";
           byte[] data = line.getBytes(StandardCharsets.ISO_8859_1);
           out.write(data);
           out.write(13);
           out.write(10);

           //3.2 发送响应头
           line = "Content-Type:text/html";
           data=line.getBytes(StandardCharsets.ISO_8859_1);
           out.write(data);
           out.write(13);
           out.write(10);

           line = "Content-Length: " + file.length();
           data= line.getBytes(StandardCharsets.ISO_8859_1);
           out.write(data);
           out.write(13);
           out.write(10);

           out.write(13); //发送回车符
           out.write(10); //发送换行符


           //3.3发送响应正文（file 表示的文件内容）
           FileInputStream fis = new FileInputStream(file);
           FileOutputStream fos = new FileOutputStream("xxx.xx");

           int len; //每次读取的字节量

           byte[] buf = new byte[1024*10]; //10kb


           while((len=fis.read(buf))!=-1) {
               fos.write(buf, 0, len);
               out.write(buf,0,len);

           }



       } catch (IOException e) {
           e.printStackTrace();

       } catch (URISyntaxException e) {
           e.printStackTrace();
       }  finally {
           try {
               //一问一答后断开TCP 连接
               socket.close();        //关闭Socket
           }  catch (IOException e){
               e.printStackTrace();
           }
       }

    }



}
