import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String url = "https://www.flipkart.com/tripr-solid-men-mandarin-collar-black-t-shirt/p/itm7dd5187e59102?pid=TSHH29PDGZ9Q4HYY&lid=LSTTSHH29PDGZ9Q4HYY83GKUJ&marketplace=FLIPKART&store=clo%2Fash%2Fank%2Fedy&srno=b_1_13&otracker=browse&fm=organic&iid=6cc48146-ad3b-4ec5-a435-3f22cca542fa.TSHH29PDGZ9Q4HYY.SEARCH&ppt=browse&ppn=browse&ssid=t32h9f0f39pzrm681728591259477";
        WebPage page = new WebPage("shoes", url);
        page.loadPage();
        page.loadPrice();
        System.out.println(page.getActualPrice());
        System.out.println(page.getCurrentPrice());
    }
}