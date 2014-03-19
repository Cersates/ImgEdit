package imgedit;

import javax.swing.filechooser.FileFilter;

public class Filter extends FileFilter { //Фильтер файлов

    private String ext;

    public Filter(String ext) {
        this.ext = ext;
    }

    @Override
    public boolean accept(java.io.File file) {
        if (file.isDirectory()) {
            return true;
        }
        return (file.getName().endsWith(ext));
    }

    @Override
    public String getDescription() {
        return "*" + ext;
    }
}
