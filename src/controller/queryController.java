package controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import model.FileUtil;
import service.esSearchService;

import java.io.IOException;

public class queryController extends Controller {
//    public void index() {
//        render("fileQuery.html");
//    }
//
    public void fileQuery(){

        int from =0, size=10;
        from =  Integer.parseInt(getPara("from"));
        size = Integer.parseInt(getPara("size"));
        String fileName =getPara("fileName");
        String fileContent =getPara("fileContent");
        JSONObject result =new JSONObject();
        esSearchService essearchService =new esSearchService();
        try {
            result = essearchService.fileSearch(fileName,fileContent,size,from);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        if( result == null || result.size() =0)
        renderJson(result);

    }
}
