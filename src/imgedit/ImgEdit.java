package imgedit;

import javax.swing.SwingUtilities;

public class ImgEdit {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Edit();
            }
        });
    }

}
