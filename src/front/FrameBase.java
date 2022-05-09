package front;

import javax.swing.*;
import java.awt.*;
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
