package fileparse;


import java.util.HashMap;
import java.util.Map;

public class FileTypeUtils {
    private final static Map<String, String> FILE_TYPE_MAP = new HashMap<>();

    public FileTypeUtils(){
        FILE_TYPE_MAP.put("application/msword","doc");
        FILE_TYPE_MAP.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document","docx");
        FILE_TYPE_MAP.put("application/vnd.ms-excel","xls");
        FILE_TYPE_MAP.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","xlsx");
        FILE_TYPE_MAP.put("application/vnd.ms-powerpoint","ppt");
        FILE_TYPE_MAP.put("application/vnd.openxmlformats-officedocument.presentationml.presentation","pptx");
        FILE_TYPE_MAP.put("text/plain","txt");
        FILE_TYPE_MAP.put("application/pdf","pdf");

    }

    public static String getFileType(String mimeType){
        String type =FILE_TYPE_MAP.get(mimeType);
        return type;
    }
}
