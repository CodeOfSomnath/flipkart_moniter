import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataAdder {
    public static void main(String[] args) throws IOException {
        File file = new File("/tmp/moniter_stdin.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer;
        Scanner sc = new Scanner(System.in);
        
        String pageTitle, pageUrl;

        while (true) {
            writer = new FileWriter(file, true);
            System.out.println("Enter the page title:");
            pageTitle = sc.nextLine().strip();
            System.out.println("Enter the page url:");
            pageUrl = sc.nextLine().strip();
            if (pageTitle.isBlank() || pageUrl.isBlank()) {
                break;
            }

            writer.write(String.format("%s\n", pageTitle));
            writer.write(String.format("%s\n", pageUrl));
            writer.close();
        }
        
        sc.close(); 
    }
}
