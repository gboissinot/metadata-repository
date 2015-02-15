package metadatarepo.core.version;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Gregory Boissinot
 */
public class VersionService {

    private static Pattern BOM_VERSION_PATTERN = Pattern.compile("\\d+.\\d+.\\d+.");

    public String incrementAndGet(String actualVersion) {

        if (actualVersion == null) {
            throw new IllegalStateException("You must provide a version.");
        }

        Matcher matcher = BOM_VERSION_PATTERN.matcher(actualVersion);
        if (isValidInputVersion(matcher)) {
            throw new IllegalStateException("You must provide a version to follow the pattern (INT.INT.INT).");
        }

        return incrementAndGetLastDigit(matcher);
    }

    private boolean isValidInputVersion(Matcher matcher) {
        return matcher.matches();
    }

    private String incrementAndGetLastDigit(Matcher matcher) {
        String lastDigitStr = matcher.group(3);
        int lastDigit = Integer.parseInt(lastDigitStr);
        return buildBOMVersion(matcher.group(1), matcher.group(2), ++lastDigit);
    }

    private String buildBOMVersion(String year, String runNum, int increment) {
        StringBuilder versionBuilder = new StringBuilder();
        versionBuilder
                .append(year)
                .append(".")
                .append(runNum)
                .append(".")
                .append(increment);
        return versionBuilder.toString();
    }
}
