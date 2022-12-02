package hu.akosbalogh.map;

import hu.akosbalogh.map.model.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MapPrinterTest {
    @Mock
    private Map map;

    private final MapPrinter mapPrinter = new MapPrinter();

    private final char[][] defaultCharMap4x4 = {
            {'X','H','X','H'},
            {'O','X','O','X'},
            {'X','O','X','O'},
            {'F','X','O','X'}
    };

    private final String ansiReset = "\u001B[0m";
    private final String ansiYellow = "\u001B[33m";
    private final String ansiBlack = "\u001B[30m";

    private final String formattedMap =
            ansiBlack + 'X' + ansiReset + 'H' + ansiBlack + 'X' + ansiReset + "H\n" +
            ansiYellow + "O" + ansiReset + ansiBlack + 'X' + ansiReset + ansiYellow + "O" + ansiReset + ansiBlack + 'X' + ansiReset + "\n" +
            ansiBlack + 'X' + ansiReset + ansiYellow + "O" + ansiReset + ansiBlack + 'X' + ansiReset + ansiYellow + "O" + ansiReset + "\n" +
                    'F' + ansiBlack + 'X' + ansiReset + ansiYellow + "O" + ansiReset + ansiBlack + 'X' + ansiReset + "\n";


    @Test
    public void printMapShouldPrintWithoutException() {
        given(map.getMapAsChars()).willReturn(defaultCharMap4x4);
        given(map.getNumberOfColumns()).willReturn(4);
        given(map.getNumberOfRows()).willReturn(4);

        assertDoesNotThrow(() ->{
            mapPrinter.printMap(map);
        });
    }


}
