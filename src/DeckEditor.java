import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Evan on 8/15/2014.
 */
public class DeckEditor extends JPanel
{

    CardDeck cardDeck;
    int deckSize;
    final File[] folder;

    MouseMotionListener checkBoxMouseListener;
    ItemListener checkBoxCheckListener;
    MouseListener checkBoxPanelClickListener;

    final ArrayList<JPanel> checkPanelList = new ArrayList<JPanel>();
    final ArrayList<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
    final ArrayList<JLabel> checkLabelList = new ArrayList<JLabel>();
    JLabel planeMouseoverImage = new JLabel();
    JPanel planeImagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    JPanel buttonPanel;

    JScrollPane deckEditorScroll;

    ActionListener doneButtonListener;



    public DeckEditor(CardDeck newCardDeck, ActionListener buttonListener)
    {
        doneButtonListener = buttonListener;
        cardDeck = newCardDeck;
        deckSize = cardDeck.getDeckSize();
        folder = new File(cardDeck.getFolderName() + cardDeck.getFilePathSlash()).listFiles();
        createListeners();
        createCheckboxes();
        createPlaneLabels();
        createCheckBoxJPanels();
        initializeMouseoverImage();
        createScrollPanel();
        createButtons();
        createDeckEditorGUI();
    }

    private void createListeners()
    {
        checkBoxCheckListener = new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                for(int i = 0; i < deckSize; i++)
                {
                    if(e.getSource() == checkBoxList.get(i))
                    {
                        if(checkBoxList.get(i).isSelected())
                        {
                            cardDeck.addToDeck(i + 1);
                        } else {
                            cardDeck.removeFromDeck(i + 1);
                        }
                    }
                }
            }
        };

        checkBoxMouseListener = new MouseMotionListener()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {

            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                for(File file : folder)
                {
                    if (file.getName().contains(Integer.toString(checkPanelList.indexOf(e.getSource()) + 1)))
                    {
                        planeMouseoverImage.setIcon(new ImageIcon(cardDeck.getFolderName() + cardDeck.getFilePathSlash() + file.getName()));
                        break;
                    }
                }
            }
        };

        checkBoxPanelClickListener = new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                for(int i = 0; i < deckSize; i++)
                {
                    if(e.getSource().equals(checkPanelList.get(i)))
                    {
                        System.out.println("Mouse Click");
                        checkBoxList.get(i).setSelected(!checkBoxList.get(i).isSelected());
                        break;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e)
            {

            }

            @Override
            public void mouseReleased(MouseEvent e)
            {

            }

            @Override
            public void mouseEntered(MouseEvent e)
            {

            }

            @Override
            public void mouseExited(MouseEvent e)
            {

            }
        };

    }

    private void createCheckboxes()
    {
        for(int i = 0; i < deckSize; i++)
        {
            if(cardDeck.unusedCardsContains(i + 1))
                checkBoxList.add(new JCheckBox("", false));
            else
                checkBoxList.add(new JCheckBox("", true));
            checkBoxList.get(i).addItemListener(checkBoxCheckListener);
        }
    }

    private void createPlaneLabels()
    {
        String planeName;
        for(int i = 0; i < deckSize; i++)
        {
            for(File file : folder)
            {
                if(file.getName().contains(Integer.toString(i+1)))
                {
                    planeName = file.getName();
                    planeName = planeName.replaceAll("[0-9]", "");
                    planeName = planeName.replaceAll("[_]", " ");
                    planeName = planeName.replaceAll(".jpg", "");
                    checkLabelList.add(new JLabel(planeName));
                    break;
                }
            }
        }
    }

    private void createCheckBoxJPanels()
    {
        for(int i = 0; i < deckSize; i++)
        {
            checkPanelList.add(new JPanel(new FlowLayout(FlowLayout.LEFT)));
            checkPanelList.get(i).add(checkBoxList.get(i));
            checkPanelList.get(i).add(checkLabelList.get(i));
            checkPanelList.get(i).addMouseMotionListener(checkBoxMouseListener);
            checkPanelList.get(i).addMouseListener(checkBoxPanelClickListener);
        }
    }

    private void initializeMouseoverImage()
    {
        planeMouseoverImage.setIcon(new ImageIcon(cardDeck.getImagePath(0)));
        planeImagePanel.add(planeMouseoverImage);
    }

    private void createScrollPanel()
    {
        JPanel scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        for(int i = 0; i < deckSize; i++)
        {
            scrollPanel.add(checkPanelList.get(i));
        }
        deckEditorScroll = new JScrollPane(scrollPanel);
        deckEditorScroll.setPreferredSize(new Dimension(200, 400));
        deckEditorScroll.getVerticalScrollBar().setUnitIncrement(16);
    }

    private void createButtons()
    {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JButton doneButton = new JButton("Done");
        JButton saveButton = new JButton("Save Deck");

        buttonPanel.add(doneButton);
        buttonPanel.add(saveButton);

        doneButton.addActionListener(doneButtonListener);
        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("save deck");
                cardDeck.saveDeck();
            }
        });
    }


    private void createDeckEditorGUI()
    {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel rightSideGUILayout = new JPanel();
        rightSideGUILayout.setLayout(new BoxLayout(rightSideGUILayout, BoxLayout.Y_AXIS));
        rightSideGUILayout.add(planeImagePanel);
        rightSideGUILayout.add(buttonPanel);

        add(deckEditorScroll);
        add(rightSideGUILayout);
    }

}
