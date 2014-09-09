import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by EvanHiggins on 8/14/14.
 */
public class FrameWindow extends JFrame
{
    JTabbedPane tabbedWindow = new JTabbedPane();

    JPanel globalWindow = new JPanel();

    CardDeck archenemyCardDeck = new CardDeck(43, "archenemy");
    CardDeck planechaseCardDeck = new CardDeck(80, "planechase");

    ActionListener doneButtonListener;



    DeckEditor archenemyDeckEditor;
    DeckEditor planechaseDeckEditor;

    CardViewer archenemyCardViewer;
    CardViewer planechaseCardViewer;

    Dimension archenemyDimension = new Dimension(370, 570);
    Dimension planechaseDimension = new Dimension(437, 417);
    Dimension archenemyDeckEditorDimension = new Dimension(627, 570);
    Dimension planechaseDeckEditorDimension = new Dimension(745, 417);




    public FrameWindow()
    {
        doneButtonListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("button");
                CardLayout cardLayout = (CardLayout) globalWindow.getLayout();
                cardLayout.show(globalWindow, "Viewer");
                if(tabbedWindow.getSelectedComponent().equals(archenemyCardViewer))
                {
                    setSize(archenemyDimension);
                } else if (tabbedWindow.getSelectedComponent().equals(planechaseCardViewer)){
                    setSize(planechaseDimension);
                }

            }
        };

        archenemyDeckEditor = new DeckEditor(archenemyCardDeck, doneButtonListener);
        archenemyCardViewer = new CardViewer(archenemyCardDeck);

        planechaseDeckEditor = new DeckEditor(planechaseCardDeck, doneButtonListener);
        planechaseCardViewer = new CardViewer(planechaseCardDeck);

        createTabbedWindow();
        createMenuBar();
        createCardLayout();
        createJFrame();
    }

    private void createTabbedWindow()
    {
        tabbedWindow.addTab("Archenemy", archenemyCardViewer);
        tabbedWindow.addTab("Planechase", planechaseCardViewer);


        tabbedWindow.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                if(tabbedWindow.getSelectedComponent().equals(archenemyCardViewer))
                {
                    setSize(archenemyDimension);
                } else if (tabbedWindow.getSelectedComponent().equals(planechaseCardViewer)){
                    setSize(planechaseDimension);
                }
            }
        });
    }

    private void createMenuBar()
    {
        final JMenuBar globalMenuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem editMenuItem = new JMenuItem("Edit Deck");
        editMenuItem.setMnemonic(KeyEvent.VK_E);
        globalMenuBar.add(file);
        file.add(editMenuItem);

        editMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(new Dimension(getSize()));
                CardLayout cardLayout = (CardLayout) globalWindow.getLayout();
                if(tabbedWindow.getSelectedComponent().equals(archenemyCardViewer))
                {
                    setSize(archenemyDeckEditorDimension);
                    cardLayout.show(globalWindow, "archenemyDeckEditor");
                } else if(tabbedWindow.getSelectedComponent().equals(planechaseCardViewer)) {
                    setSize(planechaseDeckEditorDimension);
                    cardLayout.show(globalWindow, "planechaseDeckEditor");
                }
            }
        });

        setJMenuBar(globalMenuBar);
    }

    private void createCardLayout()
    {
        globalWindow.setLayout(new CardLayout());

        globalWindow.add("Viewer", tabbedWindow);
        globalWindow.add("archenemyDeckEditor", archenemyDeckEditor);
        globalWindow.add("planechaseDeckEditor", planechaseDeckEditor);
    }

    private void createJFrame()
    {
        add(globalWindow);

        setSize(archenemyDimension);
        setTitle("M:TG Multiplayer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FrameWindow mainGUIWindow = new FrameWindow();
            }
        });
    }
}

