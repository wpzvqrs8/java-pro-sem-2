package roughwork;

public class emojis_exttraction {
    static void main() {
        System.out.println();
        for (int i = 1; i < 63255; i++) {
            System.out.print((char)i);
            if(i%100==0) System.out.println();
        }
    }
}
