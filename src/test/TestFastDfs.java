package test;

import com.jfinal.kit.PathKit;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TestFastDfs {


    //fdfs_client 核心配置文件
    public static String conf_filename = "fdfs_client.conf";


    public static void testUpload() {	//上传文件
        TrackerServer trackerServer =null;
        StorageServer storageServer = null;

        System.out.println("start connect");
        try {
            ClientGlobal.init(conf_filename);
            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);

            //要上传的文件路径
            String basepath = PathKit.getWebRootPath();
            String source = basepath + File.separator + "upload" + File.separator
                    + "uploadFile" + File.separator + "images" + File.separator;
            String local_filename = source + "test.png";
//            这个参数可以指定，也可以不指定，如果指定了，可以根据 testGetFileMate()方法来获取到这里面的值
//            NameValuePair nvp [] = new NameValuePair[]{
//                    new NameValuePair("age", "18"),
//                    new NameValuePair("sex", "male")
//            };

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//          String fileIds[] = storageClient.upload_file(local_filename, "png", nvp);
            String fileIds[] = storageClient.upload_file(local_filename, "png", null);

            System.out.println(fileIds.length);
            System.out.println("组名：" + fileIds[0]);
            System.out.println("路径: " + fileIds[1]);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(null!=storageServer) storageServer.close();
                if(null!=trackerServer) trackerServer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public void testDownload() {	//下载文件
        TrackerServer trackerServer =null;
        StorageServer storageServer = null;

        try {
            String groupName = "group1";
            String filePath = "M00/00/31/ChQBNF1LuJSASqjHAAJ4GoSY2Ug171.png";
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            byte[] bytes = storageClient.download_file(groupName, filePath);

            String storePath = "C:\\Users\\Administrator\\Desktop\\download.png";
            OutputStream out = new FileOutputStream(storePath);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try{
                if(null!=storageServer) storageServer.close();
                if(null!=trackerServer) trackerServer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public void testGetFileInfo(){ //获取文件信息
        TrackerServer trackerServer =null;
        StorageServer storageServer = null;

        try {
            String groupName = "group1";
            String filePath = "M00/00/00/ChQBNF1LuJSASqjHAAJ4GoSY2Ug171.png";
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            FileInfo file = storageClient.get_file_info(groupName, filePath);
            System.out.println("ip--->"+file.getSourceIpAddr());
            System.out.println("文件大小--->"+file.getFileSize());
            System.out.println("文件上传时间--->"+file.getCreateTimestamp());
            System.out.println(file.getCrc32());
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(null!=storageServer) storageServer.close();
                if(null!=trackerServer) trackerServer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public void testGetFileMate(){ //获取文件的原数据类型
        TrackerServer trackerServer =null;
        StorageServer storageServer = null;

        try {
            String groupName = "group1";
            String filePath = "M00/00/00/ZGIW_lpujW-ADvpRAAblmT4ACuo125.png";
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();

            StorageClient storageClient = new StorageClient(trackerServer,
                    storageServer);

            //这个值是上传的时候指定的NameValuePair
            NameValuePair nvps [] = storageClient.get_metadata(groupName, filePath);
            if(null!=nvps && nvps.length>0){
                for(NameValuePair nvp : nvps){
                    System.out.println(nvp.getName() + ":" + nvp.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(null!=storageServer) storageServer.close();
                if(null!=trackerServer) trackerServer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public void testDelete(){ //删除文件
        TrackerServer trackerServer =null;
        StorageServer storageServer = null;

        try {
            String groupName = "group1";
            String filePath = "M00/00/00/ChQBNF1LuJSASqjHAAJ4GoSY2Ug171.png";
            ClientGlobal.init(conf_filename);

            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();

            StorageClient storageClient = new StorageClient(trackerServer,
                    storageServer);
            int i = storageClient.delete_file(groupName, filePath);
            System.out.println( i==0 ? "删除成功" : "删除失败:"+i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(null!=storageServer) storageServer.close();
                if(null!=trackerServer) trackerServer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] arg){
        testUpload();
    }

}
