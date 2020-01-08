import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.storage.GoogleCloudStorage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Answers.RETURNS_SELF;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GoogleCloudStorage.class)
public class GoogleCloudStorageTest {
    private GoogleCloudStorage gcs;
    private String bucketName = "test";
    private String key = "test.txt";
    private String content = "Hi, I'm learning ";

    @Before
    public void setUp(){
        try {
            Storage storageMock = mock(Storage.class);
            StorageOptions storageOptionsMock = mock(StorageOptions.class);
            Blob blobMock = mock(Blob.class);
            when(storageOptionsMock.getService()).thenReturn(storageMock);
            gcs = spy(new GoogleCloudStorage());
            gcs.setStorage(storageMock);
            when(storageMock.create(any(BlobInfo.class), any())).thenReturn(blobMock);
            when(storageMock.readAllBytes(any(BlobId.class))).thenReturn(content.getBytes());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @After
    public void tearDown(){
        gcs = null;
    }

    @Test
    public void uploadFileTest(){
       boolean isUpload = gcs.uploadFile(bucketName, key, content.getBytes());
        Assert.assertTrue(isUpload);
    }

    @Test
    public void downloadContentTest() {
        byte[] content = gcs.downloadContent(bucketName, key);
        Assert.assertNotNull(content);
        Assert.assertEquals(this.content, new String(content));
    }
}
