package service;

import model.FdsfUtil;
import org.csource.common.MyException;
import org.csource.fastdfs.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FilesService {
    String group;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    String path;

    public void fdfsUpload(String local_filename, String fileType) throws IOException, MyException {

        FdsfUtil.init();
        StorageClient storageClient = new StorageClient(FdsfUtil.getTrackerServer(), FdsfUtil.getStorageServer());
        String fileIds[] = storageClient.upload_file(local_filename, fileType, null);

        System.out.println(fileIds.length);
        System.out.println("组名：" + fileIds[0]);
        System.out.println("路径: " + fileIds[1]);
        setGroup( fileIds[0]);
        setPath( fileIds[1]);
    }

    public void fdsfClose(){
        try {
            StorageServer stoServer= FdsfUtil.getStorageServer();
            if(null!=stoServer ) stoServer.close();
            TrackerServer trackServer = FdsfUtil.getTrackerServer();
            if(null!=trackServer) trackServer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void fdfsDownload(HttpServletResponse response, String dfsGroup, String dfsPath, String dfsType, String dfsName){
//        TrackerServer trackerServer =null;
//        StorageServer storageServer = null;

        try {

            //应从前端得到参数，这里假设
//            String groupName = "group1";
//            String filePath = "M00/00/31/ChQBNF1LuJSASqjHAAJ4GoSY2Ug171.png";

            String groupName = dfsGroup;
            String filePath = dfsPath;

            System.out.println(groupName + ":::"+filePath);

            String fileName=dfsName+"."+dfsType;

            System.out.println("fileName:"+dfsName);

            FdsfUtil.init();


            StorageClient storageClient = new StorageClient(FdsfUtil.getTrackerServer(), FdsfUtil.getStorageServer());
            byte[] bytes = storageClient.download_file(groupName, filePath);


            response.setHeader("Content-disposition","attachment;filename="+fileName);

            response.setContentType("application/octet-stream;charset=utf-8");

            OutputStream out =response.getOutputStream();
            out.write(bytes);

            // 人走带门
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
}

    public void fdfsDelete(String dfsGroup, String dfsPath) throws IOException, MyException {
        String groupName = dfsGroup;
        String filePath = dfsPath;
        System.out.println(groupName + ":::"+filePath);
        FdsfUtil.init();
        StorageClient storageClient = new StorageClient(FdsfUtil.getTrackerServer(), FdsfUtil.getStorageServer());

        int i = storageClient.delete_file(groupName, filePath);
        System.out.println( i==0 ? "删除成功" : "删除失败:"+i);


    }


}
