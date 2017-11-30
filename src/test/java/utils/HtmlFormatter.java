package utils;

/**
 * Created by Carlos Vera on 07/28/2017.
 * This class contains some utils for format html code
 */

public class HtmlFormatter {

    /**
     * Return a formatted String using html tags. FE: It's used to show the text in Extent Report
     *
     * @param text
     * @param colour
     * @param bold
     * @param addBig
     * @param underline
     * @param startLineBreak
     * @param endLineBreak
     * @return
     */
    public static String formatHTMLText(String text, boolean bold, boolean addBig, boolean underline, String colour, boolean startLineBreak, boolean endLineBreak) {
        String html = addBold(text, bold);
        html = addBig(html, addBig);
        html = addUnderline(html, underline);
        html = addColour(html, colour);
        html = addStartLineBreak(html, startLineBreak);
        return addEndLineBreak(html, endLineBreak);
    }

    public static String addHTMLTag(String text, String classLabel) {
        return "<span class=' " + classLabel + "'>" + text + "</span>";
    }

    private static String addBold(String text, boolean bold) {
        if (bold)
            return "<b>" + text + "</b>";
        return text;
    }

    private static String addUnderline(String text, boolean underline) {
        if (underline)
            return "<u>" + text + "</u>";
        return text;
    }

    public static String addBig(String text, boolean big) {
        if (big)
            return "<big>" + text + "</big>";
        return text;
    }

    private static String addColour(String text, String colour) {
        return "<font color=\"" + colour + "\">" + text + "</font>";
    }

    private static String addStartLineBreak(String text, boolean startLineBreak) {
        if (startLineBreak)
            return "<br/>" + text;
        return text;
    }

    private static String addEndLineBreak(String text, boolean endLineBreak) {
        if (endLineBreak)
            return text + "<br/><br/>";
        return text;
    }

    /**
     * Use this method using Reporter.log BEFORE the known fail in order to print in the HTML report the Jira ticket associated
     *
     * @param jiraId
     * @param stepName
     * @return
     */
    public static String addKnownBug(String jiraId, String stepName) {
        return "<a href='https://jira.itx.com/browse/" + jiraId + "'><span class='label info'> -Known Bug: " + jiraId + " in Step '" + stepName + "'- </span></a>";
    }
}
