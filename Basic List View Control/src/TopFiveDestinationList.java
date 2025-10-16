// TopFiveDestinationList.java
// CS 250 – Module Three – Developing Basic ListView Control
// Ordered list (1–5) with name, one-sentence description, and picture
// Clickable “View package” link (opens browser)


import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import javax.swing.*;
import javax.swing.border.*;

// ---------- Application entry ----------
public class TopFiveDestinationList {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TopDestinationListFrame frame = new TopDestinationListFrame();
            frame.setTitle("Top 5 Destination List");
            frame.setVisible(true);
        });
    }
}

// ---------- Simple data model for each destination ----------
class Destination {
    final String name;        // e.g., "Paris, France"
    final String description; // one-sentence description for image.
    final String imagePath;   // classpath path under /resources/, e.g. "/resources/paris.jpg"
    final String packageUrl;  // text link to top-selling package

    Destination(String name, String description, String imagePath, String packageUrl) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.packageUrl = packageUrl;
    }
}

// ---------- Main Frame ----------
class TopDestinationListFrame extends JFrame {
    private final DefaultListModel<Destination> listModel = new DefaultListModel<>();
    private final JList<Destination> list = new JList<>(listModel);

    public TopDestinationListFrame() {
        super("Top Five Destination List");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 750);
        setLocationRelativeTo(null);

        // Populate Top 5 in rank order (1 → 5).
        addDestination("1. Paris, France",
        	    "Iconic landmarks and café culture great for first-time Europe trips.",
        	    "/resources/paris.jpg",
        	    "https://en.wikivoyage.org/wiki/Paris");

        	addDestination("2. Tokyo, Japan",
        	    "Cutting-edge cityscapes balanced with quiet temples and amazing food.",
        	    "/resources/tokyo.jpg",
        	    "https://en.wikivoyage.org/wiki/Tokyo");

        	addDestination("3. Santorini, Greece",
        	    "White-washed villages with stunning sunsets over the caldera.",
        	    "/resources/santorini.jpg",
        	    "https://en.wikivoyage.org/wiki/Santorini");

        	addDestination("4. New York City, USA",
        	    "Museums, Broadway, and neighborhoods with distinct personalities.",
        	    "/resources/nyc.jpg",
        	    "https://en.wikivoyage.org/wiki/New_York_City");

        	addDestination("5. Bali, Indonesia",
        	    "Rice terraces, surf beaches, and wellness retreats for any budget.",
        	    "/resources/bali.jpg",
        	    "https://en.wikivoyage.org/wiki/Bali");

        // EXTRA CUSTOMIZATION (color scheme + fonts + padding)
        list.setCellRenderer(new DestinationRenderer());
        list.setFixedCellHeight(120); // consistent row height
        list.setSelectionBackground(new Color(30, 120, 180));
        list.setSelectionForeground(Color.WHITE);
        list.setBackground(new Color(245, 247, 250));

        // Make the “link” behave like a link: when a row is clicked, open its package URL.
        list.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        list.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int idx = list.locationToIndex(e.getPoint());
                if (idx >= 0) {
                    Destination d = listModel.getElementAt(idx);
                    openInBrowser(d.packageUrl);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Name label
        JLabel who = new JLabel("Created by JaeYY Nkese");
        who.setBorder(new EmptyBorder(8, 12, 8, 12));
        getContentPane().add(who, BorderLayout.SOUTH);
    }

    private void addDestination(String name, String desc, String imagePath, String url) {
        listModel.addElement(new Destination(name, desc, imagePath, url));
    }

    // Opens the URL using the default browser; shows a dialog if something goes wrong.
    private void openInBrowser(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                JOptionPane.showMessageDialog(this, "Opening links is not supported on this system.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not open link:\n" + url,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// ---------- Custom ListCellRenderer that shows image, title, description, and a link label ----------
class DestinationRenderer extends JPanel implements ListCellRenderer<Destination> {
    private static final Border CELL_PAD = new EmptyBorder(8, 12, 8, 12);
    private final JLabel image = new JLabel();
    private final JLabel title = new JLabel();
    private final JLabel desc  = new JLabel();
    private final JLabel link  = new JLabel("View package");

    DestinationRenderer() {
        setLayout(new BorderLayout(12, 0));
        setBorder(CELL_PAD);

        // image on the left
        image.setPreferredSize(new Dimension(120, 90));
        add(image, BorderLayout.WEST);

        // center column: title, desc, link
        JPanel center = new JPanel(new GridLayout(3, 1, 0, 2));
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        desc.setFont(desc.getFont().deriveFont(13f));
        link.setForeground(new Color(15, 102, 196)); // blue-ish link color
        link.setFont(link.getFont().deriveFont(Font.PLAIN, 13f));
        link.setText("<html><u>View package</u></html>"); // underline like a link
        center.setOpaque(false);
        center.add(title);
        center.add(desc);
        center.add(link);

        add(center, BorderLayout.CENTER);
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Destination> list, Destination d, int index,
            boolean isSelected, boolean cellHasFocus) {

        title.setText(d.name);
        desc.setText(d.description);

        // Try to load the requested image; fall back to TestImage.jpg if missing
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(getClass().getResource(d.imagePath));
        } catch (Exception ignored) {}
        if (icon == null || icon.getIconWidth() <= 0) {
            icon = new ImageIcon(getClass().getResource("/resources/TestImage.jpg"));
        }
        // scale to fit the label (keeps aspect ratio)
        Image scaled = icon.getImage().getScaledInstance(120, 90, Image.SCALE_SMOOTH);
        image.setIcon(new ImageIcon(scaled));

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }
}
