import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WebPage {
    private String pageTitle;
    private String pageUrl;
    private int actualPrice;
    private int targetPrice;

    public int getCurrentPrice() {
        return currentPrice;
    }


    private int currentPrice;
    private File pageSource;
    private final String specialKey = "₹";

    public WebPage(String pageTitle, String pageUrl) {
        this.pageUrl = pageUrl;
        this.pageTitle = pageTitle;

    }

    private ProcessBuilder createProcessBuilder(String... commands) {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(commands);
        return builder;
    }

    public void loadPage() throws IOException, InterruptedException {
        ArrayList<ProcessBuilder> commands = new ArrayList<>();
        this.pageSource = File.createTempFile("fkm", "txt");
        Process p = this.createProcessBuilder("w3m", "-dump", pageUrl)
                .redirectOutput(this.pageSource)
                .start();
        p.waitFor();
    }

    public void loadPrice() throws IOException, InterruptedException {
        File file = File.createTempFile("fkmp", "txt");
        this.createProcessBuilder("grep", specialKey, this.pageSource.getAbsolutePath())
                .redirectOutput(file)
                .start()
                .waitFor();


        List<String> lines = Files.readAllLines(Path.of(file.getAbsolutePath()));
        String cp = lines.getFirst()
                .replaceAll(specialKey, "")
                .replaceAll(",", "");
        String ap = lines.get(1)
                .replaceAll(specialKey, "")
                .replaceAll(",", "");
        this.currentPrice = Integer.parseInt(cp);
        this.actualPrice = Integer.parseInt(ap);

        file.delete();
    }


    public int getActualPrice() {
        return actualPrice;
    }

}
