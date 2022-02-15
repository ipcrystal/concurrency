package org.iproute.http;

/**
 * Main
 *
 * @author winterfell
 * @since 2022/2/15
 */
public class Main {
    public static void main(String[] args) {
        try {
            SimpleHttpServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
