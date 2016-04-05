import javax.swing.*;
import java.awt.*;

/**
 * Created by Wojtek on 2016-01-11.
 */
public class GUI extends JFrame {

    Stats stats;
    UploadDirectoryService dirHandler;
    FileUtil fileProcessor;

    private JPanel baseP;
    private JButton beginButton;
    private JPanel higherPanel;
    private JPanel lowerPanel;
    private JLabel totalFilesSentLabel;
    private JLabel filesPerSecondLabel;
    private JSlider numberOfThreadsSlider;
    private JTextField targetDirectoryTextField;
    private boolean hasStarted = false;

    public GUI() throws HeadlessException {

        this.setContentPane(baseP);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setVisible(true);
        beginButton.addActionListener(a -> start());
    }

    public void setup(UploadDirectoryService handler, FileUtil processor, Stats statistics) {
        this.stats = statistics;
        this.dirHandler = handler;
        this.fileProcessor = processor;

        dirHandler.addOnNewFileDetectedListener(n -> System.out.println("Znaleziono plik: " + n.getName()));
        dirHandler.addOnNewFileDetectedListener(n -> fileProcessor.submitFile(n, f -> statistics.fileSent()));
    }

    public boolean hasStarted() {
        return hasStarted;
    }

    private void start() {
        hasStarted = true;

        for(Component c : higherPanel.getComponents()) c.setEnabled(false);

        higherPanel.revalidate();
        higherPanel.doLayout();
    }

    public void update() {
        updateTotalFilesSent(stats.getTotalFilesSent());
        updateFilesPerSecond(stats.getFilesSentPerSecond());
    }

    public String getDirectory() {
        return targetDirectoryTextField.getText();
    }

    public int getThreadCount() {
        return numberOfThreadsSlider.getValue();
    }

    public void updateFilesPerSecond(float fps) {
        filesPerSecondLabel.setText("Ile wrzuca plikow na sekunde: " + String.format("%.2f", fps));
    }

    public void updateTotalFilesSent(int total) {
        totalFilesSentLabel.setText("W sumie: " + total);
    }

}
