package front;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to unify the creation of all frames in project
 */
public abstract class FrameBase extends JFrame {

    /**
     * The baseFont to used in this project
     * All the windows use this baseFont
     */
    public static final Font baseFont = new Font(Font.SERIF, Font.PLAIN, 20);
    public static final int menuSkip = 60;

    /**
     * @param baseContainer container to extract components from
     * @return all the components (inner too)
     */
    public static List<Component> getAllComponents(Container baseContainer) {
        List<Component> components = new ArrayList<>();
        for(Component component : baseContainer.getComponents()) {
            components.add(component);
            if(component instanceof Container) {
                components.addAll(getAllComponents((Container) component));
            }
        }
        return components;
    }

    /**
     * @return all components of this frame
     */
    protected List<Component> getAllComponents() {
        return getAllComponents(this);
    }

    protected void setFonts() {
        for(Component c : getAllComponents()) {
            c.setFont(getFontToUse());
        }
    }

    public FrameBase(String title, int width, int height, LayoutManager layoutManager) throws HeadlessException {
        super();
        setTitle(title);
        setSize(width, height);
        setResizable(false);
        setLayout(layoutManager);

        setupUI();
        setupButtons();
        setupMenu();

        setFonts();

        setVisible(true);
    }

    protected void savePNG(int WIDTH, int HEIGHT) {
        // this is event to save current scheme to .png file
        JFileChooser saveFile = new JFileChooser(); // create JFileChooser
        saveFile.addChoosableFileFilter(new FileNameExtensionFilter("Only .png files", ".png"));
        int saveDialogResult = saveFile.showSaveDialog(null); // show dialog
        File fileToWriteTo = null;
        switch (saveDialogResult) {
            case JFileChooser.APPROVE_OPTION: // if user have chosen some file
                fileToWriteTo = saveFile.getSelectedFile(); // we get this file
                break;
            case JFileChooser.ERROR_OPTION:
            case JFileChooser.CANCEL_OPTION:
            default:
                break;
        }

        if(fileToWriteTo != null) {
            BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics graphicsToDraw = bufferedImage.createGraphics();
            graphicsToDraw.translate(0, -menuSkip); // translate graphics position so it can skip title and menu
            paintAll(graphicsToDraw); // paint with help of local paint function
            try {
                ImageIO.write(bufferedImage, "png", fileToWriteTo); // copy&paster from stackoverflow
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Prints error message msg.
     *
     * @param msg massage to print
     */
    protected void handleException(String msg) {
        JFrame frame = new JFrame(); // create frame
        frame.setSize(500, 200); // set its size
        frame.setLayout(new FlowLayout()); // set flow layout because we only two elements
        JLabel label = new JLabel(msg); // create msg
        frame.add(label);
        JButton ok = new JButton("OK"); // create OK button
        ok.addActionListener(event -> frame.dispose()); // set button event
        frame.add(ok); // add button
        frame.setVisible(true); // show frame
    }

    /**
     * Create user interface elements
     */
    protected abstract void setupUI();

    /**
     * Creates buttons (contains business logic)
     */
    protected abstract void setupButtons();

    /**
     * Creates user menus
     */
    protected abstract void setupMenu();

    protected Font getFontToUse() {
        return baseFont;
    }
}
