package demo;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.template.Engine;
import controller.queryController;
import controller.uploadController;
import index.indexController;
import test.TestFastDfs;

public class DemoConfig extends JFinalConfig{

    public static void main(String[] args){
//        TestFastDfs testFastDfs =new TestFastDfs();
//        testFastDfs.testUpload();

//        testFastDfs.testDownload();
        JFinal.start("WebRoot", 80, "/");

    }


    @Override
    public void configConstant(Constants me) {

        me.setDevMode(true);
//        me.setBaseUploadPath("F:\\JfinalLearn\\web\\upload\\uploadfile\\images");// 配置文件上传路径
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", indexController.class,"/index");
        me.add("/upload", uploadController.class);
        me.add("/query", queryController.class);
        me.add("/hello", HelloController.class);
    }

    @Override
    public void configEngine(Engine me) {

    }

    @Override
    public void configPlugin(Plugins me) {

    }

    @Override
    public void configInterceptor(Interceptors me) {


    }

    @Override
    public void configHandler(Handlers me) {

    }
}
