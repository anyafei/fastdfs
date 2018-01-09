package star.demo.fastdfs.fdfs;

import java.io.File;
import java.io.IOException;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class FdfsClient {

    private static final long serialVersionUID = 1L;

    private static final String CLIENT_CONFIG_FILE = "fdfs/fdfs_client.conf";

    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;
    private static StorageClient storageClient;

    static {
        try {
            String classPath = new File(FdfsClient.class.getResource("/").getFile()).getCanonicalPath();
            String fdfsClientConfigFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;
            ClientGlobal.init(fdfsClientConfigFilePath);
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageClient = new StorageClient(trackerServer, storageServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <strong>方法概要： 文件上传</strong> <br>
     * <strong>创建时间： 2016-9-26 上午10:26:11</strong> <br>
     *
     * @param fileContent
     *            file
     * @return Url
     */
    public static String upload(byte[] fileContent, String extName, NameValuePair[] valuePairs) {
        String[] uploadResults = null;
        try {
            uploadResults = storageClient.upload_file(fileContent,extName, valuePairs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];

        String Url = "/" + groupName + "/" + remoteFileName;
        return Url;
    }

}
