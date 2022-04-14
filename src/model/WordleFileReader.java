import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordleFileReader {

    BufferedReader reader;
    List<String> list;

    public WordleFileReader() {
        FileReader file = null;
        try {
            file = new FileReader("src/answers.txt");
            reader = new BufferedReader(file);
            list = getWordsAsList();
        } catch (IOException e) {
            System.out.println("File not readable");
            System.exit(1);
        }
    }

    private List<String> getWordsAsList() throws IOException {
        List<String> list = new ArrayList<>();
        while (reader.ready()) {
            list.add(reader.readLine());
        }
        return list;
    }

    public int length() {
        return list.size();
    }

    public List<String> getList() {
        return list;
    }
}
