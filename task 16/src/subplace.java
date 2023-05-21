import java.awt.Image;

import javax.swing.ImageIcon;

public class subplace {
    String img_path;
    String name;

    public subplace(String name, String img_path) {
        this.name = name;
        this.img_path = img_path;
    }

    String getName() {
        return name;
    }

    ImageIcon getImageicon() {
        ImageIcon imageIcon = new ImageIcon(img_path);
        Image newimg = imageIcon.getImage().getScaledInstance(200, 100,
                java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    String getImgPath() {
        return img_path;
    }

    public String toString() {
        return name;
    }
}
