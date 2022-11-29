package hu.akosbalogh;

import hu.akosbalogh.game.GameService;
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

        GameService gameService = applicationContext.getBean(GameService.class);
        gameService.start();
    }
}