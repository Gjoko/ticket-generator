import java.util.Random;
import java.util.stream.IntStream;

public class SlipHelper {

    // ticket can be 0 to 5 (for the total of 6 tickets)
    public static int getNumberCountForTicket(int[][] sg, int ticketId) throws Exception {
        if (ticketId < 0 || ticketId > 5) {
            throw new Exception("Invalid ticketId");
        }
        return IntStream.of(sg[ticketId * 3]).sum() +
                IntStream.of(sg[ticketId * 3 + 1]).sum() +
                IntStream.of(sg[ticketId * 3 + 2]).sum();
    }

    public static void printStatistics(int [][] slip) throws Exception {
        System.out.println("Count of numbers per ticket:");
        for (int i = 0; i < 6; i++) {
            System.out.print(SlipHelper.getNumberCountForTicket(slip, i) + " ");
        }
        System.out.println("\nCount of numbers per column:");
        for (int i = 0; i < 9; i++) {
            System.out.print(getNumberCountForColumn(slip, i) + " ");
        }
    }

    public static int getNumberCountForColumn(int[][] slip, int columnId) throws Exception {
        if (columnId < 0 || columnId > 8) {
            throw new Exception("InvalidColumnId");
        }
        int sum = 0;
        for (int i = 0; i < slip.length; i++) {
            sum += slip[i][columnId];
        }
        return sum;
    }

    public static boolean columnIsFilled(int[][] slip, int columnIndex) throws Exception {
        if (columnIndex < 0 || columnIndex > 8) {
            throw new Exception("Invalid column index");
        }
        boolean columnIsFilled = switch (columnIndex) {
            case 0 -> SlipHelper.getNumberCountForColumn(slip, columnIndex) == 9;
            case 8 -> SlipHelper.getNumberCountForColumn(slip, columnIndex) == 11;
            default -> SlipHelper.getNumberCountForColumn(slip, columnIndex) == 10;
        };
        return columnIsFilled;
    }

    public static int getColumnCountForTicket(int[][] slip, int ticketIndex, int columnIndex) {
        int minRow = ticketIndex * 3;
        return slip[minRow][columnIndex] + slip[minRow + 1][columnIndex] +
                slip[minRow + 2][columnIndex];
    }

    public static int getNumCountForRow(int[][] slip, int rowId) throws Exception {
        if (rowId < 0 || rowId > 18) {
            throw new Exception("Invalid rowId");
        }
        return IntStream.of(slip[rowId]).sum();
    }

    public static int generateRandomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min) + min;
    }
}
