package service;

import fileparse.FileTypeUtils;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class tikaService {

    public static String parse(String filePath) throws Exception{
        return parse(filePath,10*1024*1024);
    }

    public static String parse(String filePath,int limit) throws Exception{
        File file=new File(filePath);
        if(!file.exists()){
            System.out.println("目标文件不存在！");
            return null;
        }
        BodyContentHandler handler=null;
        if(limit>10*1024*1024) {
            handler = new BodyContentHandler(limit);
        }else{
            handler = new BodyContentHandler(10 * 1024 * 1024);
        }
        Metadata meta=new Metadata();
        FileInputStream input=new FileInputStream(file);
        ParseContext context=new ParseContext();
        new AutoDetectParser().parse(input,handler,meta,context);
        return handler.toString();
    }

    public static String fileType(String pathName){
        //assume example.mp3 is in your current directory
        File file = new File(pathName);//

        //Instantiating tika facade class
        Tika tika = new Tika();

        //detecting the file type using detect method
        String filetype = null;
        try {
            filetype = tika.detect(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String type;
        FileTypeUtils fileTypeUtils =new FileTypeUtils();
        type = FileTypeUtils.getFileType(filetype);
        System.out.println(type);
        System.out.println(filetype);

        return type;

    }
}
