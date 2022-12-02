package hu.akosbalogh.game;

import hu.akosbalogh.data.GameStateRepository;
import hu.akosbalogh.data.ScoreRepository;
import hu.akosbalogh.input.InputService;
import hu.akosbalogh.map.MapPrinter;
import hu.akosbalogh.map.model.Map;
import hu.akosbalogh.map.validation.MapValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private MapValidator mapValidator;
    @Mock
    private MapPrinter mapPrinter;
    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private GameStateRepository gameStateRepository;
    @Mock
    private GameStateService gameStateService;
    @Mock
    private InputService inputService;
    @Mock
    private Map map;

    private GameService gameService;

    private final String[][] highScores = {
            {"Ákos","2"},
            {"Balázs","1"}
    };

    @BeforeEach
    public void setUp() {
        gameService = new GameService(
                gameStateService,
                inputService,
                mapValidator,
                mapPrinter,
                scoreRepository,
                gameStateRepository
        );
    }


    @Test
    public void startingNormalGameShouldNotResultInException() throws Exception {
        given(inputService.getUserNameFromUser()).willReturn("Ákos");
        when(inputService.getUserInput()).thenReturn("start").thenReturn("exit");
        given(inputService.getMapSizeFromUser()).willReturn(4);
        given(gameStateService.getMap()).willReturn(map);

        gameService.start();


        verify(scoreRepository).loginPlayer("Ákos");
        verify(gameStateService).buildNewMap(4);
        verify(mapPrinter).printMap(map);
        verify(scoreRepository).closeConnection();
    }

    @Test
    public void foxWinningShouldBeAddedToDatabase() throws Exception {
        given(inputService.getUserNameFromUser()).willReturn("Ákos");
        when(inputService.getUserInput()).thenReturn(
                "Ákos",
                "start",
                "move ur",
                "exit"
        );
        given(inputService.getMapSizeFromUser()).willReturn(4);
        given(inputService.isUserMoveCorrectFormat("move ur")).willReturn(true);
        given(mapValidator.isFoxWinner(map)).willReturn(true);
        given(gameStateService.getMap()).willReturn(map);
        given(mapValidator.isSpecifiedSpaceAvailable(map,1,2, "ur")).willReturn(true);
        given(inputService.isUserMoveCorrectFormat("move ur")).willReturn(true);
        given(gameStateService.getFoxPosition()).willReturn(new int[]{1, 2});

        gameService.start();

        verify(scoreRepository).loginPlayer("Ákos");
        verify(scoreRepository).increaseScore("Ákos");
        verify(gameStateRepository).deleteGameStateIfExists("Ákos");
        verify(scoreRepository).closeConnection();
    }

    @Test
    public void houndWinningShouldResetGameState() throws Exception {
        given(inputService.getUserNameFromUser()).willReturn("Ákos");
        when(inputService.getUserInput()).thenReturn(
                "Ákos",
                "start",
                "move dl",
                "exit"
        );
        given(inputService.getMapSizeFromUser()).willReturn(4);
        given(inputService.isUserMoveCorrectFormat("move dl")).willReturn(true);
        given(mapValidator.isFoxWinner(map)).willReturn(false);
        given(mapValidator.isHoundWinner(map)).willReturn(true);
        given(gameStateService.getMap()).willReturn(map);
        given(mapValidator.isSpecifiedSpaceAvailable(map,2,1, "dl")).willReturn(true);
        given(inputService.isUserMoveCorrectFormat("move dl")).willReturn(true);
        given(gameStateService.getFoxPosition()).willReturn(new int[]{2, 1});

        gameService.start();

        verify(scoreRepository).loginPlayer("Ákos");
        verify(gameStateRepository).deleteGameStateIfExists("Ákos");
        verify(scoreRepository).closeConnection();
    }

    @Test
    public void allKnownCommandsShouldWork() throws Exception {
        given(inputService.getUserNameFromUser()).willReturn("Ákos");
        when(inputService.getUserInput()).thenReturn(
                "Ákos",
                "move",
                "commands",
                "test",
                "start",
                "move dl",
                "move ur",
                "scores",
                "load",
                "saveandexit"
        );
        given(inputService.getMapSizeFromUser()).willReturn(4);
        given(gameStateService.getMap()).willReturn(map);
        given(scoreRepository.getTopFiveHighScores()).willReturn(highScores);
        given(inputService.isUserMoveCorrectFormat("move ur")).willReturn(true);
        given(inputService.isUserMoveCorrectFormat("move dl")).willReturn(true);
        given(gameStateService.getFoxPosition()).willReturn(new int[]{3, 0});
        given(mapValidator.isSpecifiedSpaceAvailable(map,3,0, "ur")).willReturn(true);
        given(mapValidator.isSpecifiedSpaceAvailable(map,3,0, "dl")).willReturn(true);
        given(gameStateRepository.getGameState("Ákos")).willReturn(gameStateService);

        gameService.start();


        verify(scoreRepository).loginPlayer("Ákos");
        verify(gameStateService).buildNewMap(4);
        verify(mapPrinter, atLeastOnce()).printMap(map);
        verify(gameStateRepository).saveGameState("Ákos",gameStateService);
        verify(scoreRepository).closeConnection();
    }
}
