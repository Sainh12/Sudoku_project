package ui.custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class buttonReset extends JButton {

    public buttonReset(final ActionListener actionlistener){
        this.setText("Reiniciar Jogo");
        this.addActionListener(actionlistener);
    }


}
