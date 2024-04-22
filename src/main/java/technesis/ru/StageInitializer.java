package technesis.ru;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import technesis.ru.controller.MainController;


@Component
public class StageInitializer implements ApplicationListener<JavaFxApplication.StageReadyEvent> {

    private final Scene scene;

    public StageInitializer(MainController mainController) {
        this.scene = new Scene(mainController);
    }


    @Override
    public void onApplicationEvent(JavaFxApplication.StageReadyEvent event) {
        Stage stage = event.getStage();
        stage.setScene(scene);
        stage.setTitle("Тестовое задание \"Заметки\"");
//        stage.getIcons().add(new Image("/images/.png"));
        stage.show();
    }
}
