public class Task1 {
    public static void main(String[] args) throws TooNegativeException {
        for(int i = 1; i <= 100; i++) {
            String isDivisible = isDivisible(i);

            if(isDivisible != null) {
                System.out.println(isDivisible);
            }
        }
    }

    public static String isDivisible(int input) throws TooNegativeException {
            // BONUS EXPRESSIONS
            // (input % 3 == 0 || input.indexOf('3') > -1)
            // (input % 5 == 0 || input.indexOf('5') > -1)
        
            if (input <= 0) {
                throw new TooNegativeException("Oops, number can't be negative or 0");
            } else if (input % 3 == 0 && input % 5 == 0) {
                return "HiOf";
            } else if (input % 3 == 0) {
                return "Hi";
            } else if (input % 5 == 0) {
                return "Of";
            } else {
                return null;
            }
    }
}
