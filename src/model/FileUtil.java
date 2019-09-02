package model;

import java.util.Date;

public class FileUtil {

    private String group;
    private String path;
    private String filename;
    private String filetype;
    private String content;
    private String timestamp;

    public FileUtil(String tgroup, String tpath, String tfilename, String tfiletype, String tcontent ){
        setGroup(tgroup);
        setPath(tpath);
        setFilename(tfilename);
        setFiletype(tfiletype);
        setContent(tcontent);
//        setTimestamp(ttimestamp);

    }
    public FileUtil(){}

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
