package com.lee.test;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author KitenLee
 * * @date 2026/2/28
 */
public class ScoketServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(1025);
            System.out.println("服务已经启动成功");
            Socket socket = serverSocket.accept();
            String ip = socket.getInetAddress().getHostAddress();
            System.out.println("客户端IP已连接： "+ip+" 端口： "+socket.getPort());


        }catch (Exception e){
            System.out.println(e);
        }
    }
}
