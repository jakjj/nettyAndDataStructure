package netty.nio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BufferReadTest {

    public static void main(String[] args) {
        String moneyRegex = "([0-9]{3}-[0-9]{9})|[1-9][0-9]{10}";
        Pattern pattern = Pattern.compile(moneyRegex);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\file1.txt"));
            String line;
            line = bufferedReader.readLine();
            while(line != null){
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()){
                    System.out.println(line);
                }
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
