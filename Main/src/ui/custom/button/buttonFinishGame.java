package ui.custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class buttonFinishGame extends JButton {

    public buttonFinishGame(final ActionListener actionlistener){
        this.setText("Concluir");
        this.addActionListener(actionlistener);
    }

}
