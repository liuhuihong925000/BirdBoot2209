package com.webserver.core;

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

    public ClientHandler(Socket socket) {

        this.socket = socket;
    }
    //Commencal Ramones 20
    public void run(){
       try {
           //1. 解析请求

           //1.1 读取请求行
           String line = readLine();
          // System.out.println( "请求行：" + line);
           //测试读取一行字符串的操作
            System.out.println("请求行：" + line);
           //将请求行当内容空格拆分为三个部分，并赋值给下面三个变量
           String method;
           String uri;
           String protocol;


           String[] data = line.split("\\s");
           method = data[0];
           uri = data[1];
           protocol=data[2];

           System.out.println("method"+ method);
           System.out.println("uri"+uri);
           System.out.println("protocol"+ protocol);


           //1.2 解析消息头：

           Map<String,String> headers = new HashMap<>();
           while(true) {
               line = readLine();
               if(line.isEmpty()) {  //若读取到了空行，则说明消息头部分读取完毕
                   break;
               }
               System.out.println("消息头： "+line);

               //每個消息頭都按照“:"(冒號空格)拆分為消息頭的名字和值并以key,value存入headers
            data = line.split(":\\s");
            //將消息頭存入Map時，消息頭的名字轉換爲小寫
            headers.put(data[0].toLowerCase(),data[1]);


           }
           System.out.println("所有消息頭： "+ headers);
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

    private String readLine() throws IOException {   //被重用的代码对应的方法通常不自己处理

        InputStream in = socket.getInputStream();  //从socket 建立输入流
        StringBuilder builder = new StringBuilder(); //保存拼接一行内容
        char cur = 'a', pre='a'; // 局部变量，无默认初始值，使用前先赋值。 cur 记录本次读取的字符，pre 记录上次读取的字符
        int d; //每次读取的字字节

        while ((d=in.read())!=-1) {
            cur = (char)d;   //將本次讀取的字節转换为char 记录在cur 上
            if (pre ==13&& cur==10){ //如果上次读取的为回车符13，本次读取的是换行符10
                break; //停止读取（一行结束了）
            }
            builder.append(cur); //将本次读取的字符拼接到一家读取的字符串中
            pre = cur;// 在读取前将本次读取的字符记作“上次读取的字符”

        }

           return builder. toString().trim();  //删除字符串头尾的空白字符
    }
}
