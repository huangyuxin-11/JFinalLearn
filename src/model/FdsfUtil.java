package model;

import org.csource.fastdfs.*;

import java.io.IOException;

public class FdsfUtil {

    private static TrackerServer trackerServer =null;
    private static TrackerClient trackerClient =null;
    private static StorageServer storageServer = null;
    private static StorageClient storageClient = null;

    public static void init(){
        try {
            ClientGlobal.init("fdfs_client.conf");
            TrackerClient storageClient = new TrackerClient();
            trackerServer = storageClient.getConnection();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static TrackerServer getTrackerServer() {
        return trackerServer;
    }

    public static void setTrackerServer(TrackerServer trackerServer) {
        FdsfUtil.trackerServer = trackerServer;
    }

    public static TrackerClient getTrackerClient() {
        return trackerClient;
    }

    public static void setTrackerClient(TrackerClient trackerClient) {
        FdsfUtil.trackerClient = trackerClient;
    }

    public static StorageServer getStorageServer() {
        return storageServer;
    }

    public static void setStorageServer(StorageServer storageServer) {
        FdsfUtil.storageServer = storageServer;
    }

    public static StorageClient getStorageClient() {
        return storageClient;
    }

    public static void setStorageClient(StorageClient storageClient) {
        FdsfUtil.storageClient = storageClient;
    }
}
