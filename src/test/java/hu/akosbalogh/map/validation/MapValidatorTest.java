package hu.akosbalogh.map.validation;

import hu.akosbalogh.map.model.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MapValidatorTest {

    private final MapValidator mapValidator = new MapValidator();

    @Mock
    private Map map;

    private final char[][] mapFoxColumn = new char[][]{
            {'X','O','X','O'},
            {'H','X','O','X'},
            {'X','H','X','F'},
            {'O','X','O','X'}
    };

    private final char[][] mapHoundWinner = new char[][]{
            {'X','O','X','O'},
            {'H','X','O','X'},
            {'X','H','X','O'},
            {'F','X','O','X'}
    };
    private final char[][] mapNotHoundWinner = new char[][]{
            {'X','O','X','O'},
            {'H','X','H','X'},
            {'X','O','X','O'},
            {'F','X','O','X'}
    };

    private final char[][] mapFoxWinner = new char[][]{
            {'X','O','X','F'},
            {'H','X','O','X'},
            {'X','H','X','O'},
            {'O','X','O','X'}
    };
    private final char[][] mapNotFoxWinner = new char[][]{
            {'X','O','X','O'},
            {'H','X','H','X'},
            {'X','O','X','F'},
            {'O','X','O','X'}
    };

    @Test
    public void searchFoxColumnIndexShouldReturnCorrectColumnIndex() throws Exception {
        given(map.getMapAsChars()).willReturn(mapFoxColumn);
        given(map.getNumberOfColumns()).willReturn(4);
        given(map.getNumberOfRows()).willReturn(4);

        int result = mapValidator.searchFoxColumnIndex(map);

        assertEquals(result, 3);
    }

    @Test
    public void isHoundWinnerShouldReturnFalseWhenCorrect() throws Exception {
        given(map.getMapAsChars()).willReturn(mapNotHoundWinner);
        given(map.getNumberOfColumns()).willReturn(4);
        given(map.getNumberOfRows()).willReturn(4);

        assertFalse(mapValidator.isHoundWinner(map));
    }

    @Test
    public void isHoundWinnerShouldReturnTrueWhenCorrect() throws Exception {
        given(map.getMapAsChars()).willReturn(mapHoundWinner);
        given(map.getNumberOfColumns()).willReturn(4);
        given(map.getNumberOfRows()).willReturn(4);

        assertTrue(mapValidator.isHoundWinner(map));
    }

    @Test
    public void isFoxWinnerShouldReturnTrueWhenCorrect() throws Exception {
        given(map.getMapAsChars()).willReturn(mapFoxWinner);
        given(map.getNumberOfColumns()).willReturn(4);

        assertTrue(mapValidator.isFoxWinner(map));
    }

    @Test
    public void isFoxWinnerShouldReturnFalseWhenCorrect() throws Exception {
        given(map.getMapAsChars()).willReturn(mapNotFoxWinner);
        given(map.getNumberOfColumns()).willReturn(4);

        assertFalse(mapValidator.isFoxWinner(map));
    }

    @Test
    public void searchingForFoxWithoutValidMapShouldResultInException() {
        given(map.getNumberOfRows()).willReturn(0);

        assertThrows(Exception.class, () -> {
           mapValidator.searchFoxRowIndex(map);
        });
    }
}
