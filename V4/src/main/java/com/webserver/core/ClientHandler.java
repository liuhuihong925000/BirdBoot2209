package com.webserver.core;

import com.webserver.http.HttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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

           System.out.println("获取方法："+ method);

          String header = request.getHeaders("host");
           System.out.println(header);
           //2.处理请求

           //3.



       } catch (IOException e) {
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


    ;

    /**
     * 读取客户端发过来的一行字符串
     * @return
     */


}
