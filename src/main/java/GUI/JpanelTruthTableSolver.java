package GUI;

import TruthTable.TruthTableSolve;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import TruthTable.PreParser;
import TruthTable.TruthTable;
import TruthTableSolver.gui.ButtonEditor;
import TruthTableSolver.gui.ButtonRenderer;
import TruthTableSolver.gui.RowHeaderRenderer;
import com.bpodgursky.jbool_expressions.parsers.ExprParser;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public final class JpanelTruthTableSolver extends JPanel {

    public JScrollPane scroll;
    public static int number_of_terms;
    public static ArrayList<Integer> values;
     String[] terms_names;
    static JEditorPane editorPane = new JEditorPane();
    static int sum_of_products_or_product_of_sums;
    JTable main_table;
    JScrollPane main_table_scroll;
    TableModel main_table_dataModel;
    AbstractTableModel x;

    public JpanelTruthTableSolver() {
        this.setLayout(new GridBagLayout());
        terms_names = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
        number_of_terms = 2;
        sum_of_products_or_product_of_sums = 0;
        values = new ArrayList<>();
        setAndClearValuesArray();
        createGui();
        editorPane.setText("Genera una funcion a partir de una tabla de verdad\n"
                + "Acepta terminos indiferentes(x)\n"
                + "Establezca la cantidad de variable de entrada\n"
                + "Haga click y elija la salida correspondiente para la fila elegida\n"
                + "Eliga el metodo de representación (Maxierminos o Miniterminos)");
    }

    public void createGui() {
        ListModel main_table_listModel = new AbstractListModel() {
            @Override
            public int getSize() {
                return (int) Math.pow(2, number_of_terms);
            }

            @Override
            public Object getElementAt(int index) {
                return index;
            }
        };
        main_table_dataModel = x = new AbstractTableModel() {
            private Object[][] data = getData(number_of_terms + 1);

            @Override
            public int getColumnCount() {
                data = getData(number_of_terms + 1);
                return number_of_terms + 1;
            }

            @Override
            public int getRowCount() {
                return (int) Math.pow(2, number_of_terms);
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 0;
            }

            @Override
            public void setValueAt(Object value, int row, int col) {
                data[row][col] = value;
                fireTableCellUpdated(row, col);
            }

            @Override
            public Object getValueAt(int row, int col) {
                return data[row][col];
            }

            @Override
            public String getColumnName(int col) {
                if (col == 0) {
                    return "Salida";
                }
                return terms_names[col - 1];
            }
        };

        main_table = new JTable(main_table_dataModel);
        main_table.getColumn("Salida").setCellRenderer(new ButtonRenderer());
        main_table.getColumn("Salida").setCellEditor(new ButtonEditor(new JCheckBox()));

        final JList main_table_rowHeader = new JList(main_table_listModel);
        main_table_rowHeader.setFixedCellWidth(50);
        main_table_rowHeader.setFixedCellHeight(main_table.getRowHeight() + main_table.getRowMargin() - 1);
        main_table_rowHeader.setCellRenderer(new RowHeaderRenderer(main_table));

        main_table_scroll = new JScrollPane(main_table);
        main_table_scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, main_table.getTableHeader());
        main_table_scroll.setRowHeaderView(main_table_rowHeader);
        main_table_scroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        main_table_scroll.setPreferredSize(new Dimension(200, 0));

        JButton clear_button = new JButton("Reiniciar salida");
        editorPane.setEditable(false);
        editorPane.setComponentPopupMenu(new PopMenu());
        JScrollPane editor_pane_scroll = new JScrollPane(editorPane);
        editor_pane_scroll.setPreferredSize(new Dimension(0, 40));

        SpinnerModel smodel
                = new SpinnerNumberModel(
                        number_of_terms, 
                        2, 
                        8, 
                        1
                );
        final JSpinner spinner = new JSpinner(smodel);
        spinner.setName("number of terms");
        spinner.setFont(new Font("Dialog", Font.BOLD, 15));

        JRadioButton sum_of_products = new JRadioButton("Miniterminos");
        sum_of_products.setSelected(true);

        JRadioButton product_of_sums = new JRadioButton("Maxiterminos");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(sum_of_products);
        buttonGroup.add(product_of_sums);

        spinner.addChangeListener((ChangeEvent e) -> {
            number_of_terms = Integer.parseInt(spinner.getValue().toString());
            setAndClearValuesArray();
            main_table.updateUI();
            x.fireTableStructureChanged();
            main_table.getColumn("Salida").setCellRenderer(new ButtonRenderer());
            main_table.getColumn("Salida").setCellEditor(new ButtonEditor(new JCheckBox()));
            main_table_rowHeader.updateUI();
            main_table_scroll.updateUI();

            if (!editorPane.getText().contains("Genera una")) {
                editorPane.setText("");
            }
        });

        clear_button.addActionListener((ActionEvent e) -> {
            setAndClearValuesArray();
            x.fireTableStructureChanged();
            main_table.getColumn("Salida").setCellRenderer(new ButtonRenderer());
            main_table.getColumn("Salida").setCellEditor(new ButtonEditor(new JCheckBox()));
            editorPane.setText("");
        });

        sum_of_products.addActionListener((ActionEvent e) -> {
            sum_of_products_or_product_of_sums = 0;
            solveTable();
        });

        product_of_sums.addActionListener((ActionEvent e) -> {
            sum_of_products_or_product_of_sums = 1;
            editorPane.setText("Solucionando...");
            Thread thread = new Thread(() -> {
                solveTable();
            });
            if (editorPane.getText().contains("Sol")) {
                thread.start();
            }
        });

        GridBagConstraints c1 = new GridBagConstraints();
        c1.fill = GridBagConstraints.BOTH;
        c1.gridx = 0;
        c1.gridy = 0;
        c1.weightx = 1.0;
        c1.weighty = 0.5;
        c1.gridheight = 11;
        c1.gridwidth = 1;
        c1.anchor = GridBagConstraints.CENTER;
        this.add(main_table_scroll, c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 1;
        c2.gridy = 5;
        c2.weightx = 0.1;
        c2.weighty = 0.5;
        c2.anchor = GridBagConstraints.CENTER;
        this.add(spinner, c2);

        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 1;
        c3.gridy = 4;
        c3.weightx = 0.1;
        c3.weighty = 0.5;
        c3.anchor = GridBagConstraints.LAST_LINE_END;
        this.add(new JLabel("Cantidad de variables"), c3);

        GridBagConstraints c4 = new GridBagConstraints();
        c4.gridx = 1;
        c4.gridy = 9;
        c4.weightx = 0.1;
        c4.weighty = 0.5;
        c4.anchor = GridBagConstraints.CENTER;
        this.add(clear_button, c4);

        GridBagConstraints c5 = new GridBagConstraints();
        c5.fill = GridBagConstraints.BOTH;
        c5.gridx = 0;
        c5.gridy = 11;
        c5.weightx = 0.5;
        c5.weighty = 1.0;
        c5.gridwidth = 3;
        this.add(editor_pane_scroll, c5);

        GridBagConstraints c6 = new GridBagConstraints();
        c6.gridx = 1;
        c6.gridy = 6;
        c6.weightx = 0.1;
        c6.weighty = 0.5;
        c6.anchor = GridBagConstraints.LAST_LINE_START;
        this.add(sum_of_products, c6);

        GridBagConstraints c7 = new GridBagConstraints();
        c7.gridx = 1;
        c7.gridy = 7;
        c7.weightx = 0.1;
        c7.weighty = 0.5;
        c7.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(product_of_sums, c7);
    }

    Object[][] getData(int number_of_terms) {
        int col = number_of_terms;
        int row = (int) Math.pow(2, col - 1);
        Object[][] temp = new Object[row][col];
        int aux;
        for (int i = 0; i < row; i++) {
            switch (values.get(i)) {
                case 0:temp[i][0] = "0";break;
                case 1:temp[i][0] = "1";break;
                case 2:temp[i][0] = "x";break;
                default:break;
            }
            aux = i;
            for (int j = col - 1; j > 0; j--) {
                temp[i][j] = aux % 2;
                aux /= 2;
            }
        }
        return temp;
    }

    public static void setAndClearValuesArray() {
        values = new ArrayList<>();
        int q = (int) Math.pow(2, JpanelTruthTableSolver.number_of_terms);
        for (int i = 0; i < q; i++) {
            values.add(0);
        }
    }

    public static void setAnswer(String solution) {
        editorPane.setText(PreParser.expresiontoString(solution).replace("*", ""));
    }

    public static void solveTable() {
        try {        
            TruthTableSolve solve = new TruthTableSolve(TruthTable.getTable(values), TruthTable.setVarNames(values.size()));
                String solMin = TruthTableSolve.solveMin(solve.solveMinWithOutX());
                String solMinX = TruthTableSolve.solveMin(solve.solveMinWithX());
            if (sum_of_products_or_product_of_sums == 0) {
                setAnswer((solMin.length() <= solMinX.length())?solMin:solMinX);
                JPanelReduction.setActualExpression(editorPane.getText());
                JPanelReduction.setActualMinExpression(editorPane.getText());
                JPanelReduction.setActualMaxExpression("");
            } else {
                String solX = RuleSet.toCNF(ExprParser.parse(PreParser.parseToExpresion(solMinX))).toString();
                String sol = RuleSet.toCNF(ExprParser.parse(PreParser.parseToExpresion(solMin))).toString();
                setAnswer((sol.length() <= solX.length())?sol:solX);
                JPanelReduction.setActualExpression(editorPane.getText());
                JPanelReduction.setActualMaxExpression(editorPane.getText());
                JPanelReduction.setActualMinExpression("");
            }
        } catch (Exception ex) {
            setAnswer("Indetermindo");
            JPanelReduction.resetExpression();
        }
    }
}
