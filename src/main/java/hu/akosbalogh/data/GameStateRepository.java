package hu.akosbalogh.data;

import java.io.File;

import hu.akosbalogh.game.GameStateService;
import hu.akosbalogh.map.model.PersistableGameState;
import hu.akosbalogh.map.validation.GameStateValidator;
import hu.akosbalogh.map.validation.MapValidator;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * XML Based game saving repository.
 */
@Repository
public class GameStateRepository {
    //private final GameStateValidator gameStateValidator;
    //private final MapValidator mapValidator;
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    @Autowired
    public GameStateRepository() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(PersistableGameState.class);
        marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        unmarshaller = jaxbContext.createUnmarshaller();
    }

    /**
     * Deletes the XML save file based on given username.
     *
     * @param userName The user's name to look for.
     */
    public void deleteGameStateIfExists(String userName) {
        File file = new File("state_" + userName + ".xml");
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Saves the user's game state into XML file.
     *
     * @param userName The user's name for naming the file.
     * @param gameStateService The game's state.
     */
    public void saveGameState(String userName, GameStateService gameStateService) {
        try {
            PersistableGameState persistableGameState =
                    new PersistableGameState(
                            gameStateService.getMap(),
                            gameStateService.getHoundPositions(),
                            gameStateService.getFoxPosition());

            marshaller.marshal(persistableGameState, new File("state_" + userName + ".xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads in a given user's game state from their own XML file.
     *
     * @param userName The user's name to look for.
     * @return Returns the user's game state if found. Returns null if not found.
     */
    public GameStateService getGameState(String userName) {
        try {
            File file = new File("state_" + userName + ".xml");
            if (file.exists()) {
                PersistableGameState persistableGameState = (PersistableGameState) unmarshaller.unmarshal(file);

                return new GameStateService(
                        persistableGameState.getMap(),
                        persistableGameState.getHoundPositions(),
                        persistableGameState.getFoxPosition()
                );
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load XML");
        }
    }
}
