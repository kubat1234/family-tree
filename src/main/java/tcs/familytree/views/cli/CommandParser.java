package tcs.familytree.views.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

    static Scanner scanner = new Scanner(System.in);

    static List<String> parseStdin() {
        return parse(scanner.nextLine());
    }

    static List<String> parse(String line) {
        final String alNum = "\\w+";
        final String whitespace = "\\s+";
        final String bounded = "(\\\\\"|[^\"])*";
        final List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(alNum);
        Matcher mat = pattern.matcher(line);
        Pattern boundedPattern = Pattern.compile(bounded);
        Matcher mat2 = boundedPattern.matcher(line);
        int last = 0;
        while(last < line.length()) {
            if(String.valueOf(line.charAt(last)).matches(alNum)) {
                mat.find(last);
                result.add(mat.group());
                last = mat.end();
                continue;
            }
            if(String.valueOf(line.charAt(last)).matches(whitespace)) {
                while (String.valueOf(line.charAt(last)).matches(whitespace)) {
                    ++last;
                    if(last == line.length()) {
                        break;
                    }
                }
                continue;
            }
            if(line.charAt(last) == '\"') {
                ++last;
                mat2.find(last);
                if(mat2.end() == line.length()) {
                    throw new IllegalArgumentException("Reached unclosed \"...\"");
                }
                last = mat2.end()+1;
                result.add(mat2.group().replaceAll("\\\\\"", "\""));
                continue;
            }
            throw new IllegalArgumentException("Unexpected character: " + line.charAt(last));
        }

        return result;
    }
}
