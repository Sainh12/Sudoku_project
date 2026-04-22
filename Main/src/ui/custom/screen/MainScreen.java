package ui.custom.screen;

import service.BoardService;
import ui.custom.button.buttonCheckGameStatus;
import ui.custom.button.buttonFinishGame;
import ui.custom.button.buttonReset;
import ui.custom.frame.MainFrame;
import ui.custom.panel.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;

    private JButton finishedButton;
    private JButton checkGameStatusButton;
    private JButton resetButton;

    public MainScreen(final Map<String,String> gameConfig){
        this.boardService = new BoardService(gameConfig);
    }

    public void buildMainScreen(){
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        addResetButton(mainPanel);
        addShowGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void addFinishGameButton(JPanel mainPanel) {
        finishedButton = new buttonFinishGame(e->{
            if (boardService.gameIsFinished()){
                JOptionPane.showMessageDialog(null, "Parabéns, você concluiu o jogo");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishedButton.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "Seu jogo tem alguma inconsistência, ajuste e tente novamente");
            }
        });

        mainPanel.add(finishedButton);
    }

    private void addShowGameStatusButton(JPanel mainPanel) {

        checkGameStatusButton = new buttonCheckGameStatus(e->{
            var hasErrors = boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var message = switch(gameStatus){
                case COMPLETE -> "O jogo está completo";
                case NON_STARTED -> "o jogo não foi iniciado";
                case INCOMPLETE -> "o jogo está incompleto";
            };
            message += hasErrors ? " e contém erros" : " e não contém erros";
            JOptionPane.showMessageDialog(null,message);
        });
        mainPanel.add(checkGameStatusButton);
    }

    private void addResetButton(JPanel mainPanel) {
        resetButton = new buttonReset(e-> {
            var dialogueResult = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja realmente reiniciar o jogo?",
                    "Limpar o jogo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (dialogueResult==0){
                boardService.reset();
            }
        });
        mainPanel.add(resetButton);
    }
}
