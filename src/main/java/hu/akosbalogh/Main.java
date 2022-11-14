package hu.akosbalogh;

import hu.akosbalogh.data.ScoreRepository;
import hu.akosbalogh.game.GameController;
import hu.akosbalogh.game.RandomController;
import hu.akosbalogh.input.InputController;
import hu.akosbalogh.map.MapController;
import hu.akosbalogh.map.MapPrinter;
import hu.akosbalogh.map.validation.MapValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * The Main class.
 */
public class Main {
    /**
     * The Main method.
     *
     * @param args Input arguments.
     * @throws Exception Exception.
     */
    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("hu.akosbalogh");

        GameController gameController = applicationContext.getBean(GameController.class);
        gameController.start();
    }
}