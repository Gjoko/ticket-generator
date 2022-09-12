import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SlipGenerator {


    private final int[][] slip;
    private final int[][] numberPopulatedSlip;

    public SlipGenerator() {
        this.slip = new int[18][9];
        for (int i = 0; i < slip[0].length; i++) {
            for (int j = 0; j < slip[0].length; j++) {
                this.slip[i][j] = 0;
            }
        }
        this.numberPopulatedSlip = new int[18][9];
    }

    public void generateSlip() throws Exception {

        addFirstNumbersForFirstColumn();

        boolean numberNotInserted = true;
        while (numberNotInserted) {

            int rowIndex = SlipHelper.generateRandomNumber(0, 18);
            if (slip[rowIndex][8] == 1 || SlipHelper.getNumCountForRow(slip, rowIndex) == 5 ||
                    SlipHelper.getNumberCountForTicket(slip, rowIndex / 3) == 15) {
                continue;
            }
            numberNotInserted = false;
            slip[rowIndex][8] = 1;
        }

        fillInRemainingColumnsPerTicket(2);
        fillInRemainingColumnsPerTicket(2);
        fillInRemainingColumnsPerTicket(2);
        fillInRemainingColumnsPerTicket(3);

        checkAndApplyAdjustments();
        checkAndApplyAdjustments();

        populateSlipWithNumbers();
    }

    public void populateSlipWithNumbers() {
        int counter = 1;
        for (int j = 0; j < 9; j++) {
            for (int row = 0; row < 18; row++) {
                if (this.slip[row][j] == 1) {
                    this.numberPopulatedSlip[row][j] = counter;
                    counter++;
                }
            }
        }
    }

    public void checkAndApplyAdjustments() throws Exception {

        for (int rowIndex = 0; rowIndex < 18; rowIndex++) {
            if (SlipHelper.getNumCountForRow(slip, rowIndex) != 5) {
                int faultyColumn = findFaultyColumn();
                int ticketRow1 = 0;
                int ticketRow2 = 0;
                switch (rowIndex % 3) {
                    case 0:
                        ticketRow1 = rowIndex + 1;
                        ticketRow2 = rowIndex + 2;
                        break;
                    case 1:
                        ticketRow1 = rowIndex - 1;
                        ticketRow2 = rowIndex + 1;
                        break;
                    case 2:
                        ticketRow1 = rowIndex - 2;
                        ticketRow2 = rowIndex - 1;
                        break;
                    default:
                        break;
                }
                if (faultyColumn != -1) {
                    performValueSwap(faultyColumn, rowIndex, ticketRow1, ticketRow2);
                }
            }
        }
    }

    public void performValueSwap(int columnIndex, int faultyRow, int ticketRow1, int ticketRow2) {
        if (this.slip[ticketRow1][columnIndex] == 0) {
            for (int i = 0; i < 9; i++) {
                if (this.slip[ticketRow1][i] == 1 && this.slip[faultyRow][i] == 0) {
                    this.slip[ticketRow1][i] = 0;
                    this.slip[faultyRow][i] = 1;
                    this.slip[ticketRow1][columnIndex] = 1;
                    break;
                }
            }
        } else if (this.slip[ticketRow2][columnIndex] == 0) {
            for (int i = 0; i < 9; i++) {
                if (this.slip[ticketRow2][i] == 1 && this.slip[faultyRow][i] == 0) {
                    this.slip[ticketRow2][i] = 0;
                    this.slip[faultyRow][i] = 1;
                    this.slip[ticketRow2][columnIndex] = 1;
                    break;
                }
            }
        }
    }

    public int findFaultyColumn() throws Exception {
        for (int j = 0; j < 9; j++) {
            switch (j) {
                case 0:
                    if (SlipHelper.getNumberCountForColumn(slip, 0) != 9) {
                        return 0;
                    }
                    break;
                case 8:
                    if (SlipHelper.getNumberCountForColumn(slip, 8) != 11) {
                        return 8;
                    }
                    break;
                default:
                    if (SlipHelper.getNumberCountForColumn(slip, j) != 10) {
                        return j;
                    }
                    break;
            }
        }
        return -1;
    }


    public void addFirstNumbersForFirstColumn() throws Exception {
        for (int ticketIndex = 0; ticketIndex < 6; ticketIndex++) {
            for (int columnIndex = 0; columnIndex < 9; columnIndex++) {
                boolean numberNotInserted = true;
                while (numberNotInserted) {
                    int rowIndex = SlipHelper.generateRandomNumber(ticketIndex * 3, ticketIndex * 3 + 3);
                    if (SlipHelper.getNumCountForRow(slip, rowIndex) == 5) {
                        continue;
                    }
                    this.slip[rowIndex][columnIndex] = 1;
                    numberNotInserted = false;
                }
            }
        }
    }

    public void fillInRemainingColumnsPerTicket(int maxDepth) throws Exception {
        for (int columnIndex = 0; columnIndex < 9; columnIndex++) {
            List<Integer> passedRows = IntStream.range(0, 18).boxed().collect(Collectors.toList());

            if (SlipHelper.columnIsFilled(slip, columnIndex)) {
                continue;
            }
            boolean numberInserted = false;
            while (!numberInserted) {
                if (passedRows.isEmpty()) {
                    break;
                }
                int rowIndex = SlipHelper.generateRandomNumber(0, 18);
                if (SlipHelper.getNumberCountForTicket(slip, rowIndex / 3) == 15 || SlipHelper.getNumCountForRow(slip, rowIndex) == 5
                        || SlipHelper.getColumnCountForTicket(slip, rowIndex / 3, columnIndex) >= maxDepth || this.slip[rowIndex][columnIndex] == 1) {
                    passedRows.remove(Integer.valueOf(rowIndex));
                    continue;
                }
                numberInserted = true;
                slip[rowIndex][columnIndex] = 1;
            }
        }
    }

    public int[][] getSlip() {
        return slip;
    }

    public int[][] getNumberPopulatedSlip() {
        return numberPopulatedSlip;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberPopulatedSlip.length; i++) {
            sb.append(Arrays.toString(numberPopulatedSlip[i]));
            sb.append("\n");
            if (i != 0 && (i + 1) % 3 == 0) {
                sb.append("================================\n");
            }
        }

        return sb.toString();
    }
}
