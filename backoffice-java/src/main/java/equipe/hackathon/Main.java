package equipe.hackathon;

import equipe.hackathon.gui.EventoGui;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::iniciar);

    }

    private static void iniciar() {
        var eventoGui = new EventoGui();
        eventoGui.setVisible(true);
    }
}
