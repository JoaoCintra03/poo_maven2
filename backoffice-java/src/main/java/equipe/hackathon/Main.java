package equipe.hackathon;

import equipe.hackathon.gui.EventoGui;
import equipe.hackathon.gui.PalestranteGui;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::iniciar);
    }

    private static void iniciar() {
        // Menu principal
        JFrame menu = new JFrame("Sistema de Eventos");
        menu.setSize(300, 200);
        menu.setLocationRelativeTo(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(3, 1));

        JButton btnEventos = new JButton("Gerenciar Eventos");
        btnEventos.addActionListener(e -> {
            new EventoGui().setVisible(true);
        });

        JButton btnPalestrantes = new JButton("Gerenciar Palestrantes");
        btnPalestrantes.addActionListener(e -> {
            new PalestranteGui().setVisible(true);
        });

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> System.exit(0));

        painel.add(btnEventos);
        painel.add(btnPalestrantes);
        painel.add(btnSair);

        menu.add(painel);
        menu.setVisible(true);
    }
}
