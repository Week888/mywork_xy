import net.sf.json.JSONObject;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class Soc implements Runnable {  //处理接收的消息


    private Socket socket;

    public Soc(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            handlerSocket();
        } catch (Exception e) {
            System.out.println(TextConstant.failedConncetion);
        }

    }

    private void handlerSocket() throws Exception {
        //根据输入输出流和客户端连接
        InputStream inputStream = socket.getInputStream();//得到一个输入流，接收客户端传递的信息
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);//提高效率，将自己字节流转为字符流
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//加入缓冲区
        String temp = null;
        String info = "";
        while ((temp = bufferedReader.readLine()) != null) {
            info += temp;
            System.out.println("new client message :" + info + " , ip：" + socket.getInetAddress().getHostAddress()+" : "+socket.getPort());
        }
        OutputStream outputStream = socket.getOutputStream();//获取一个输出流，向服务端发送信息
        PrintWriter printWriter = new PrintWriter(outputStream);//将输出流包装成打印流
        printWriter.print(backJson(info));
        printWriter.flush();
        socket.shutdownOutput();//关闭输出流

        //关闭相对应的资源
        printWriter.close();
        outputStream.close();
        bufferedReader.close();
        inputStream.close();
        socket.close();


    }
    public String backJson(String jsonText){   //解析传进来的JSON字段并处理
        JSONObject object = JSONObject.fromObject(jsonText);
        TextHandler handler = new TextHandler(object.getString("text"),object.getInt("width"));
        return handler.handle();
    }
}

