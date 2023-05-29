package org.iproute.threadpool;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 一个基于线程池技术的简单的http 服务器
 *
 * @author winterfell
 * @since 2022/2/15
 */
public class SimpleHttpServer {

    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<>(1);

    /**
     * simple http server 的根路径
     */
    static String basePath;
    static ServerSocket serverSocket;
    static int port = 8080;

    public static void setBasePath(String basePath) {
        if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {
            SimpleHttpServer.basePath = basePath;
        }
    }

    public static void start() throws Exception {
        serverSocket = new ServerSocket(port);
        Socket socket = null;

        while ((socket = serverSocket.accept()) != null) {
            // 接收一个客户端生成一个HttpRequestHandler并放入线程中执行
            threadPool.execute(new HttpRequestHandler(socket));
        }

        serverSocket.close();
    }


    static class HttpRequestHandler implements Runnable {
        private final Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String line = null;

            BufferedReader br = null;
            BufferedReader reader = null;
            PrintWriter out = null;
            InputStream in = null;

            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String header = reader.readLine();
                // 由相对路径计算出绝对路径
                String filePath = basePath + header.split(" ")[1];
                out = new PrintWriter(socket.getOutputStream());

                // 如果请求的资源的后缀会.jpg活着.ico
                if (filePath.endsWith(".jpg") || filePath.endsWith(".ico")) {
                    in = Files.newInputStream(Paths.get(filePath));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i;
                    while ((i = in.read()) != -1) {
                        baos.write(i);
                    }
                    byte[] array = baos.toByteArray();

                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: image/jpeg");
                    out.println("Content-Length: " + array.length);
                    out.println("");

                    socket.getOutputStream().write(array, 0, array.length);
                } else {
                    /*
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                    out = new PrintWriter(socket.getOutputStream());
                    */

                    String word = "Hello World";

                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: text/plain");
                    out.println("Content-Length: " + word.length());
                    out.println("");
                    out.println(word);
                }
                out.flush();
            } catch (Exception e) {
                assert out != null;
                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();
            } finally {
                close(br, in, reader, out, socket);
            }
        }

        private static void close(Closeable... closeables) {
            if (closeables != null) {
                for (Closeable closeable : closeables) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        try {
            SimpleHttpServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
