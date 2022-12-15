package com.asecl.simdc.org.simdc_project.ftp;

import com.asecl.simdc.org.simdc_project.exception.QLException;
import org.apache.commons.net.ftp.FTPClient;

import org.springframework.stereotype.Service;

import java.io.*;


@Service
public class FTPUploadUtils {

    /**
     * 文件上传
     *
     * @param remotePath     上传到远程的文件夹路径
     * @param originFileName 文件名
     * @param inputStream    文件流
     * @return
     */
    public void uploadFile(String remotePath, String originFileName, InputStream inputStream) throws IOException {
        //连接FTP
        FTPClient ftpClient = FTPService.createFtpConnection();
        try {
            // 设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //创建文件夹，如果有的话 就不会创建，返回false
            ftpClient.makeDirectory(remotePath);
            // 设置上传目录
            ftpClient.changeWorkingDirectory(remotePath);
            //文件转移时候的一次性读取大小
            ftpClient.setBufferSize(2048);
            //将流写到服务器
            ftpClient.storeFile(originFileName, inputStream);
            inputStream.close();
            //ftp服务退出
            ftpClient.logout();
        } catch (IOException e) {
            throw new QLException(e);
        } finally {
            if (ftpClient != null) {
                FTPService.ftpConnectionClose(ftpClient);
            }
        }
    }


    /**
     * 下载文件
     *
     * @param remotePath          ftp上文件存放的地址 如:  /img/logo.png
//     * @param localPath：本地存放文件的地址 如：E:\images\logo.png
     */
    public InputStream getFileByFtp(String remotePath) throws IOException {
        InputStream is = null ;
        FTPClient ftpClient = FTPService.createFtpConnection();
        is = (InputStream) ftpClient.retrieveFileStream(remotePath);

        if (ftpClient != null) {
            FTPService.ftpConnectionClose(ftpClient);
        }

        return is;
//        try {
//
//
//
//
//        } catch (FileNotFoundException e) {
//            throw new QLException(e);
//        } catch (IOException e) {
//            throw new QLException(e);
//        } finally {
//            if (ftpClient != null) {
//                FTPService.ftpConnectionClose(ftpClient);
//            }
//        }
    }
   
}
