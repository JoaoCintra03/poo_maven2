package equipe.hackathon.gui;

import equipe.hackathon.model.Palestrante;
import equipe.hackathon.service.PalestranteService;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PalestranteGui extends JFrame {
    private JTextField tfId;
    private JTextField tfNome;
    private JTextArea taDescricao;
    private JTextField tfFoto;
    private JTextField tfTema;

    private JTable tbPalestrantes;
    private final PalestranteService service = new PalestranteService();

    public PalestranteGui() {
        setTitle("Cadastro de Palestrantes");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().add(montarPainelEntrada(), BorderLayout.NORTH);
        getContentPane().add(montarPainelSaida(), BorderLayout.CENTER);
        getContentPane().add(montarPainelBotoes(), BorderLayout.SOUTH);

        carregarPalestrantes();
    }

    private JPanel montarPainelEntrada() {
        JPanel painel = new JPanel(new GridBagLayout());
        GuiUtils guiUtils = new GuiUtils();

        JLabel lblId = new JLabel("ID:");
        tfId = new JTextField(10);
        tfId.setEditable(false);

        JLabel lblNome = new JLabel("Nome:");
        tfNome = new JTextField(30);

        JLabel lblDescricao = new JLabel("Mini-currículo:");
        taDescricao = new JTextArea(5, 30);
        taDescricao.setLineWrap(true);
        JScrollPane scrollDescricao = new JScrollPane(taDescricao);

        JLabel lblFoto = new JLabel("Foto (URL):");
        tfFoto = new JTextField(30);

        JLabel lblTema = new JLabel("Tema:");
        tfTema = new JTextField(30);

        painel.add(lblId, guiUtils.montarConstraints(0, 0));
        painel.add(tfId, guiUtils.montarConstraints(1, 0));
        painel.add(lblNome, guiUtils.montarConstraints(0, 1));
        painel.add(tfNome, guiUtils.montarConstraints(1, 1));
        painel.add(lblDescricao, guiUtils.montarConstraints(0, 2));
        painel.add(scrollDescricao, guiUtils.montarConstraints(1, 2));
        painel.add(lblFoto, guiUtils.montarConstraints(0, 3));
        painel.add(tfFoto, guiUtils.montarConstraints(1, 3));
        painel.add(lblTema, guiUtils.montarConstraints(0, 4));
        painel.add(tfTema, guiUtils.montarConstraints(1, 4));

        return painel;
    }

    private JScrollPane montarPainelSaida() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Tema");
        model.addColumn("Mini-currículo");

        tbPalestrantes = new JTable(model);
        tbPalestrantes.setDefaultEditor(Object.class, null);
        tbPalestrantes.getSelectionModel().addListSelectionListener(this::selecionarPalestrante);

        return new JScrollPane(tbPalestrantes);
    }

    private JPanel montarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnNovo = new JButton("Novo");
        btnNovo.addActionListener(this::novoPalestrante);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(this::salvarPalestrante);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(this::excluirPalestrante);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());

        painel.add(btnNovo);
        painel.add(btnSalvar);
        painel.add(btnExcluir);
        painel.add(btnLimpar);

        return painel;
    }

    private void carregarPalestrantes() {
        DefaultTableModel model = (DefaultTableModel) tbPalestrantes.getModel();
        model.setRowCount(0); // Limpa a tabela

        service.listarPalestrantes().forEach(p -> {
            model.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getTema(),
                p.getDescricao().length() > 50 ?
                    p.getDescricao().substring(0, 50) + "..." :
                    p.getDescricao()
            });
        });
    }

    private void selecionarPalestrante(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int linha = tbPalestrantes.getSelectedRow();
            if (linha >= 0) {
                int id = (int) tbPalestrantes.getValueAt(linha, 0);
                Palestrante p = service.buscarPalestrante(id);

                if (p != null) {
                    tfId.setText(String.valueOf(p.getId()));
                    tfNome.setText(p.getNome());
                    taDescricao.setText(p.getDescricao());
                    tfFoto.setText(p.getFoto());
                    tfTema.setText(p.getTema());
                }
            }
        }
    }

    private void novoPalestrante(ActionEvent e) {
        limparCampos();
    }

    private void salvarPalestrante(ActionEvent e) {
        Palestrante p = new Palestrante();

        if (!tfId.getText().isEmpty()) {
            p.setId(Integer.parseInt(tfId.getText()));
        }

        p.setNome(tfNome.getText());
        p.setDescricao(taDescricao.getText());
        p.setFoto(tfFoto.getText());
        p.setTema(tfTema.getText());

        boolean sucesso;
        if (p.getId() == 0) {
            sucesso = service.cadastrarPalestrante(p);
        } else {
            sucesso = service.atualizarPalestrante(p);
        }

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Palestrante salvo com sucesso!");
            carregarPalestrantes();
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar palestrante!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirPalestrante(ActionEvent e) {
        if (tfId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um palestrante para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja excluir este palestrante?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(tfId.getText());
            if (service.deletarPalestrante(id)) {
                JOptionPane.showMessageDialog(this, "Palestrante excluído com sucesso!");
                carregarPalestrantes();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir palestrante!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        tfId.setText("");
        tfNome.setText("");
        taDescricao.setText("");
        tfFoto.setText("");
        tfTema.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PalestranteGui gui = new PalestranteGui();
            gui.setVisible(true);
        });
    }
}
