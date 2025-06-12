package equipe.hackathon.gui;

import equipe.hackathon.model.Evento;
import equipe.hackathon.service.EventoService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EventoGui extends JFrame {

    private JTextField tfID;
    private JTextField tfTitulo;
    private JTextField tfDescricao;
    private JTextField tfLugar;
    private JTextField tfDataHora;
    private JTextField tfCurso;

    private JTable tbEventos;

    private final EventoService service = new EventoService();

    public EventoGui() {
        setTitle("Cadastro de Evento");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().add(montarPainelEntrada(), BorderLayout.NORTH);
        getContentPane().add(montarPainelSaida(), BorderLayout.CENTER);
    }

    private JPanel montarPainelEntrada() {
        JPanel painel = new JPanel(new GridBagLayout());
        GuiUtils guiUtils = new GuiUtils();

        JLabel jlID = new JLabel("Palestrante");
        tfID = new JTextField(20);
        tfID.setEditable(false);
        JLabel jlTitulo = new JLabel("Titulo da Palestra");
        tfTitulo = new JTextField(20);
        JLabel jlDescricao = new JLabel("Descrição");
        tfDescricao = new JTextField(20);
        JLabel jlLugar = new JLabel("Lugar");
        tfLugar = new JTextField(20);
        JLabel jlDataHora = new JLabel("Data e Hora");
        tfDataHora = new JTextField(20);
        JLabel jlCurso = new JLabel("Curso");
        tfCurso = new JTextField(20);
        JButton btConfirmar = new JButton("Confirmar");
        btConfirmar.addActionListener(this::confirmar);

        painel.add( jlID, guiUtils.montarConstraints(0,0));
        painel.add(tfID, guiUtils.montarConstraints(1,0));
        painel.add(jlTitulo, guiUtils.montarConstraints(0,1));
        painel.add(tfTitulo, guiUtils.montarConstraints(1,1));
        painel.add(jlDescricao, guiUtils.montarConstraints(0,2));
        painel.add(tfDescricao, guiUtils.montarConstraints(1,2));
        painel.add(jlLugar, guiUtils.montarConstraints(0,3));
        painel.add(tfLugar, guiUtils.montarConstraints(1,3));
        painel.add(jlDataHora, guiUtils.montarConstraints(0,4));
        painel.add(tfDataHora, guiUtils.montarConstraints(1,4));
        painel.add(jlCurso, guiUtils.montarConstraints(0,5));
        painel.add(tfCurso, guiUtils.montarConstraints(1,5));
        painel.add(btConfirmar, guiUtils.montarConstraints(0,6));

        return painel;
    }

    private void confirmar(ActionEvent event) {
        var evento = new Evento();
        evento.setId(Math.toIntExact(tfID.getText().isEmpty() ? null : Long.valueOf(tfID.getText())));
        evento.setTitulo(tfTitulo.getText());
        evento.setDescricao(tfDescricao.getText());
        evento.setLugar(tfLugar.getText());
        evento.setDataHora(tfDataHora.getText());
        evento.setCurso(tfCurso.getText());

        var servico = new EventoService();
        servico.cadastrarEvento(evento);

        limparCampos();
    }

    private void limparCampos() {
        tfID.setText(null);
        tfTitulo.setText(null);
        tfDescricao.setText(null);
        tfLugar.setText(null);
        tfDataHora.setText(null);
        tfCurso.setText(null);
    }

    private JScrollPane montarPainelSaida() {
        var tableModel = new DefaultTableModel();
        tableModel.addColumn("Id_Palestrante");
        tableModel.addColumn("Título da Palestra");
        tableModel.addColumn("Curso");
        tableModel.addColumn("Data/Hora");

        service.listarEventos().forEach(e ->
                tableModel.addRow(new Object[]{
                        e.getId(),
                        e.getTitulo(),
                        e.getCurso(),
                        String.format(String.valueOf(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                })
        );


        tbEventos = new JTable(tableModel);
        tbEventos.setDefaultEditor(Object.class, null);
        tbEventos.getSelectionModel().addListSelectionListener(this::selecionar);

        return new JScrollPane(tbEventos);
    }


    private void selecionar(ListSelectionEvent listSelectionEvent) {
        var linha = tbEventos.getSelectedRow();
        tfID.setText(tbEventos.getValueAt(linha,0).toString());
        tfTitulo.setText(tbEventos.getValueAt(linha,1).toString());
        tfDescricao.setText(tbEventos.getValueAt(linha,2).toString());
        tfLugar.setText(tbEventos.getValueAt(linha,4).toString());
        tfDataHora.setText(tbEventos.getValueAt(linha,5).toString());
        tfCurso.setText(tbEventos.getValueAt(linha,6).toString());
    }

}


