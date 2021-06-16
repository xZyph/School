package helpers;

public class ConsoleHeader {
    private String title;
    private String body;
    private int length;

    public ConsoleHeader(String body) {
        this.body = " # " + body + " #";
        this.length = body.length() + 4;
    }

    public ConsoleHeader(String[] body) {
        this.title = body[0];
        this.length = title.length() + 4;
        this.body = "";

        for(int i = 1; i < body.length; i++) {
            this.length = Math.max((body[i].length() + 4), this.length);
            this.body += " # " + body[i] + " #";
        }
    }

    public void show() {
        System.out.println("\n " + generateBorder(length));

        if(title != null) {
            int titleCenterSpacingLength = (this.length - title.length() - 4) / 2;
            String titleSpacingBefore = "";
            String titleSpacingAfter = "";

            for (int i = 0; i < titleCenterSpacingLength; i++) {
                titleSpacingBefore += " ";
            }
            titleSpacingAfter = title.length() % 2 != 0 && length % 2 != 0 ? titleSpacingBefore : titleSpacingBefore + " ";

            System.out.println(" # " + titleSpacingBefore + title + titleSpacingAfter + " #");
        }

        if(body != null) {
            System.out.println(body);
        }

        System.out.println(" " + generateBorder(length) + "\n");
    }

    private String generateBorder(int length) {
        String border = "";

        for(int i = 0; i < length; i++) {
            border += "#";
        }

        return border;
    }
}
