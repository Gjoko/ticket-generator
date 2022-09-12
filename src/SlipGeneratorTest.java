import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SlipGeneratorTest {

    private final SlipGenerator sg = new SlipGenerator();

    @BeforeEach
    void beforeEach() throws Exception {
        sg.generateSlip();
    }

    @Test
    void testEachTicketContains15Numbers() throws Exception {
        for (int i = 0; i < 6; i++) {
            assertEquals(15, SlipHelper.getNumberCountForTicket(sg.getSlip(), i));
        }
    }

    @Test
    void testEachRowContains5Numbers() {
        for (int i = 0; i < 18; i++) {
            try {
                assertEquals(5, SlipHelper.getNumCountForRow(sg.getSlip(), i));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Test
    void testEachTicketDoesntContainColumnWithoutNumber() {
        for (int ticketIndex = 0; ticketIndex < 6; ticketIndex++) {
            for (int columnIndex = 0; columnIndex < 9; columnIndex++) {
                assertNotEquals(0,
                        SlipHelper.getColumnCountForTicket(this.sg.getSlip(), ticketIndex, columnIndex));
            }
        }
    }

    @Test
    void testNoDuplicateNumbersBetweenInAStrip() {
        Set<Integer> numberSet = new HashSet<>();
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 9; j++) {
                numberSet.add(sg.getNumberPopulatedSlip()[i][j]);
            }
        }
        // we expect 91 because 0 is here as a replacement of the empty space
        assertEquals(91, numberSet.size());
    }

    @Test
    void testOrderOfColumns() {
        for (int i = 0; i < 9; i++) {
            ArrayList<Integer> columnList = new ArrayList<>();
            for (int j = 0; j < 18; j++) {
                if (sg.getNumberPopulatedSlip()[j][i] != 0) {
                    columnList.add(sg.getNumberPopulatedSlip()[j][i]);
                }
            }
            ArrayList<Integer> secondList = (ArrayList<Integer>) columnList.clone();
            Collections.sort(secondList);
            assertArrayEquals(columnList.toArray(), secondList.toArray());
        }
    }

    @Test
    void sizeOfColumns() {
        for (int i = 0; i < 9; i++) {
            ArrayList<Integer> columnList = new ArrayList<>();
            for (int j = 0; j < 18; j++) {
                if (sg.getNumberPopulatedSlip()[j][i] != 0) {
                    columnList.add(sg.getNumberPopulatedSlip()[j][i]);
                }
            }
            if (i == 0) {
                assertEquals(columnList.size(), 9);
            } else if (i == 8) {
                assertEquals(columnList.size(), 11);
            } else {
                assertEquals(columnList.size(), 10);
            }

        }
    }

    @Test
    void testMinAndMaxOfFirstColumn() {
        ArrayList<Integer> columnList = new ArrayList<>();
        for (int j = 0; j < 18; j++) {
            if (sg.getNumberPopulatedSlip()[j][0] != 0) {
                columnList.add(sg.getNumberPopulatedSlip()[j][0]);
            }
        }
        Collections.sort(columnList);
        assertEquals(columnList.get(0), 1);
        assertEquals(columnList.get(columnList.size() - 1), 9);
    }

    @Test
    void testMinAndMaxOfLastColumn() {
        ArrayList<Integer> columnList = new ArrayList<>();
        for (int j = 0; j < 18; j++) {
            if (sg.getNumberPopulatedSlip()[j][8] != 0) {
                columnList.add(sg.getNumberPopulatedSlip()[j][8]);
            }
        }
        Collections.sort(columnList);
        assertEquals(columnList.get(0), 80);
        assertEquals(columnList.get(columnList.size() - 1), 90);
    }

    @Test
    void testMinAndMaxOfOtherColumns() {
        for (int i = 1; i < 8; i++) {
            ArrayList<Integer> columnList = new ArrayList<>();
            for (int j = 0; j < 18; j++) {
                if (sg.getNumberPopulatedSlip()[j][i] != 0) {
                    columnList.add(sg.getNumberPopulatedSlip()[j][i]);
                }
            }
            Collections.sort(columnList);
            assertEquals(columnList.get(0), i * 10);
            assertEquals(columnList.get(columnList.size() - 1), i * 10 + 9);
        }
    }
}