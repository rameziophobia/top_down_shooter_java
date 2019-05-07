package view.menu.mainmenu;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import model.ui.menu.Menu;
import model.ui.menu.MenuButton;
import view.Main;
import view.menu.mainmenu.menus.*;

import java.util.HashMap;

public class MenuScene extends Scene {

    public static final String PATH_RESOURCES_SPRITES_UI = Main.PATH_RESOURCES_SPRITES + "ui/";
    private static final double BUTTON_SCALE = 0.8;

    private static final Font TITLE_FONT_SIZE = new Font(40);

    private HashMap<Menus, Menu> menuHashMap = new HashMap<>();
    private StackPane stackPane;

    private StackPane stp_menus;

    public MenuScene(double width, double height) {
        super(new StackPane(), width, height);//todo width and height
        stackPane = (StackPane) getRoot();

        createScene();
    }

    private void createScene() {

        Rectangle blackScreen = new Rectangle(1280, 720, Color.BLACK);//todo width and height
        blackScreen.setOpacity(0);
        blackScreen.setMouseTransparent(true);

        FadeTransition fadeToBlack = new FadeTransition();
        fadeToBlack.setDuration(Duration.seconds(0.75));
        fadeToBlack.setToValue(1);
        fadeToBlack.setDelay(Duration.seconds(0.2));
        fadeToBlack.setNode(blackScreen);

        ImageView imgV_logo = new ImageView(PATH_RESOURCES_SPRITES_UI + "placeholder-logo-2.png");
        HBox hbx_logo = new HBox(imgV_logo);
        hbx_logo.setAlignment(Pos.CENTER);

        MenuButton.setButtonScale(BUTTON_SCALE);

        Menu mainMenu = new MainMenu(this);
        menuHashMap.put(Menus.Main, mainMenu);

        Menu newGameMenu = new NewGameMenu(this);
        menuHashMap.put(Menus.NewGame, newGameMenu);

        Menu loadGameMenu = new LoadGameMenu(this);
        menuHashMap.put(Menus.LoadGame, loadGameMenu);

        Menu hallOfFameMenu = new HallOfFameMenu(this);
        menuHashMap.put(Menus.HallOfFame, hallOfFameMenu);

        Menu settingsMenu = new SettingsMenu(this);
        menuHashMap.put(Menus.Settings, settingsMenu);

        stp_menus = new StackPane(mainMenu, newGameMenu, loadGameMenu, hallOfFameMenu, settingsMenu);

        VBox vbx_main = new VBox(hbx_logo, stp_menus);
        vbx_main.setPadding(new Insets(10, 0, 10, 0));

        stackPane.getChildren().addAll(
                vbx_main,
                blackScreen);

        mainMenu.fadeIn();
    }

    public static Label createMenuTitle(String text) {
        Label lbl_title = new Label(text);
        lbl_title.setFont(TITLE_FONT_SIZE);
        VBox.setMargin(lbl_title, new Insets(10, 10, 10, 10));
        return lbl_title;
    }

    public void menuTransition(Menus key) {
        if (menuHashMap.containsKey(key)) {
            menuHashMap.get(key).fadeIn();
        } else {
            throw new IllegalArgumentException("Invalid Menu key");
        }
    }

    public void update() {
        stp_menus.getChildren().forEach(node -> ((Menu) node).getChildren().forEach(node1 -> {
            if (node1 instanceof MenuButton) {
                ((MenuButton) node1).update();
            }
        }));
    }
}
