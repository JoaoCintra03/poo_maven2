package equipe.hackathon.gui;

import equipe.hackathon.model.Evento;
import equipe.hackathon.service.EventoService;
import equipe.hackathon.service.PalestranteService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;

public class EventoGui extends JFrame {
    private JTextField tfID;
    private JTextField tfTitulo;
    private JTextField tfDescricao;
    private JTextField tfLugar;
    private JTextField tfDataHora;
    private JTextField tfCurso;
    private JComboBox<String> cbPalestrante;
    private JTable tbEventos;
    private final EventoService eventoService = new EventoService();
    private final PalestranteService palestranteService = new PalestranteService();

    public EventoGui() {
        setTitle("Cadastro de Evento");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().add(montarPainelEntrada(), BorderLayout.NORTH);
        getContentPane().add(montarPainelSaida(), BorderLayout.CENTER);
        getContentPane().add(montarPainelBotoes(), BorderLayout.SOUTH);
    }

    private JPanel montarPainelEntrada() {
        JPanel painel = new JPanel(new GridBagLayout());
        GuiUtils guiUtils = new GuiUtils();

        JLabel jlID = new JLabel("ID:");
        tfID = new JTextField(10);
        tfID.setEditable(false);

        JLabel jlTitulo = new JLabel("Título da Palestra:");
        tfTitulo = new JTextField(30);

        JLabel jlDescricao = new JLabel("Descrição:");
        tfDescricao = new JTextField(30);

        JLabel jlLugar = new JLabel("Lugar:");
        tfLugar = new JTextField(30);

        JLabel jlDataHora = new JLabel("Data e Hora (dd/MM/yyyy HH:mm):");
        tfDataHora = new JTextField(30);

        JLabel jlCurso = new JLabel("Curso:");
        tfCurso = new JTextField(30);

        JLabel jlPalestrante = new JLabel("Palestrante:");
        cbPalestrante = new JComboBox<>();
        carregarPalestrantes();

        painel.add(jlID, guiUtils.montarConstraints(0, 0));
        painel.add(tfID, guiUtils.montarConstraints(1, 0));
        painel.add(jlTitulo, guiUtils.montarConstraints(0, 1));
        painel.add(tfTitulo, guiUtils.montarConstraints(1, 1));
        painel.add(jlDescricao, guiUtils.montarConstraints(0, 2));
        painel.add(tfDescricao, guiUtils.montarConstraints(1, 2));
        painel.add(jlLugar, guiUtils.montarConstraints(0, 3));
        painel.add(tfLugar, guiUtils.montarConstraints(1, 3));
        painel.add(jlDataHora, guiUtils.montarConstraints(0, 4));
        painel.add(tfDataHora, guiUtils.montarConstraints(1, 4));
        painel.add(jlCurso, guiUtils.montarConstraints(0, 5));
        painel.add(tfCurso, guiUtils.montarConstraints(1, 5));
        painel.add(jlPalestrante, guiUtils.montarConstraints(0, 6));
        painel.add(cbPalestrante, guiUtils.montarConstraints(1, 6));

        return painel;
    }

    private void carregarPalestrantes() {
        cbPalestrante.removeAllItems();
        palestranteService.listarPalestrantes().forEach(p -> {
            cbPalestrante.addItem(p.getNome() + " (ID: " + p.getId() + ")");
        });
    }

    private JScrollPane montarPainelSaida() {
        var tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Título");
        tableModel.addColumn("Palestrante");
        tableModel.addColumn("Data/Hora");
        tableModel.addColumn("Curso");

        eventoService.listarEventos().forEach(e -> {
            tableModel.addRow(new Object[]{
                e.getId(),
                e.getTitulo(),
                e.getPalestranteNome(),
                e.getDataHora(),
                e.getCurso()
            });
        });

        tbEventos = new JTable(tableModel);
        tbEventos.setDefaultEditor(Object.class, null);
        tbEventos.getSelectionModel().addListSelectionListener(this::selecionar);

        return new JScrollPane(tbEventos);
    }

    private JPanel montarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnNovo = new JButton("Novo");
        btnNovo.addActionListener(e -> limparCampos());

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(this::confirmar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(this::excluir);

        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnExcluir);

        return painel;
    }

    private void confirmar(ActionEvent event) {
        var evento = new Evento();

        if (!tfID.getText().isEmpty()) {
            evento.setId(Integer.parseInt(tfID.getText()));
        }

        evento.setTitulo(tfTitulo.getText());
        evento.setDescricao(tfDescricao.getText());
        evento.setLugar(tfLugar.getText());
        evento.setDataHora(tfDataHora.getText());
        evento.setCurso(tfCurso.getText());

        // Obter ID do palestrante selecionado
        String palestranteSelecionado = (String) cbPalestrante.getSelectedItem();
        if (palestranteSelecionado != null && palestranteSelecionado.contains("ID:")) {
            int idPalestrante = Integer.parseInt(palestranteSelecionado.split("ID:")[1].replace(")", "").trim());
            evento.setPalestranteId(idPalestrante);
        }

        boolean sucesso;
        if (evento.getId() == 0) {
            sucesso = eventoService.cadastrarEvento(evento);
        } else {
            sucesso = eventoService.atualizarEvento(evento);
        }

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Evento salvo com sucesso!");
            atualizarTabela();
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar evento!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir(ActionEvent event) {
        if (tfID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja excluir este evento?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            long id = Long.parseLong(tfID.getText());
            if (eventoService.deletarEvento(id)) {
                JOptionPane.showMessageDialog(this, "Evento excluído com sucesso!");
                atualizarTabela();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir evento!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void selecionar(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) {
            int linha = tbEventos.getSelectedRow();
            if (linha >= 0) {
                long id = (long) tbEventos.getValueAt(linha, 0);
                Evento evento = eventoService.buscarEvento(id);

                if (evento != null) {
                    tfID.setText(String.valueOf(evento.getId()));
                    tfTitulo.setText(evento.getTitulo());
                    tfDescricao.setText(evento.getDescricao());
                    tfLugar.setText(evento.getLugar());
                    tfDataHora.setText(evento.getDataHora());
                    tfCurso.setText(evento.getCurso());

                    // Selecionar o palestrante correto no combobox
                    for (int i = 0; i < cbPalestrante.getItemCount(); i++) {
                        if (cbPalestrante.getItemAt(i).contains("ID: " + evento.getPalestranteId())) {
                            cbPalestrante.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void atualizarTabela() {
        var tableModel = (DefaultTableModel) tbEventos.getModel();
        tableModel.setRowCount(0);

        eventoService.listarEventos().forEach(e -> {
            tableModel.addRow(new Object[]{
                e.getId(),
                e.getTitulo(),
                e.getPalestranteNome(),
                e.getDataHora(),
                e.getCurso()
            });
        });
    }

    private void limparCampos() {
        tfID.setText("");
        tfTitulo.setText("");
        tfDescricao.setText("");
        tfLugar.setText("");
        tfDataHora.setText("");
        tfCurso.setText("");
        cbPalestrante.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EventoGui gui = new EventoGui();
            gui.setVisible(true);
        });
    }
}
