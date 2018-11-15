import java.util.HashMap;
import java.util.Map;

public class HexMap {

    public static Map<Character, boolean[]> hexToBinary;
    private static final boolean t = true;
    private static final boolean f = false;

    static {
        hexToBinary = new HashMap<>();

        hexToBinary.put('0', new boolean[]{f, f, f, f});
        hexToBinary.put('1', new boolean[]{f, f, f, t});
        hexToBinary.put('2', new boolean[]{f, f, t, f});
        hexToBinary.put('3', new boolean[]{f, f, t, t});
        hexToBinary.put('4', new boolean[]{f, t, f, f});
        hexToBinary.put('5', new boolean[]{f, t, f, t});
        hexToBinary.put('6', new boolean[]{f, t, t, f});
        hexToBinary.put('7', new boolean[]{f, t, t, t});
        hexToBinary.put('8', new boolean[]{t, f, f, f});
        hexToBinary.put('9', new boolean[]{t, f, f, t});
        hexToBinary.put('A', new boolean[]{t, f, t, f});
        hexToBinary.put('B', new boolean[]{t, f, t, t});
        hexToBinary.put('C', new boolean[]{t, t, f, f});
        hexToBinary.put('D', new boolean[]{t, t, f, t});
        hexToBinary.put('E', new boolean[]{t, t, t, f});
        hexToBinary.put('F', new boolean[]{t, t, t, t});

    }
}
