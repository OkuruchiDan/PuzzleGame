import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClickAction extends AbstractAction {
    private final ArrayList<PuzzleButton> buttons;
    private final JPanel panel;
    private final ArrayList<PuzzleButton> solution;

    public ClickAction(ArrayList<PuzzleButton> buttons, JPanel panel, ArrayList<PuzzleButton> solution) {
        this.buttons = buttons;
        this.panel = panel;
        this.solution = solution;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        checkButton(e);
        checkSolution();
    }

    private void checkButton(ActionEvent e) {
        //searching for clicked button
        List<PuzzleButton> selectedButtons = buttons.stream()
                .filter(PuzzleButton::isSelectedButton)
                .collect(Collectors.toList());
        PuzzleButton currentButton = (PuzzleButton) e.getSource();// getting current button
        currentButton.setSelectedButton(true);
        int currentButtonIndex = buttons.indexOf(currentButton);
        //if no button are selected, do not update collection of PuzzleButton
        if (selectedButtons.size() == 1) {
            int lastClickedButtonIndex = 0;

            for (PuzzleButton button : buttons) {
                if (button.isSelectedButton() && !button.equals(currentButton)) {
                    lastClickedButtonIndex = buttons.indexOf(button);
                }
            }
            Collections.swap(buttons, lastClickedButtonIndex, currentButtonIndex);
            updateButtons();
        }
    }

    private void updateButtons() {
        panel.removeAll();
        for (PuzzleButton button : buttons) {
            button.setSelectedButton(false);
            button.setBorder(BorderFactory.createLineBorder(Color.gray));;
            panel.add(button);
        }
        panel.validate();
    }

    private void checkSolution() {
        ArrayList<PuzzleButton> current = new ArrayList<>(buttons);

        if (compareList(solution, current)) {
            JOptionPane.showMessageDialog(panel, "Finished", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static boolean compareList(List list1, List list2) {
        return list1.toString().contentEquals(list2.toString());
    }
}

