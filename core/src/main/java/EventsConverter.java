import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class EventsConverter {
    public static void main(String[] args) {
        //noinspection ConcatenationWithEmptyString
        String input = "" +
            "";

        List<String> result = new ArrayList<>();

        String lastVersion = "1.0.0";
        int lastRuleNumber = 0;
        String lastParameterName = null;
        List<String> parameterValues = new ArrayList<>();

        for (String line : input.split("\n")) {
            if (true) System.out.println("LINE=" + line);
            BlockType blockType = BlockType.of(line);
            line = line.trim();

            if (blockType != BlockType.PARAMETER_VALUE) {
                lastParameterName = closeParameters(result, parameterValues, lastParameterName);
            }

            if (blockType == BlockType.VERSION) {
                if (line.startsWith("if")) {
                    line = line.substring("if (this.data.hasVersion(".length(), line.length() - ")) {".length());
                    lastVersion = line.replace(", ", ".");
                } else {
                    lastVersion = "1.0.0";
                }
            } else if (blockType == BlockType.TRIGGER_NAME) {
                String triggerName = line.substring(line.indexOf("this.triggers.") + "this.triggers.".length());
                String comment = null;
                if (triggerName.contains("//")) {
                    comment = triggerName.substring(triggerName.indexOf("//") + "//".length()).trim();
                }
                triggerName = triggerName.substring(0, triggerName.indexOf(','));
                result.add("  " + (++lastRuleNumber) + ":");
                result.add("    trigger: \"" + triggerName + "\"" + (comment == null ? "" : " # " + comment));
                if (!lastVersion.equals("1.0.0")) {
                    if (lastVersion.endsWith(".0")) {
                        lastVersion = lastVersion.substring(0, lastVersion.length() - ".0".length());
                    }
                    result.add("    min-version: \"" + lastVersion + "\"");
                }
            } else if (blockType == BlockType.PARAMETER_NAME) {
                if (line.equals("},") || line.equals("});")) {
                    lastParameterName = closeParameters(result, parameterValues, lastParameterName);
                } else if (line.endsWith(" ->")) {
                    lastParameterName = line.substring(0, line.length() - " ->".length());
                } else if (line.contains(" -> {")) {
                    lastParameterName = line.substring(0, line.length() - " -> {".length());
                } else {
                    lastParameterName = "TODO"; // TODO
                    int ending = line.lastIndexOf(')');
                    if (ending < 0) ending = line.lastIndexOf(',');
                    String tagName = line.substring("this.tags.".length(), ending);
                    parameterValues.add("#physicscontrol_" + tagName);

                    lastParameterName = closeParameters(result, parameterValues, lastParameterName);
                }
            } else if (blockType == BlockType.PARAMETER_VALUE) {
                args = line.split("\\.addPrimitive\\(this\\.tags\\.");
                String parameterValue;
                if (args.length == 2) {
                    line = args[1];
                    int ending = line.lastIndexOf(')');
                    if (ending < 0) ending = line.lastIndexOf(',');
                    String tagName = line.substring(0, ending);
                    parameterValue = "#physicscontrol_" + tagName;
                } else {
                    int ending = line.lastIndexOf(')');
                    if (ending < 0) ending = line.lastIndexOf(',');
                    String enumName = line.substring(line.lastIndexOf('.') + ".".length(), ending);
                    parameterValue = enumName.toLowerCase();
                }
                if (parameterValue.endsWith(")")) parameterValue = parameterValue.substring(0, parameterValue.length() - ")".length());
                parameterValues.add(parameterValue);
            }
        }

        lastParameterName = closeParameters(result, parameterValues, lastParameterName);

        System.out.println("*** RESULT: ***");
        for (String line : result) {
            System.out.println(line);
        }
    }

    private static String closeParameters(List<String> result, List<String> parameterValues, String lastParameterName) {
        if (lastParameterName == null) return null;
        if (parameterValues.isEmpty()) {
            throw new IllegalArgumentException("Parameter values not found");
        }
        if (parameterValues.size() == 1) {
            result.add("    " + lastParameterName + ": \"" + parameterValues.iterator().next() + "\"");
        } else {
            result.add("    " + lastParameterName + ":");
            for (String parameterValue : parameterValues) {
                result.add("      - \"" + parameterValue + "\"");
            }
        }
        parameterValues.clear();
        return null;
    }

    private enum BlockType {
        VERSION,
        TRIGGER_NAME,
        PARAMETER_NAME,
        PARAMETER_VALUE;

        @Nonnull
        public static BlockType of(@Nonnull String line) {
            int spaces = 0;
            for (char c : line.toCharArray()) {
                if (c != ' ') break;
                else spaces++;
            }
            if (spaces == "        ".length()) {
                return VERSION;
            }
            if (spaces == "            ".length()) {
                return TRIGGER_NAME;
            }
            if (spaces == "                ".length()) {
                return PARAMETER_NAME;
            }
            if (spaces == "                    ".length()) {
                return PARAMETER_VALUE;
            }
            throw new IllegalArgumentException("Unable to get type of line (spaces=" + spaces + ";line=" + line + ")");
        }
    }
}
