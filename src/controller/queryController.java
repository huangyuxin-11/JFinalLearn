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

        String fileName =getPara("fileName");
        String fileContent =getPara("fileContent");
        JSONObject result =new JSONObject();
        esSearchService essearchService =new esSearchService();
        try {
            result = essearchService.fileSearch(fileName,fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        if( result == null || result.size() =0)
        renderJson(result);

    }
}
