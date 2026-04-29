package ui.custom.screen;

import model.Space;
import service.BoardService;
import service.EventEnum;
import service.NotifierService;
import ui.custom.Input.NumberText;
import ui.custom.button.buttonCheckGameStatus;
import ui.custom.button.buttonFinishGame;
import ui.custom.button.buttonReset;
import ui.custom.frame.MainFrame;
import ui.custom.panel.MainPanel;
import ui.custom.panel.SudokuSector;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream.*;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static service.EventEnum.CLEAR_SPACE;

public class MainScreen {

    private final static Dimension dimension = new Dimension(600, 600);

    private final BoardService boardService;
    private final NotifierService notifierService;

    private JButton finishedButton;
    private JButton checkGameStatusButton;
    private JButton resetButton;

    public MainScreen(final Map<String,String> gameConfig){
        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen(){
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        for (int r = 0 ; r < 9 ; r+=3){
            var endrow = r + 2;
            for (int c = 0 ; c < 9 ; c+=3) {
                var endcolumn = c + 2;
                var spaces = getSpacesFromSector(boardService.getSpaces(), c , endcolumn, r , endrow);
                JPanel sector = generateSection(spaces);
                mainPanel.add(sector);
            }
        }
        addResetButton(mainPanel);
        addShowGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private List<Space> getSpacesFromSector (List<List<Space>> spaces,
                                             final int initCol, final int endCol,
                                             final int initRow, final int endRow){
        List<Space> spacesSector = new ArrayList<>();
        for (int r= initRow ; r <= endRow; r++){
            for (int c= initCol ; c <= endCol; c++){
                spacesSector.add(spaces.get(c).get(r));
            }
        }
        return spacesSector;
    }

    private JPanel generateSection(final List<Space> spaces){
        List<NumberText> fields = new ArrayList<>(spaces.stream().map(NumberText::new).toList());
        fields.forEach(t-> notifierService.subscriber(CLEAR_SPACE,t));
        return new SudokuSector(fields);
    }
    private void addFinishGameButton(JPanel mainPanel) {
        finishedButton = new buttonFinishGame(e->{
            if (boardService.gameIsFinished()){
                showMessageDialog(null, "Parabéns, você concluiu o jogo");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishedButton.setEnabled(false);

            } else {
                showMessageDialog(null, "Seu jogo tem alguma inconsistência, ajuste e tente novamente");
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
            showMessageDialog(null,message);
        });
        mainPanel.add(checkGameStatusButton);
    }

    private void addResetButton(JPanel mainPanel) {
        resetButton = new buttonReset(e-> {
            var dialogueResult = showConfirmDialog(
                    null,
                    "Deseja realmente reiniciar o jogo?",
                    "Limpar o jogo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (dialogueResult==0){
                boardService.reset();
                notifierService.notify(CLEAR_SPACE);
            }
        });
        mainPanel.add(resetButton);
    }
}
