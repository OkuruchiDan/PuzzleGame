import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class PuzzleButton extends JButton {
    private boolean isSelectedButton = false;
    private Image image;

    public PuzzleButton() {
        super();
        initUI();
    }

    public PuzzleButton(Image i) {
        super(new ImageIcon(i));
        this.image = i;
        initUI();
    }

    private void initUI() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setBorder(BorderFactory.createLineBorder(Color.yellow));
            }
        });
    }

    public boolean isSelectedButton() {
        return isSelectedButton;
    }

    public void setSelectedButton(boolean selectedButton) {
        isSelectedButton = selectedButton;
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
