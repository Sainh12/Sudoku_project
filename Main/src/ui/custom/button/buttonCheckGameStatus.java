package ui.custom.button;


import javax.swing.*;
import java.awt.event.ActionListener;

public class buttonCheckGameStatus extends JButton {

    public buttonCheckGameStatus(final ActionListener actionlistener){
        this.setText("Verificar jogo");
        this.addActionListener(actionlistener);
    }

}
