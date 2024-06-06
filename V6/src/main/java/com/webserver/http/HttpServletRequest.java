package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求对象
 * 该类的每一个实例用于表示浏览器发送过来的一个HTTP请求
 * 一个请求由三部分构成：
 * 1.消息行
 * 2，消息头
 * 3. 消息正文
 */
public class HttpServletRequest {//构造函数
    private Socket socket;

    //请求行当相关信息
    private String method;    //请求方式
    private String uri;       // 抽象路径
    private String protocol;  //协议版本

    //消息头相关信息
    private Map<String,String> headers = new HashMap<>();  //key: 消息头名字 value:

    public HttpServletRequest() {    }  //无参构造；

    public HttpServletRequest(Socket socket) throws IOException {
        this.socket = socket;

        //1.1 读取请求行
           parseRequestLine();



        //1.2 解析消息头：
           parseHeaders();


        //1.3 解析请求消息正文
         //  parseContent();

    }


    // 解析请求行
    private void parseRequestLine() throws IOException {
        String line = readLine();
        // System.out.println( "请求行：" + line);
        //测试读取一行字符串的操作
        System.out.println("请求行：" + line);
        //将请求行当内容空格拆分为三个部分，并赋值给下面三个变量
        String[] data = line.split("\\s");
        method = data[0];
        uri = data[1];
        protocol=data[2];

        System.out.println("method: "+ method);
        System.out.println("uri: "+uri);
        System.out.println("protocol: "+ protocol);


    }

    /**
     * 读取客户端发送过来第一行字符串
     * @return
     * @throws IOException
     */



    private void parseHeaders() throws IOException {
        while(true) {
            String line = readLine();
            if(line.isEmpty()) {  //若读取到了空行，则说明消息头部分读取完毕
                break;
            }
            System.out.println("消息头： "+line);

            String[] data = line.split(":\\s");
            headers.put(data[0].toLowerCase(),data[1]);


        }
        System.out.println("所有消息頭： "+ headers);

    }


    //private String parseContent() throws IOException {
    // 被重用的代码对应的方法通常不自己处理

    //}


    private String readLine() throws IOException {

        InputStream in = socket.getInputStream();  //从socket 建立输入流
        StringBuilder builder = new StringBuilder(); //保存拼接帝一行内容
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


    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHeaders(String name) {
        name=name.toLowerCase();
        return headers.get(name);
    }
}
