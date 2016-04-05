import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Wojtek on 2016-01-11.
 */
public class DropboxService {

    public static final String DROPBOX_ACCESS_TOKEN = "zmLFoBG73CAAAAAAAAAABwgf8u0O1zgCiYG_Q8xaft-lkuqc3l-6184CRzAYM4wr";

    DbxClientV2 client;

    public DropboxService() {
        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
        client = new DbxClientV2(config, DROPBOX_ACCESS_TOKEN);
    }

    public void uploadFile(File f) {
        boolean isDone = false;
        while(!isDone) {
            try {
                client.files.uploadBuilder("/" + f.getName()).run(new FileInputStream(f));
                isDone = true;
                System.out.println("Plik: " + f.getName() + " wrzucony do folderu na dropboksie.");
            } catch (IOException | DbxException e) {
                System.out.println("Plik: " + f.getName() + " nie udalo sie wrzucic na dropboksa :(");
            }
        }
    }

}