package GUI;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow {

    private final BorderPane root;
    private final Stage stage;

    private ReductionPane reductionPane;
    private TruthTablePane truthTablePane;
    private KarnaughMapPane karnaughMapPane;
    private AboutPane aboutPane;

    public MainWindow(Stage stage) {
        this.stage = stage;
        this.root = new BorderPane();

        createPanes();

        VBox topBox = new VBox();
        topBox.getChildren().addAll(createMenuBar(), createToolBar());
        root.setTop(topBox);

        showReduction();
    }

    private void createPanes() {
        reductionPane = new ReductionPane(stage);
        truthTablePane = new TruthTablePane();
        karnaughMapPane = new KarnaughMapPane();
        aboutPane = new AboutPane();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // File menu
        Menu menuFile = new Menu("Archivo");
        MenuItem menuItemExit = new MenuItem("Salir");
        menuItemExit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        menuItemExit.setOnAction(e -> System.exit(0));
        menuFile.getItems().add(menuItemExit);

        // Tools menu
        Menu menuTools = new Menu("Herramientas");
        MenuItem menuItemReduction = new MenuItem("Reducción");
        menuItemReduction.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        menuItemReduction.setOnAction(e -> showReduction());

        MenuItem menuItemTruthTable = new MenuItem("Tabla de Activación");
        menuItemTruthTable.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN));
        menuItemTruthTable.setOnAction(e -> showPanelTable());

        MenuItem menuItemKarnaugh = new MenuItem("Mapa de Karnaugh");
        menuItemKarnaugh.setAccelerator(new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN));
        menuItemKarnaugh.setOnAction(e -> showKarnaughMap());

        menuTools.getItems().addAll(menuItemReduction, menuItemTruthTable, menuItemKarnaugh);

        // Help menu
        Menu menuHelp = new Menu("Ayuda");
        MenuItem menuItemAbout = new MenuItem("Acerca de");
        menuItemAbout.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        menuItemAbout.setOnAction(e -> showAbout());
        menuHelp.getItems().add(menuItemAbout);

        menuBar.getMenus().addAll(menuFile, menuTools, menuHelp);
        return menuBar;
    }

    private ToolBar createToolBar() {
        ToolBar toolBar = new ToolBar();

        Button btnReduction = createToolbarButton("/img/ReduccionToolbar.png",
                "Reduce, crea tabla de verdad, Mapa de Karnaugh y Diseño lógico");
        btnReduction.setOnAction(e -> showReduction());

        Button btnTruthTable = createToolbarButton("/img/TablaToolbar.png",
                "Obtiene una expresión a partir de una tabla de verdad");
        btnTruthTable.setOnAction(e -> showPanelTable());

        Button btnKarnaugh = createToolbarButton("/img/KMapToolbar.png",
                "Obtiene una expresión a partir de un Mapa de Karnaugh");
        btnKarnaugh.setOnAction(e -> showKarnaughMap());

        Button btnAbout = createToolbarButton("/img/aboutToolbar.png",
                "Información del programa");
        btnAbout.setOnAction(e -> showAbout());

        toolBar.getItems().addAll(
                btnReduction,
                new Separator(),
                btnTruthTable,
                new Separator(),
                btnKarnaugh,
                new Separator(),
                btnAbout
        );

        return toolBar;
    }

    private Button createToolbarButton(String iconPath, String tooltip) {
        Button btn = new Button();
        try {
            Image img = new Image(getClass().getResourceAsStream(iconPath));
            ImageView iv = new ImageView(img);
            iv.setFitWidth(24);
            iv.setFitHeight(24);
            iv.setPreserveRatio(true);
            btn.setGraphic(iv);
        } catch (Exception e) {
            // fallback: no icon
        }
        btn.setTooltip(new Tooltip(tooltip));
        btn.getStyleClass().add("toolbar-button");
        return btn;
    }

    public void showReduction() {
        root.setCenter(reductionPane.getRoot());
    }

    public void showPanelTable() {
        root.setCenter(truthTablePane.getRoot());
    }

    public void showKarnaughMap() {
        root.setCenter(karnaughMapPane.getRoot());
    }

    public void showAbout() {
        root.setCenter(aboutPane.getRoot());
    }

    public BorderPane getRoot() {
        return root;
    }
}
