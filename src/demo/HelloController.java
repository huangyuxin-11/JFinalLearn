package demo;

import com.jfinal.core.Controller;
import test.TestFastDfs;

public class HelloController extends Controller {

    public void index(){

        TestFastDfs testFastDfs = new TestFastDfs();
//        testFastDfs.testUpload();
//        testFastDfs.testDownload();
//        testFastDfs.testGetFileInfo();
      testFastDfs.testDelete();
        renderText("Hello JFinal World.");
    }

}
