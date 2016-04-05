import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Wojtek on 2016-01-11.
 */
public class Stats {

    AtomicInteger totalFilesSent;

    long startTime;

    public Stats() {
        totalFilesSent = new AtomicInteger();
        startTime = System.currentTimeMillis();
    }

    public int getTotalFilesSent() {
        return totalFilesSent.get();
    }

    public float getFilesSentPerSecond() {
        long now = System.currentTimeMillis();
        int seconds = (int)(now - startTime)/1000;
        int totalFiles = totalFilesSent.get();
        return (float)totalFiles / (float)seconds;
    }

    public void fileSent() {
        totalFilesSent.incrementAndGet();
    }

}
