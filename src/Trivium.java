public class Trivium {
    public static final String KEY = "80000000000000000000";
    public static final String IV = "00000000000000000000";
    public static final int KEY_STREAM_LENGTH = 512 * 8;

    public static void main(String[] args) {
        System.out.println(
                transformBinaryToHex(
                        generateKeyStream(
                                setup(
                                        transformHexToBinary(reverseHexString(KEY)),
                                        transformHexToBinary(reverseHexString(IV))
                                )
                        )
                )
        );

    }

    public static VectorContainer setup(boolean[] k, boolean[] iv) {
        boolean[] vector1 = new boolean[93];
        boolean[] vector2 = new boolean[84];
        boolean[] vector3 = new boolean[111];

        for (int i = 0; i < 93; i++)
            vector1[i] = i < k.length && k[i];

        for (int i = 0; i < 84; i++)
            vector2[i] = i < iv.length && iv[i];

        for (int i = 0; i < 111; i++)
            vector3[i] = i >= 108;

        for (int i = 0; i < 4 * 288; i++) {
            boolean t1 = (vector1[65] ^ (vector1[90] && vector1[91]) ^ vector1[92] ^ vector2[77]);
            boolean t2 = (vector2[68] ^ (vector2[81] && vector2[82]) ^ vector2[83] ^ vector3[86]);
            boolean t3 = (vector3[65] ^ (vector3[108] && vector3[109]) ^ vector3[110] ^ vector1[68]);

            vector1 = shiftVector(vector1, t3);

            vector2 = shiftVector(vector2, t1);

            vector3 = shiftVector(vector3, t2);
        }

        return new VectorContainer(vector1, vector2, vector3);
    }

    public static boolean[] generateKeyStream(VectorContainer vc) {
        boolean[] keyStream = new boolean[KEY_STREAM_LENGTH];
        for (int i = 0; i < KEY_STREAM_LENGTH; i++) {
            boolean t1 = (vc.vector1[65] ^ vc.vector1[92]);
            boolean t2 = (vc.vector2[68] ^ vc.vector2[83]);
            boolean t3 = (vc.vector3[65] ^ vc.vector3[110]);

            keyStream[i] = t1 ^ t2 ^ t3;

            t1 = (t1 ^ (vc.vector1[90] && vc.vector1[91]) ^ vc.vector2[77]);
            t2 = (t2 ^ (vc.vector2[81] && vc.vector2[82]) ^ vc.vector3[86]);
            t3 = (t3 ^ (vc.vector3[108] && vc.vector3[109]) ^ vc.vector1[68]);

            vc.vector1 = shiftVector(vc.vector1, t3);

            vc.vector2 = shiftVector(vc.vector2, t1);

            vc.vector3 = shiftVector(vc.vector3, t2);
        }

        return keyStream;
    }

    public static boolean[] transformHexToBinary(String sequence) {
        boolean[] binarySequence = new boolean[sequence.length() * 4];
        int index = 0;
        for (int i = 0; i < sequence.length(); i++) {
            boolean[] bits = HexMap.hexToBinary.get(sequence.charAt(i));
            for (int j = 0; j < bits.length; j++)
                binarySequence[index++] = bits[j];
        }

        return binarySequence;
    }

    public static String transformBinaryToHex(boolean[] sequence) {
        StringBuilder sb = new StringBuilder();
        sequence = reverseArray(sequence);
        boolean[] leftSubArray = new boolean[4];
        boolean[] rightSubArray = new boolean[4];
        for (int i = 0; i < sequence.length; i += 8) {
            if (i % 128 == 0)
                sb.insert(0, "\n");

            for (int j = 0; j < 4; j++) {
                leftSubArray[j] = sequence[i + j];
                rightSubArray[j] = sequence[i + 4 + j];
            }

            //sb.append(binaryToHex(rightSubArray));
            //sb.append(binaryToHex(leftSubArray));

            sb.insert(0, binaryToHex(rightSubArray));
            sb.insert(0, binaryToHex(leftSubArray));

        }
        return sb.toString();
        //return sb.reverse().toString();
    }

    public static String reverseHexString(String sequence) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequence.length(); i += 2) {
            sb.insert(0, sequence.substring(i, i + 2));
        }

        return sb.toString();
    }

    public static boolean[] shiftVector(boolean[] vector, boolean t) {
        boolean[] tmp = new boolean[vector.length];
        tmp[0] = t;
        for (int i = 1; i < tmp.length; i++) {
            tmp[i] = vector[i - 1];
        }

        return tmp;
    }

    public static String binaryToHex(boolean[] array) {
        int sum = 0;
        if (array[0])
            sum += 8;
        if (array[1])
            sum += 4;
        if (array[2])
            sum += 2;
        if (array[3])
            sum += 1;

        if (sum < 10)
            return Integer.toString(sum);
        else if (sum == 10)
            return "A";
        else if (sum == 11)
            return "B";
        else if (sum == 12)
            return "C";
        else if (sum == 13)
            return "D";
        else if (sum == 14)
            return "E";
        else
            return "F";
    }

    public static boolean[] reverseArray(boolean[] array) {
        boolean[] tmp = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            tmp[i] = array[array.length - 1 - i];
        }

        return tmp;
    }

}
