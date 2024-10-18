import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WebPage {
    private String pageTitle;
    public String getPageTitle() {
        return pageTitle;
    }


    private String pageUrl;

    

    public String getPageUrl() {
        return pageUrl;
    }


    private int actualPrice;
    private int targetPrice;
    public void setTargetPrice(int price) {
        this.targetPrice = price;
    }

    public int getTargetPrice() {
        return targetPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }


    private int currentPrice;
    private File pageSource;
    private final String specialKey = "₹";

    public String getRsSign(){
        return specialKey;
    }

    public WebPage(String pageTitle, String pageUrl) throws IOException, InterruptedException {
        this.pageUrl = pageUrl;
        this.pageTitle = pageTitle;
        this.loadPage();
        this.loadPrice();

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
