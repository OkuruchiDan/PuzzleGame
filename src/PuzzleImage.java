import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PuzzleImage extends JComponent {
    private Image image;
    private static final int DESIRED_WIDTH = 400;
    private static final int NUMBER_OF_BUTTONS = 12;
    private final ArrayList<PuzzleButton> solution = new ArrayList<>(NUMBER_OF_BUTTONS);
    private ArrayList<PuzzleButton> splitImages = new ArrayList<>(NUMBER_OF_BUTTONS);
    private File directory;

    public PuzzleImage(Image image) {
        this.image = image;
        startSplit();
    }


    private void startSplit() {
        BufferedImage bufferedImage = imageToBufferedImage(image);
        int height = getNewHeight(bufferedImage.getWidth(), bufferedImage.getHeight());
        BufferedImage resized = resizeImage(bufferedImage, DESIRED_WIDTH, height, BufferedImage.TYPE_INT_RGB);
        directory = new File("/home/bogdan/Programming/PuzzleGame/src/items");//directory for sliced images
        splitImages = splitImageForPuzzleItems(resized, DESIRED_WIDTH, height);
        //creating directory for spliced Images
        if (directory.mkdir()) {
            try {
                saveImagesFromPuzzleButton(splitImages);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<PuzzleButton> splitImageForPuzzleItems(BufferedImage resized, int width, int height) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                image = createImage(new FilteredImageSource(resized.getSource(),
                        new CropImageFilter(j * width / 3, i * height / 4, width / 3, height / 4)));
                PuzzleButton button = new PuzzleButton(image);
                splitImages.add(button);

                button.putClientProperty("position", new Point(i, j));
            }
        }
        solution.addAll(splitImages);//saving right position of Image
        Collections.shuffle(splitImages);// shuffle items for puzzle
        return splitImages;
    }

    //Saving spliced Image in created directory.
    public void saveImagesFromPuzzleButton(ArrayList<PuzzleButton> splitImages) throws IOException {
        for (int i = 0; i < splitImages.size(); i++) {
            Image image = splitImages.get(i).getImage();
            BufferedImage bi = imageToBufferedImage(image);
            File fstream = new File(directory, i + "-btn.png");
            ImageIO.write(bi, "png", fstream);
        }
    }

    //convert From Image to BufferedImage
    public static BufferedImage imageToBufferedImage(Image im) {
        BufferedImage bi = new BufferedImage
                (im.getWidth(null), im.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }

    //resizing Image with saving proportions
    private BufferedImage resizeImage(BufferedImage originImage, int width, int height, int type) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originImage, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }

    //GetNewHeight for Image with saving Proportions based on DESIRED_WIDTH
    private int getNewHeight(int width, int height) {
        double ratio = DESIRED_WIDTH / (double) width;
        return (int) (height * ratio);
    }

    public Image getImage() {
        return image;
    }

    public ArrayList<PuzzleButton> getSplitImages() {
        return splitImages;
    }

    public ArrayList<PuzzleButton> getSolution() {
        return solution;
    }

    public File getDirectory() {
        return directory;
    }
}

