module com.sk.dungeonboardgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sk.dungeonboardgame to javafx.fxml;
    exports com.sk.dungeonboardgame;
}
