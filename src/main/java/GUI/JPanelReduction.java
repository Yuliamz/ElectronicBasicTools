package GUI;

import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.parsers.ExprParser;
import com.bpodgursky.jbool_expressions.rules.RuleSet;
import TruthTable.PreParser;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Set;
import javax.script.ScriptException;
import karnaughMap.KarnaughMap;
import LogicCiruitDraw.DrawPLA_DNF;
import LogicCiruitDraw.ExpressionDrawerParser;
import LogicCiruitDraw.DialogLogicalCircuit;
import LogicCiruitDraw.DrawPLA_KNF;
import LogicCiruitDraw.PLACanvasDNF;
import LogicCiruitDraw.PLACanvasKNF;
import TruthTable.TruthTable;
import TruthTable.TruthTableSolve;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.text.html.HTML;

public class JPanelReduction extends javax.swing.JPanel {

    private Thread thread;
    private static String actualExpression;
    private static String actualMinExpression;
    private static String actualMaxExpression;
    private JFrame frame;

    public JPanelReduction(JFrame frame) {
        this.frame = frame;
        initComponents();        
        resetExpression();
        resetExpression();
        try {
            TruthTable.getTable("A+!A");
        } catch (ScriptException ex) {
            Logger.getLogger(JPanelReduction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldExpression = new javax.swing.JTextField();
        jButtonTruthTable = new javax.swing.JButton();
        jButtonMax = new javax.swing.JButton();
        jButtonMin = new javax.swing.JButton();
        jButtonKarnaugh = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabelSolution = new javax.swing.JEditorPane();
        jButtonDNF = new javax.swing.JButton();
        jButtonKNF = new javax.swing.JButton();

        setBackground(new java.awt.Color(209, 209, 209));
        setPreferredSize(new java.awt.Dimension(475, 370));

        jTextFieldExpression.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldExpression.setBorder(null);
        jTextFieldExpression.setComponentPopupMenu(new PopMenu());
        jTextFieldExpression.setDisabledTextColor(java.awt.Color.white);
        jTextFieldExpression.setSelectedTextColor(java.awt.Color.black);
        jTextFieldExpression.setSelectionColor(new java.awt.Color(102, 204, 255));

        jButtonTruthTable.setBackground(java.awt.Color.lightGray);
        jButtonTruthTable.setText("Tabla de Activación");
        jButtonTruthTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTruthTableActionPerformed(evt);
            }
        });

        jButtonMax.setBackground(java.awt.Color.lightGray);
        jButtonMax.setText("Maxiterminos");
        jButtonMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMaxActionPerformed(evt);
            }
        });

        jButtonMin.setBackground(java.awt.Color.lightGray);
        jButtonMin.setText("Miniterminos");
        jButtonMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMinActionPerformed(evt);
            }
        });

        jButtonKarnaugh.setBackground(java.awt.Color.lightGray);
        jButtonKarnaugh.setText("Mapa de Karnaugh");
        jButtonKarnaugh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonKarnaughActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(null);

        jLabelSolution.setEditable(false);
        jLabelSolution.setBorder(null);
        jLabelSolution.setText("NOT: ! \nAND: * \nOR: +\n1 y 0 Estan disponibles\n\nEjemplo: !A!B!C!D+!ACD+!ABC*!1+A!BD+CD+ABCD*0");
        jLabelSolution.setComponentPopupMenu(new PopMenu());
        jLabelSolution.setDisabledTextColor(java.awt.Color.white);
        jLabelSolution.setSelectedTextColor(java.awt.Color.black);
        jLabelSolution.setSelectionColor(new java.awt.Color(102, 153, 255));
        jScrollPane1.setViewportView(jLabelSolution);

        jButtonDNF.setText("Compuerta AND");
        jButtonDNF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDNFActionPerformed(evt);
            }
        });

        jButtonKNF.setText("Compuerta OR");
        jButtonKNF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonKNFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonKarnaugh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonTruthTable, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonDNF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonKNF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonMin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonMax, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 8, Short.MAX_VALUE)
                        .addComponent(jTextFieldExpression, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jTextFieldExpression, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonTruthTable)
                    .addComponent(jButtonMax)
                    .addComponent(jButtonDNF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonMin)
                    .addComponent(jButtonKarnaugh)
                    .addComponent(jButtonKNF))
                .addGap(34, 34, 34)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents


    private void jButtonTruthTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTruthTableActionPerformed
        String text = jTextFieldExpression.getText().replaceAll(" +", "");
        if (!text.isEmpty()) {
            try {
                showDialog(new JDialogTruthTable(frame, jTextFieldExpression.getText()));
                setActualExpression(jTextFieldExpression.getText().trim());
            } catch (RuntimeException | ScriptException ex) {invalidExpresion(ex);}
        } else {
            jLabelSolution.setText("Ingrese una expresión");
        }

    }//GEN-LAST:event_jButtonTruthTableActionPerformed

    private void jButtonMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMaxActionPerformed
        String text = jTextFieldExpression.getText().trim().replaceAll(" +", "");
        if (!text.isEmpty()) {
                jLabelSolution.setText("Calculando...");
                stopThread();
                if (text.equals(actualExpression) && !"".equals(actualMaxExpression)) {
                    jLabelSolution.setText(actualMaxExpression);
                } else {
                    thread = new Thread(() -> {
                        calculateMax();
                    });
                    startThread("Calc");
                }
            
        } else {
            jLabelSolution.setText("Ingrese una expresión");
        }
    }//GEN-LAST:event_jButtonMaxActionPerformed

    private void jButtonMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMinActionPerformed
        String text = jTextFieldExpression.getText().trim().replaceAll(" +", " ");
        if (!text.isEmpty()) {
            jLabelSolution.setText("Calculando...");
            stopThread();
            if (text.equals(actualExpression) && !"".equals(actualMinExpression)) {
                jLabelSolution.setText(actualMinExpression);
            } else {
                thread = new Thread(() -> {
                    calculateMin();
                });
                startThread("Calc");
            }

        } else {
            jLabelSolution.setText("Ingrese una expresión");
        }
    }//GEN-LAST:event_jButtonMinActionPerformed

    private void jButtonKarnaughActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonKarnaughActionPerformed
        try {   
            String expressionS = TruthTable.parseExpression(jTextFieldExpression.getText());
            if (TruthTable.getVariables(expressionS).size() > 1) {
                JDialogKarnaughMap dialog = new JDialogKarnaughMap(frame, new KarnaughMap(TruthTable.getVariables(expressionS), TruthTable.getTable(expressionS)));
                locateKarnaughMap(dialog);
                setActualExpression(jTextFieldExpression.getText().trim());
            } else {
                jLabelSolution.setText("Deben haber mínimo dos variables de entrada");
            }
        } catch (ScriptException | RuntimeException ex) {
            invalidExpresion(ex);
        }
    }//GEN-LAST:event_jButtonKarnaughActionPerformed

    private void jButtonDNFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDNFActionPerformed
        String text = jTextFieldExpression.getText().trim().replaceAll(" +", " ");
        if (!text.isEmpty()) {
            jLabelSolution.setText("Dibujando...");
            stopThread();
            if (text.equals(actualExpression) && !actualMinExpression.equals("")) {
                drawDNF(actualMinExpression);
            } else {
                thread = new Thread(() -> {
                    drawDNF();
                });
                startThread("Dibu");
            }
        } else {
            jLabelSolution.setText("Ingrese una expresión");
        }
    }//GEN-LAST:event_jButtonDNFActionPerformed

    private void jButtonKNFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonKNFActionPerformed
        String text = jTextFieldExpression.getText().trim().replaceAll(" +", " ");
        if (!text.isEmpty()) {
            jLabelSolution.setText("Dibujando...");
                stopThread();
                if (text.equals(actualExpression) && !actualMaxExpression.equals("")) {
                    drawKNF(actualMaxExpression);
                } else {
                    thread = new Thread(() -> {drawKNF();});
                    startThread("Dibu");
                }
        } else {
            jLabelSolution.setText("Ingrese una expresión");
        }
    }//GEN-LAST:event_jButtonKNFActionPerformed

    private void invalidExpresion(Exception e) {
        jLabelSolution.setContentType("text/html");
        jLabelSolution.setText("<html><body><Strong>Expresión invalida</Strong><br><br><Strong>Información:</Strong><br>" + e.getMessage() + "</body></html>");
        resetExpression();
        e.printStackTrace();
    }

    public static void resetExpression() {
        setActualExpression("");
        setActualMinExpression("");
        setActualMaxExpression("");
    }

    private void stopThread() {
        if (thread != null) {
            thread.stop();
        }
    }

    private void startThread(String textContain) {
        if (PreParser.getCleanExpression(jLabelSolution.getText()).contains(textContain)) {
            thread.start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/img/JPanelReduction.jpg")).getImage(), 0, 0, null);
    }

    private void showDialog(JDialog dialog) {
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void ubicateDialogPLA(DialogLogicalCircuit dialogPLA, String[] var) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        if (dialogPLA.getWidth() >= dimension.getWidth() || dialogPLA.getHeight() >= dimension.getHeight() - 60) {
            dialogPLA.setSize((var.length * 62) + 150, dialogPLA.getHeight() - 60);
            dialogPLA.setVisible(true);
        } else {
            dialogPLA.setSize((var.length * 55) + 150, (ExpressionDrawerParser.getAl().size() * 30) + 150);
            showDialog(dialogPLA);
        }
    }

    private void calculateMax() {
        try {
            Expression<String> posForm = RuleSet.toCNF(ExprParser.parse(PreParser.parseToExpresion(jTextFieldExpression.getText())));
            this.jLabelSolution.setText(PreParser.expresiontoString(posForm.toString().toUpperCase()));
            actualExpression = jTextFieldExpression.getText().trim();
            actualMaxExpression = PreParser.getCleanExpression(jLabelSolution.getText());
            actualMinExpression = "";
        } catch (Exception e) {
            invalidExpresion(e);
        }
    }

    private void calculateMin() {
        try {
            String text = TruthTable.parseExpression(jTextFieldExpression.getText());
            if (TruthTable.getVariables(text).size() <= 7) {
                jLabelSolution.setText(TruthTableSolve.solveMin(jTextFieldExpression.getText()));
            } else {
                jLabelSolution.setText(PreParser.expresiontoString(RuleSet.toDNF(ExprParser.parse(RuleSet.toCNF(ExprParser.parse(PreParser.parseToExpresion(jTextFieldExpression.getText()))).toString())).toString()).replace("*", ""));
            }
            actualExpression = jTextFieldExpression.getText().trim();
            actualMinExpression = PreParser.getCleanExpression(jLabelSolution.getText());
            actualMaxExpression = "";
        } catch (Exception e) {
            invalidExpresion(e);
        }
    }

    private void drawDNF() {
        try {
            String exp = (TruthTable.getVariables(PreParser.parseToExpresion(jTextFieldExpression.getText())).size() > 7) ? PreParser.expresiontoString(RuleSet.toDNF(ExprParser.parse(RuleSet.toCNF(ExprParser.parse(PreParser.parseToExpresion(jTextFieldExpression.getText()))).toString())).toString()) : TruthTableSolve.solveMin(jTextFieldExpression.getText());
            Set<Character> vars = TruthTable.getVariables(exp);
            ExpressionDrawerParser.createArrayDNF(exp, vars);
            String[] var = TruthTable.getStringVar(vars);
            jLabelSolution.setText(exp);
            DialogLogicalCircuit dialogPLA = new DialogLogicalCircuit(exp, null);
            PLACanvasDNF canvasDNF = new PLACanvasDNF(new DrawPLA_DNF(var));
            canvasDNF.setPreferredSize(new Dimension((var.length * 50) + 112, (ExpressionDrawerParser.getAl().size() * 30) + 125));
            dialogPLA.setPLADNF(canvasDNF);
            ubicateDialogPLA(dialogPLA, var);
            actualExpression = jTextFieldExpression.getText().trim();
            actualMinExpression = PreParser.getCleanExpression(jLabelSolution.getText());
            actualMaxExpression = "";
        } catch (Exception e) {
            invalidExpresion(e);
        }
    }

    private void drawDNF(String minExpression) {
        try {
            Set<Character> vars = TruthTable.getVariables(minExpression);
            ExpressionDrawerParser.createArrayDNF(minExpression, vars);
            String[] var = TruthTable.getStringVar(vars);
            jLabelSolution.setText(minExpression);
            DialogLogicalCircuit dialogPLA = new DialogLogicalCircuit(minExpression, null);
            PLACanvasDNF canvasDNF = new PLACanvasDNF(new DrawPLA_DNF(var));
            canvasDNF.setPreferredSize(new Dimension((var.length * 50) + 112, (ExpressionDrawerParser.getAl().size() * 30) + 125));
            dialogPLA.setPLADNF(canvasDNF);
            ubicateDialogPLA(dialogPLA, var);
            actualExpression = jTextFieldExpression.getText().trim();
            actualMinExpression = PreParser.getCleanExpression(jLabelSolution.getText());
            actualMaxExpression = "";
        } catch (Exception e) {
            invalidExpresion(e);
        }
    }

    private void drawKNF() {
        try {
            Expression<String> posForm = RuleSet.toCNF(ExprParser.parse(PreParser.parseToExpresion(jTextFieldExpression.getText())));
            String exp = PreParser.expresiontoString(posForm.toString().toUpperCase());
            Set<Character> vars = TruthTable.getVariables(exp);
            ExpressionDrawerParser.createArrayKNF(exp, vars);
            String[] var = TruthTable.getStringVar(vars);
            jLabelSolution.setText(exp);
            DialogLogicalCircuit dialogPLA = new DialogLogicalCircuit(exp, null);
            PLACanvasKNF canvasKNF = new PLACanvasKNF(new DrawPLA_KNF(var));
            canvasKNF.setPreferredSize(new Dimension((var.length * 50) + 112, (ExpressionDrawerParser.getAl().size() * 30) + 125));
            dialogPLA.setPLAKNF(canvasKNF);
            ubicateDialogPLA(dialogPLA, var);
            actualExpression = jTextFieldExpression.getText().trim();
            actualMaxExpression = PreParser.getCleanExpression(jLabelSolution.getText());
            actualMinExpression = "";
        } catch (Exception e) {
            invalidExpresion(e);
        }

    }

    private void drawKNF(String maxExpression) {
        Set<Character> vars = TruthTable.getVariables(maxExpression);
        ExpressionDrawerParser.createArrayKNF(maxExpression, vars);
        String[] var = TruthTable.getStringVar(vars);
        jLabelSolution.setText(maxExpression);
        DialogLogicalCircuit dialogPLA = new DialogLogicalCircuit(maxExpression, null);
        PLACanvasKNF canvasKNF = new PLACanvasKNF(new DrawPLA_KNF(var));
        canvasKNF.setPreferredSize(new Dimension((var.length * 50) + 112, (ExpressionDrawerParser.getAl().size() * 30) + 125));
        dialogPLA.setPLAKNF(canvasKNF);
        ubicateDialogPLA(dialogPLA, var);
        actualExpression = jTextFieldExpression.getText().trim();
        actualMaxExpression = PreParser.getCleanExpression(jLabelSolution.getText());
        actualMinExpression = "";
    }
    
    public void locateKarnaughMap(JDialogKarnaughMap dialog){
        int with = dialog.getjTable1().getColumnModel().getColumn(1).getWidth() * dialog.getjTable1().getColumnCount();
                Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                if (with + 55 >= screen.getWidth()) {
                    if (dialog.getjTable1().getHeight() + 100 >= screen.getHeight()) {
                        dialog.setSize((int) screen.getWidth() - 2, (int) screen.getHeight() - 40);
                    } else {
                        dialog.setSize((int) screen.getWidth() - 2, dialog.getjTable1().getHeight() + 120);
                    }
                    dialog.setVisible(true);
                } else {
                    dialog.setSize(new Dimension(with + 55, dialog.getjTable1().getHeight() + 100));
                    showDialog(dialog);
                }
    }

    public static void setActualExpression(String actualExpression) {
        JPanelReduction.actualExpression = actualExpression;
    }

    public static void setActualMaxExpression(String actualMaxExpression) {
        JPanelReduction.actualMaxExpression = actualMaxExpression;
    }

    public static void setActualMinExpression(String actualMinExpression) {
        JPanelReduction.actualMinExpression = actualMinExpression;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDNF;
    private javax.swing.JButton jButtonKNF;
    private javax.swing.JButton jButtonKarnaugh;
    private javax.swing.JButton jButtonMax;
    private javax.swing.JButton jButtonMin;
    private javax.swing.JButton jButtonTruthTable;
    private javax.swing.JEditorPane jLabelSolution;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldExpression;
    // End of variables declaration//GEN-END:variables
}
