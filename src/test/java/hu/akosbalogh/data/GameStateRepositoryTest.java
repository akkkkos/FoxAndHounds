package hu.akosbalogh.data;

import hu.akosbalogh.game.GameStateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameStateRepositoryTest {

    @Mock
    public GameStateService gameStateService;

    public GameStateRepository gameStateRepository = new GameStateRepository();

    public GameStateRepositoryTest() throws Exception {
    }

    @AfterEach
    public void cleanUp(){
        gameStateRepository.deleteGameStateIfExists("randomUsername");
    }

    @Test
    public void getGameStateShouldReturnWithGameStateServiceIfFileFound() {
        gameStateRepository.saveGameState("randomUsername", gameStateService);
        GameStateService result = gameStateRepository.getGameState("randomUsername");
        assertNotNull(result);
    }

    @Test
    public void badGameStateShouldThrowException() throws Exception {
        when(gameStateService.getMap()).thenThrow(Exception.class);

        FileOutputStream fileOutputStream = new FileOutputStream("state_randomUsername.xml", true);
        fileOutputStream.write("bad,data".getBytes());
        fileOutputStream.close();

        assertDoesNotThrow(() -> gameStateRepository.saveGameState("randomUsername", gameStateService));
        assertThrows(Exception.class, () -> gameStateRepository.getGameState("randomUsername"));
    }

    @Test
    public void dontDeleteFileIfItDoesNotExist() {
        File file = new File("state_randomUsername.xml");

        assertFalse(file.exists());
        gameStateRepository.deleteGameStateIfExists("randomUsername");
        assertFalse(file.exists());
    }

    @Test
    public void getGameStateShouldReturnNullIfFileNotFound() {
        File file = new File("state_randomUsername.xml");

        assertFalse(file.exists());

        GameStateService result = gameStateRepository.getGameState("randomUsername");

        assertNull(result);
    }
}
