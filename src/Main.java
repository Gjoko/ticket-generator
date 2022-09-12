public class Main {
    public static void main(String[] args) throws Exception {
        SlipGenerator slipGenerator = new SlipGenerator();
        slipGenerator.generateSlip();
        System.out.println(slipGenerator);
        SlipHelper.printStatistics(slipGenerator.getSlip());
    }
}