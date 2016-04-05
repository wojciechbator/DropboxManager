import java.io.File;

/**
 * Created by Wojtek on 2016-01-11.
 */
public class Main {

    public static void main(String[] args) {
        GUI gui = new GUI();

        while(!gui.hasStarted()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        File directoryHandle = new File(gui.getDirectory());
        UploadDirectoryService directoryHandler = new UploadDirectoryService(directoryHandle);
        FileUtil fileProcessor = new FileUtil(new DropboxService(), gui.getThreadCount());
        Stats statistics = new Stats();

        gui.setup(directoryHandler, fileProcessor, statistics);

        while(true) {
            directoryHandler.listen();
            gui.update();
        }
    }
}