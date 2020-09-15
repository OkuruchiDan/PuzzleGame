import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Game extends JFrame {
    private static final int DESIRED_WIDTH = 400;
    private BufferedImage image;

    public Game(){
        initUI();
    }

    private void initUI() {
        try {
            image = loadImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Creating Object PuzzleImage it Allows to take a ArrayList of sliced Image
        //,path to directory with sliced Image
        //,chek the right solution
        PuzzleImage puzzleImage = new PuzzleImage(image);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.gray));
        panel.setLayout(new GridLayout(4, 3, 0, 0));
        add(panel, BorderLayout.CENTER);

        //Loading files from created folder.
//        File directory = puzzleImage.getDirectory();
//        File[] files = directory.listFiles();
//        for (File file : Objects.requireNonNull(files)) {
//            try {
//                BufferedImage image = ImageIO.read(file);
//                PuzzleButton button = new PuzzleButton(image);
//                panel.add(button);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        //loading ArrayList of PuzzleButton from PuzzleImage
        ArrayList<PuzzleButton> buttons = puzzleImage.getSplitImages();
        ArrayList<PuzzleButton> solution = puzzleImage.getSolution();
        for (PuzzleButton button : buttons) {
            panel.add(button);
            button.setBorder(BorderFactory.createLineBorder(Color.gray));
            button.addActionListener(new ClickAction(buttons, panel, solution));
        }

        pack();
        setTitle("Puzzle");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    //Loading Image from src Folder
    private BufferedImage loadImage() throws IOException {
        return ImageIO.read(new File("IMG_0290.jpg"));
    }
}
