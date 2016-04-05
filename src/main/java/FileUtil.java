import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Created by Wojtek on 2016-01-11.
 */
public class FileUtil {

    DropboxService dropManager;

    Executor executor;

    public FileUtil(DropboxService dropM, int poolSize) {
        this.dropManager = dropM;
        executor = Executors.newFixedThreadPool(poolSize);
    }

    public void submitFile(File f) {
        executor.execute(() -> dropManager.uploadFile(f));
    }

    public void submitFile(File f, Consumer<File> successCallback) {
        executor.execute(() -> {
            dropManager.uploadFile(f);
            successCallback.accept(f);
        });
    }

    public void submitFile(File f, Consumer<File> startCallback, Consumer<File> successCallback) {
        executor.execute(() -> {
            startCallback.accept(f);
            dropManager.uploadFile(f);
            successCallback.accept(f);
        });
    }
}
