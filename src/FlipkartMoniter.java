import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


public class FlipkartMoniter extends Thread {

    private ArrayList<WebPage> pages;
    private boolean exitFromLoop = true;

    public FlipkartMoniter(ArrayList<WebPage> webPages) {
        this.pages = webPages;
    }

    public void exit() {
        this.exitFromLoop = false;
    }
    
    public void showNotification(String message, WebPage page) {
        JOptionPane.showMessageDialog(null,
            message,
            "Flipkart Moniter",
            JOptionPane.INFORMATION_MESSAGE);

        System.out.printf(
            "title: %s\nurl: %s\ncurrent price: %s%d\nactual price: %s%d\ntarget price: %s%d\n",
            page.getPageTitle(),
            page.getPageUrl(),
            page.getRsSign(),
            page.getCurrentPrice(),
            page.getRsSign(),
            page.getActualPrice(),
            page.getRsSign(),
            page.getTargetPrice()
        );
    }


    public void updateWebPagesList() throws IOException, InterruptedException {
        File file = new File("/tmp/moniter_stdin.txt");
        FileWriter writer = new FileWriter(file, true);
        if (file.exists()) {
            List<String> lines = Files.readAllLines(Path.of(file.getAbsolutePath()));
            String title = lines.get(0).strip();
            String url = lines.get(1).strip();
            pages.add(new WebPage(title, url));
            writer.write("");
            writer.close();
        }
        
    }


    @Override
    public void run() {
        WebPage page;

        while (this.exitFromLoop) {
            for (int i = 0; i < pages.size(); i++) {
                page = pages.get(i);
                if (page.getTargetPrice() >= page.getCurrentPrice()) {
                    showNotification(
                        String.format("%s: %s%d", page.getPageTitle(), page.getRsSign(), page.getCurrentPrice()),
                        page
                    );
                    pages.remove(i);
                }
                try {
                    updateWebPagesList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
