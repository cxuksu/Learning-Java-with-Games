/*
 * PlayerPanel.java - A class defines the player's panel.
 */
package blackjack;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author cxu
 */
public class PlayerPanel extends JPanel {

    private Player dealer;
    private Player gambler;
    private Board board;

    private JPanel betP;
    private JPanel actionP;
    private JPanel msgP;

    private JButton newGameBtn;
    private JButton hitBtn;
    private JButton standBtn;
    private JButton bet25Btn;
    private JButton bet100Btn;
    private JButton bet500Btn;
    private JButton bet1000Btn;
    private JButton bet5000Btn;
    private JLabel totalLbl;
    private JTextField totalTxt;
    private JButton exitBtn;
    private JLabel msgLbl;
    private JTextField msgTxt;
    private boolean newBet = true; // a flag for one bet
    private int preChipValue;

    public PlayerPanel() {
        initPlayerPanel();
    }

    private void initPlayerPanel() {
        ActionListener myActionListener = new MyActionListener();
        setLayout(new GridLayout(3, 1));

        betP = new JPanel();
        bet25Btn = new JButton("Bet 25");
        bet25Btn.addActionListener(myActionListener);
        bet100Btn = new JButton("Bet 100");
        bet100Btn.addActionListener(myActionListener);
        bet500Btn = new JButton("Bet 500");
        bet500Btn.addActionListener(myActionListener);
        bet1000Btn = new JButton("Bet 1000");
        bet1000Btn.addActionListener(myActionListener);
        bet5000Btn = new JButton("Bet 5000");
        bet5000Btn.addActionListener(myActionListener);
        betP.add(bet25Btn);
        betP.add(bet100Btn);
        betP.add(bet500Btn);
        betP.add(bet1000Btn);
        betP.add(bet5000Btn);
        add(betP);

        actionP = new JPanel();
        newGameBtn = new JButton("New Game");
        newGameBtn.addActionListener(myActionListener);
        newGameBtn.setEnabled(false);
        actionP.add(newGameBtn);
        hitBtn = new JButton("Hit");
        hitBtn.addActionListener(myActionListener);
        actionP.add(hitBtn);
        standBtn = new JButton("Stand");
        standBtn.addActionListener(myActionListener);
        actionP.add(standBtn);
        totalLbl = new JLabel("Total");
        actionP.add(totalLbl);
        totalTxt = new JTextField(10);
        totalTxt.setText("Enter amount");
        totalTxt.addActionListener(myActionListener);
        actionP.add(totalTxt);
        add(actionP);

        msgP = new JPanel();
        msgLbl = new JLabel("Message: ");
        msgTxt = new JTextField(25);
        msgTxt.setForeground(Color.red);
        exitBtn = new JButton("Exit");
        exitBtn.addActionListener(myActionListener);
        msgP.add(msgLbl);
        msgP.add(msgTxt);
        msgP.add(exitBtn);
        add(msgP);
        msgTxt.setText("Enter your total amount and press <Enter>");

        initGame(); // before entering the total amount
    }

    class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == totalTxt) {
                gambler.setTotalAmount(Integer.parseInt(totalTxt.getText()));
                newGameBtn.setEnabled(true); // start new game
                msgTxt.setText("Click button New Game or Exit");
            } else if (evt.getSource() == newGameBtn) {
                resetGame();
                dealer.assignHand(false, Consts.DEALER_Y);
                dealer.assignHand(true, Consts.DEALER_Y);

                gambler.assignHand(true, Consts.GAMBLER_Y);
                gambler.assignHand(true, Consts.GAMBLER_Y);
                clickedNewGame();
            } else if (evt.getSource() == hitBtn) {
                newBet = false;
                gambler.assignHand(true, Consts.GAMBLER_Y);
                // if Busted happens before hitting stand button
                int status = gambler.getStatus();
                checkGamblerStatus(status);
            } else if (evt.getSource() == standBtn) {
                hitBtn.setEnabled(false);
                // reveal the first facedown card
                Card theCard = dealer.getHand().get(0);
                theCard.setFaceUp(true);

                dealerHitStand();
                determineFinalStatus();
            } else if (evt.getSource() == bet25Btn) {
                gamblerBet(25);
            } else if (evt.getSource() == bet100Btn) {
                gamblerBet(100);
            } else if (evt.getSource() == bet500Btn) {
                gamblerBet(500);
            } else if (evt.getSource() == bet1000Btn) {
                gamblerBet(1000);
            } else if (evt.getSource() == bet5000Btn) {
                gamblerBet(5000);
            } else if (evt.getSource() == exitBtn) {
                System.exit(0);
            }
            board.repaint();
            if (gambler.getTotalAmount() >= 0) { // if having money
                totalTxt.setText("" + gambler.getTotalAmount());
            }

            if (board.isStandFlag() == true) { // finally, init a new game
                // allow new game
                if (gambler.getTotalAmount() >= 25) { // may have a bet on $25
                    newGameBtn.setEnabled(true);
                } else {
                    newGameBtn.setEnabled(false); // disable newGameBtn
                    msgTxt.setText("You have no money to play a new game. Exit");
                }
                initGame();
            }
        }
    }

    private void checkGamblerStatus(int status) {
        if (status == Consts.BUSTED) {
            msgTxt.setText("Click Stand button to see dealer's status");
        } else {
            msgTxt.setText("Bet or click button Hit or Stand");
        }
    }

    private void dealerHitStand() {
        if (dealer.getHandValue2() == 0) { // if no Ace
            if (dealer.getHandValue1() <= 16) { // hit
                while (dealer.getHandValue1() <= 16) {
                    dealer.assignHand(true, Consts.DEALER_Y);
                }
            } else if (dealer.getHandValue1() >= 17) { // stand
            }
        }
        if (dealer.getHandValue2() != 0) { // if has Ace
            if (dealer.getHandValue1() == 21) { // based on value1
                // Blackjack, stand
            } else if (dealer.getHandValue1() <= 16) { // hit
                while (dealer.getHandValue1() <= 16) {
                    dealer.assignHand(true, Consts.DEALER_Y);
                }
            } else if (dealer.getHandValue1() >= 17) { // stand
            }
            if (dealer.getHandValue1() > 21) { // based on value2
                if (dealer.getHandValue2() <= 16) { // hit
                    while (dealer.getHandValue2() <= 16) {
                        dealer.assignHand(true, Consts.DEALER_Y);
                    }
                } else if (dealer.getHandValue2() >= 17) { // stand
                }
            }
        }
        board.setStandFlag(true);
    }

    private void determineFinalStatus() {
        int dS = dealer.getStatus();
        int gS = gambler.getStatus();
        if ((dS == Consts.BLACKJACK) && (gS == Consts.BLACKJACK)) {
            // reset the statuses
            dealer.setStatus(Consts.PUSHED);
            gambler.setStatus(Consts.PUSHED);
            sayHa();
        } else if ((dS == Consts.BUSTED) && (gS == Consts.BUSTED)) {
            // reset the statuses
            dealer.setStatus(Consts.PUSHED);
            gambler.setStatus(Consts.PUSHED);
            sayHa();
        } else if ((dS == Consts.BLACKJACK) && (gS != Consts.BLACKJACK)) {
            sayBad();
        } else if ((dS != Consts.BLACKJACK) && (gS == Consts.BLACKJACK)) {
            sayGood();
        } else if ((dS == Consts.BUSTED) && (gS != Consts.BUSTED)) {
            gambler.setStatus(Consts.WON);
            sayGood();
        } else if ((dS != Consts.BUSTED) && (gS == Consts.BUSTED)) {
            dealer.setStatus(Consts.WON);
            sayBad();
        } // COMPARE: four combinations
        else if (((dS == Consts.COMPARE1) || (dS == Consts.COMPARE2))
                && ((gS == Consts.COMPARE1) || (gS == Consts.COMPARE2))) {
            // without Blackjack or Busted
            int dV1 = dealer.getHandValue1();
            int dV2 = dealer.getHandValue2();
            int gV1 = gambler.getHandValue1();
            int gV2 = gambler.getHandValue2();
            if ((dS == Consts.COMPARE1) && (gS == Consts.COMPARE1)) {
                compHandValues(dV1, gV1);
            } else if ((dS == Consts.COMPARE1) && (gS == Consts.COMPARE2)) {
                compHandValues(dV1, gV2);
            } else if ((dS == Consts.COMPARE2) && (gS == Consts.COMPARE1)) {
                compHandValues(dV2, gV1);
            } else if ((dS == Consts.COMPARE2) && (gS == Consts.COMPARE2)) {
                compHandValues(dV2, gV2);
            }
        }
    }

    public void compHandValues(int dV, int gV) {
        if (dV == gV) {
            dealer.setStatus(Consts.PUSHED);
            gambler.setStatus(Consts.PUSHED);
            sayHa();
        } else {
            if (dV > gV) {
                dealer.setStatus(Consts.WON);
                sayBad();
            } else {
                gambler.setStatus(Consts.WON);
                sayGood();
            }
        }
    }

    private void gamblerBet(int chipValue) {
        if (!newBet) { // if the previous bet is fail
            // return the previous bet value back to the totalAmount
            gambler.setTotalAmount(gambler.getTotalAmount() + preChipValue);
            newBet = true; // continue a new bet
        }
        int remainTotal = gambler.assignChips(chipValue);
        if (remainTotal > 0) { // it is the normal case
            hitBtn.setEnabled(true);
            standBtn.setEnabled(true);
            board.setBetFlag(true);
            msgTxt.setText("More bet or click button Hit or Stand");
        } else if (remainTotal == 0) {
            // no more bet, but hit or stand are allowed
            disableBet();
            hitBtn.setEnabled(true);
            standBtn.setEnabled(true);
            msgTxt.setText("No more bet can be made");
        } else { // if remainTotal < 0, the bet cannot go through
            preChipValue = chipValue; // keep the bet value for returning back
            newBet = false; // a new bet is fail since money is not enough
            // don't allow "hit" or "stand"
            hitBtn.setEnabled(false);
            standBtn.setEnabled(false);
            // show the information
            msgTxt.setText("Your money is not enough. RE-BET or Exit");
            totalTxt.setText("" + (gambler.getTotalAmount() + chipValue)
                    + " < " + chipValue);
        }
    }

    private void sayGood() {
        // won the bets
        gambler.setTotalAmount(gambler.getTotalAmount()
                + 2 * gambler.getChipsValue());
        msgTxt.setText("Congratulations!!! You won! New Game or Exit");
    }

    private void sayBad() {
        // lost the bets
        msgTxt.setText("Sorry! You lost! New Game or Exit");
    }

    private void sayHa() {
        // return the bets
        gambler.setTotalAmount(gambler.getTotalAmount()
                + gambler.getChipsValue());
        msgTxt.setText("Ha, No one won the game. New Game or Exit");
    }

    private void initGame() {
        //newGame.setEnabled(true) cannot be included for the first time
        hitBtn.setEnabled(false);
        standBtn.setEnabled(false);
        disableBet();
    }

    private void disableBet() {
        bet25Btn.setEnabled(false);
        bet100Btn.setEnabled(false);
        bet500Btn.setEnabled(false);
        bet1000Btn.setEnabled(false);
        bet5000Btn.setEnabled(false);
    }

    private void resetGame() {
        initGame();
        dealer.initPlayer();
        gambler.initPlayer();
        board.setReset();
    }

    private void clickedNewGame() {
        board.setNewGameFlag(true);
        newGameBtn.setEnabled(false);
        hitBtn.setEnabled(true);
        standBtn.setEnabled(true);
        bet25Btn.setEnabled(true);
        bet100Btn.setEnabled(true);
        bet500Btn.setEnabled(true);
        bet1000Btn.setEnabled(true);
        bet5000Btn.setEnabled(true);

        msgTxt.setText("Bet or click button Hit or Stand");
    }

    public void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    public void setGambler(Player gambler) {
        this.gambler = gambler;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
