package controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import intercept.DemoInterceptor;
import model.FileUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import service.FilesService;
import service.esSearchService;
import service.tikaService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class uploadController extends Controller {
    static private FilesService filesService =new FilesService();

    public void index() {
        render("upload.html");
    }

    @Before(DemoInterceptor.class)
    public void uploadImage() {

        UploadFile upFile = getFile();

        File f = upFile.getFile();
        String basepath = PathKit.getWebRootPath();
        String source = basepath + File.separator + "upload" + File.separator
                + "uploadFile" + File.separator + "images" + File.separator;

        String myFileName = f.getName(); //取得上载的文件的文件名

        String infile = myFileName;
        String outfile = myFileName.replace(".", "_s.");

        String inFilePath = source + infile;
        String outFilePath = source + outfile;

        f.renameTo(new File(inFilePath));
        try {
            test1(inFilePath, outFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setAttr("png1", "/upload/uploadfile/images/" + infile);
        setAttr("png2", "/upload/uploadfile/images/" + outfile);
        render("/upload/afterUpload.html");

    }

    public void uploadFile() throws Exception {

        UploadFile upFile = getFile();
        File f = upFile.getFile();



        String basepath = PathKit.getWebRootPath();
        String source = basepath + File.separator + "upload" + File.separator;
        //取得上载的文件的文件名
        String uploadFileName = f.getName();
        String caselsh = uploadFileName.substring(0,uploadFileName.lastIndexOf("."));
        String uploadFilePath = source + uploadFileName;
        //tika解析文件内容
        String uploadFilecontent = tikaService.parse(uploadFilePath);
        System.out.println(uploadFilecontent);
        //tika解析文件类型
        String uploadFileType = tikaService.fileType(uploadFilePath);
        System.out.println(uploadFileType);
        //假装有入库时间，实际上入库时间在进行insert时再取


        //test 以下面为准
        String DFSGroup="tt";
        String DFSPath="tt";

        try {
            filesService.fdfsUpload(uploadFilePath, uploadFileType);
            DFSGroup = filesService.getGroup();
            DFSPath = filesService.getPath();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
//        上传完，应断开连接
        filesService.fdsfClose();

        esSearchService essearchService =new esSearchService();
        FileUtil fileUtil =new FileUtil(DFSGroup, DFSPath, caselsh, uploadFileType, uploadFilecontent);
        essearchService.insert(fileUtil);
        System.out.println("入es库完成");


        f.delete();
        render("upload.html");

    }

    public void downloadFile() throws UnsupportedEncodingException {
        //应取得下载地址参数，这里临时设置在service里面

        String dfsGroup = getPara("dfsGroup");
        String dfsPath = getPara("dfsPath");
        String dfsName = getPara("dfsName");
        String dfsType = getPara("dfsType");
        String dfsStore =dfsGroup+dfsPath;
        System.out.println("dfsStore:"+dfsStore);

        HttpServletResponse response =getResponse();


        filesService.fdfsDownload(response, dfsGroup, dfsPath, dfsType, URLEncoder.encode(dfsName, "UTF-8"));
//        filesService.fdfsDownload(response, dfsGroup, dfsPath, dfsType, dfsName);


        //下载完，应断开连接
        filesService.fdsfClose();


    }

    public void deleteFile(){
        //应取得目标地址参数

        String dfsGroup = getPara("dfsGroup");
        String dfsPath = getPara("dfsPath");
        String dfsStore =dfsGroup+dfsPath;
        System.out.println("dfsStore:"+dfsStore);
        //删除dfs文件
        try {
            filesService.fdfsDelete(dfsGroup, dfsPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        //操作完，应断开连接
        filesService.fdsfClose();

        //删除es记录


    }


    private void test1(String infile, String outfile) throws IOException {

        Thumbnails.of(infile).size(200, 300).toFile(outfile);

    }


}