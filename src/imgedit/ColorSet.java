package imgedit;

import javax.swing.*;

public class ColorSet extends JDialog {

    JColorChooser choosColor;

    public ColorSet(JFrame owner, String title) {
        super(owner, title, true);
        this.add(choosColor);
        this.setSize(200, 200);
    }
}
