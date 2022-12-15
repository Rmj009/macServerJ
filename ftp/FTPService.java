package com.asecl.simdc.org.simdc_project.ftp;

import com.asecl.simdc.org.simdc_project.exception.QLException;
import lombok.Data;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;
import java.io.*;


@Service
@Data
public class FTPService {

    /**
     * FTP ip地址
     */
    private static String address = "127.0.0.1";
    /**
     * FTP 端口号
     */
    private static int port = 21;
    /**
     * FTP 设置字符集
     */
    private static String charset = "GBK";
    /**
     * FTP 用户名
     */
    private static String username = "root";
    /**
     * FTP 密码
     */
    private static String password = "123456";

    /**
     * ftpClient
     */
    private static FTPClient ftpClient;


    /**
     * 新建FTP连接
     *
     * @return
     */
    synchronized public static FTPClient createFtpConnection() {
        if (ftpClient == null || !ftpClient.isConnected()) {
            try {
                ftpClient = new FTPClient();
                //连接IP 如果port【端口】存在的话
                ftpClient.connect(address, port);
                //设置编码类型
                ftpClient.setControlEncoding(charset);
                //每次数据连接之前，ftpClient告诉ftp server开通一个端口来传输数据
                ftpClient.enterLocalPassiveMode();
                //登录
                ftpClient.login(username, password);
                //连接尝试后，应检查回复代码以验证
                int reply = ftpClient.getReplyCode();
                //没验证成功
                if (!FTPReply.isPositiveCompletion(reply)) {
                    //断开ftp连接
                    ftpClient.disconnect();
                    return null;
                }
                System.out.println("The ftp server is successfully connected.");
            } catch (Exception e) {
                throw new QLException(e);
            }
        }
        return ftpClient;
    }

    /**
     * 关闭FTP连接 ftpClose
     */
    public static void ftpConnectionClose(FTPClient ftpClient) {
        if (ftpClient != null) {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                    System.out.println("Ftp connection closed...");
                } catch (IOException e) {
                    throw new QLException(e);
                }
            }
        }
    }

}
