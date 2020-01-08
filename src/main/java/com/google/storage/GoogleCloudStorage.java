package com.google.storage;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.log4j.Logger;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 GoogleCloudStorage class is used for uploading and downloading file from google cloud storage.
 This is for just learning purpose.
 You need to be either in GCP machines or need to have an env GOOGLE_APPLICATION_CREDENTIALS.:
 Issues found while running on GCP:
 com.google.cloud.storage.StorageException: java.lang.RuntimeException: Unexpected error: java.security.InvalidAlgorithmParameterException: the trustAnchors parameter must be non-empty
 Resolution : in mac:  /Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/security/cacerts
 in ubuntu : /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts
 if the above mentioned files are not found and them manually and it should work.
 */
public class GoogleCloudStorage {
    private static final Logger logger = Logger.getLogger(GoogleCloudStorage.class);
    private static final String loggerPrefix = GoogleCloudStorage.class.getName() + ": ";

    /*
     Google cloud storage interface
     */
    private Storage storage;

    /*
     constructor which should be used.
     */
    public GoogleCloudStorage() {
        storage = StorageOptions.getDefaultInstance().getService();
    }

    /**
     * added for test
     */
    public void setStorage(Storage storage){
        this.storage = storage;
    }

    /**
     uploadFile as the name suggests this method helps to upload file to GCS.
     @param bucketName :- Google cloud bucket name from where to download.
     @param key :- The key in google cloud where the content will be stored
     @param file : - The file where the content of the data is store.
     */
    public boolean uploadFile(String bucketName, String key, File file) {

        boolean uploadSuccessful = false;
        try {
            byte[] content = Files.readAllBytes(Paths.get(file.getPath()));
            uploadSuccessful = uploadFile(bucketName, key, content);
        } catch (Exception ex){
            logger.error(loggerPrefix + ex.getMessage());
        }
        return uploadSuccessful;
    }

    /**
     uploadFile as the name suggests this method helps to upload file to GCS.
     @param bucketName :- Google cloud bucket name from where to download.
     @param key :- The key in google cloud where the content will be stored
     @param content : - The byte content which needs to be stored.
     */
    public boolean uploadFile(String bucketName, String key, byte[] content) {
        boolean uploadSuccessful = false;
        try {
            BlobId blobId = BlobId.of(bucketName, key);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/octet-stream").build();
            storage.create(blobInfo, content);
            uploadSuccessful = true;
        } catch (Exception e) {
            logger.info(loggerPrefix + "Failed to upload file content - " + bucketName + " " + key  + " message : " +  e.getMessage());
            e.printStackTrace();
        }
        return uploadSuccessful;
    }

    /**
     * Download byte[] content from the GCS for the mentioned key
     * @param bucketName :- Google cloud bucket name from where to download.
     * @param key :- The key in google cloud where the content will be stored.
     */
    public byte[] downloadContent (String bucketName, String key) {
        BlobId blobId = BlobId.of(bucketName, key);
        return storage.readAllBytes(blobId);
    }

    /**
     * Download file with the key mentioned.
     * @param bucketName
     * @param key
     * @return
     */
    public boolean downloadFile(String bucketName, String key) {
        return downloadFile(bucketName, key, new File(key));
    }

    /**
     * Download file with the file name mentioned in the argument.
     * @param bucketName :- Google cloud bucket name from where to download.
     * @param key :- The key in google cloud where the content will be stored.
     * @param file :- The file where data needs to be saved.
     * @return
     */
    public boolean downloadFile(String bucketName, String key, File file) {
        boolean downloadSuccessful = false;
        BlobId blobId = BlobId.of(bucketName, key);
        byte[] content = storage.readAllBytes(blobId);
        try {
            Files.write(Paths.get(file.getPath()), content);
            downloadSuccessful = true;
        }catch (Exception ex) {
            logger.error(loggerPrefix + "Unable to download file - " + bucketName + key + file.getName() + ex.getMessage());
        }
        return downloadSuccessful;
    }

    public static void main(String[] args) {
        GoogleCloudStorage googleCloudStorage = new GoogleCloudStorage();
        boolean isUploaded = googleCloudStorage.uploadFile("ap-logs-staging", "test.txt", "This is a test".getBytes());
        if(isUploaded){
            logger.info("uploaded file successfully");
        } else {
            logger.error("unable to upload the file");
        }
    }

}
