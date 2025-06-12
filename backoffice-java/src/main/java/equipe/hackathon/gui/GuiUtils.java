package equipe.hackathon.gui;

import java.awt.*;

public class    GuiUtils {
    GridBagConstraints montarConstraints( int x, int y) {
        var constraint = new GridBagConstraints();
        constraint.insets = new Insets(5, 5, 5, 5);
        constraint.gridx = x;
        constraint.gridy = y;
        return constraint;
    }


}