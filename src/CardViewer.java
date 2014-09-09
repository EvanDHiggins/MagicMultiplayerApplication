import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by EvanHiggins on 8/14/14.
 */
public class CardViewer extends JPanel
{
    //deckSize contains the size of the instanced deck retrieved from the passed CardDeck object
    int deckSize;

    CardDeck cardDeck;

    JLabel planeImage;
    JPanel planeImagePanel = new JPanel();
    JPanel buttonHolder = new JPanel();

    JPopupMenu ongoingSchemePopup;

    ImageIcon planeImageIcon;

    public CardViewer(CardDeck newCardDeck)
    {
        cardDeck = newCardDeck;
        deckSize = cardDeck.getDeckSize();
        createCardDisplay();
        createButtons();
        createViewerGUI();
        if(cardDeck.getFolderName().equals("archenemy"))
        {
            System.out.println("ongoing");
            createOngoingSchemePopup();
        }
    }

    private void createCardDisplay()
    {
        planeImage = new JLabel();
        planeImagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        planeImagePanel.add(planeImage);
        displayCard();
    }

    private void createButtons()
    {
        buttonHolder.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton nextCard = new JButton("Next Card");
        JButton previousCard = new JButton("Previous Card");
        JButton shuffle = new JButton("Shuffle");

        buttonHolder.add(nextCard);
        buttonHolder.add(previousCard);
        buttonHolder.add(shuffle);

        nextCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardDeck.topCardIncrement();
                displayCard();
            }
        });

        previousCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardDeck.topCardDecrement();
                displayCard();
            }
        });

        shuffle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardDeck.randomizeDeck();
                displayCard();
            }
        });
    }

    private void createViewerGUI()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(planeImagePanel);
        add(buttonHolder);

    }

    private void createOngoingSchemePopup()
    {
        final JPopupMenu ongoingSchemePopup = new JPopupMenu();
        JMenuItem ongoingScheme = new JMenuItem("Make Ongoing Scheme");

        ongoingSchemePopup.add(ongoingScheme);

        planeImage.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                maybeVisiblePopup(e);
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                maybeVisiblePopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                maybeVisiblePopup(e);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                maybeVisiblePopup(e);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                maybeVisiblePopup(e);
            }

            private void maybeVisiblePopup(MouseEvent e)
            {
                if(e.isPopupTrigger())
                {
                    ongoingSchemePopup.show(e.getComponent(),
                            e.getX(), e.getY());
                }
            }
        });

        ongoingScheme.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("new scheme thing");
                new OngoingSchemeDialog(planeImageIcon).setVisible(true);
            }
        });
    }


    private void displayCard()
    {
        planeImageIcon = new ImageIcon(cardDeck.displayTopCard());
        planeImage.setIcon(planeImageIcon);
    }


}

class OngoingSchemeDialog extends JDialog
{
    public OngoingSchemeDialog(ImageIcon schemeImage)
    {
        JLabel image = new JLabel(schemeImage);
        add(image);
        setSize(new Dimension(325, 475));
        setResizable(false);
    }
}

