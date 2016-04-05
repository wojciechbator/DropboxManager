import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Wojtek on 2016-01-11.
 */
public class UploadDirectoryService {

    protected File directoryHandle;

    List<File> knownDirectoryState;

    List<Consumer<File>> newFileListeners;

    public UploadDirectoryService(File directoryHandle) {
        this.directoryHandle = directoryHandle;

        if(directoryHandle == null) throw new NullPointerException("Pusty folder z plikami do wrzucania.");
        if(!directoryHandle.isDirectory()) throw new IllegalStateException("Brak wskazania folderu. Wyrzucam wyjatek.");

        newFileListeners = new ArrayList<>();
        knownDirectoryState = new ArrayList<>();
    }

    public void listen() {
        List<File> directoryState = listFiles();
        if(!directoryState.equals(knownDirectoryState)) {
            onDirectoryContentChanged(directoryState);
        }
    }

    public void addOnNewFileDetectedListener(Consumer<File> newFileListener) {
        newFileListeners.add(newFileListener);
    }

    void onDirectoryContentChanged(List<File> newState) {
        newState.forEach(f -> {
            if(!knownDirectoryState.contains(f)) onNewFileDetected(f);
        });
        knownDirectoryState = newState;
    }

    void onNewFileDetected(File newFile) {
        newFileListeners.forEach(n -> n.accept(newFile));
    }

    List<File> listFiles() {
        return Arrays.asList(directoryHandle.listFiles());
    }

}
