package test;

public class breakBlockTest {
    public static void main(String args[]) {

        block: {
            System.out.print("1");
            if (1 == 1) {
                break block;
            }
            System.out.print("2");
        }

        System.out.print("3");
    }
}
