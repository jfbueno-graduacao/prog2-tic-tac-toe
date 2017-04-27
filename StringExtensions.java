import java.util.Arrays;

public class StringExtensions {
    public static String padRight(String str, int padSize){
        return padRight(str, padSize, ' ');
    }

    public static String padRight(String str, int padSize, char padChar){
        if(str.length() >= padSize)
            return str;

        int size = padSize - str.length();
        char[] chars = new char[size];
        Arrays.fill(chars, padChar);
        String res = new String(chars);

        return str + res;
    }
}