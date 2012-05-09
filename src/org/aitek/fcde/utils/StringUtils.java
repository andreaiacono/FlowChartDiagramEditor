package org.aitek.fcde.utils;

import java.util.ArrayList;

public class StringUtils {

    /**
     * returns true if the specified string is present into the array list; else
     * returns false
     *
     * @param alValues the AL of values
     * @param strValue the string to search into the AL
     * @return
     */
    public static boolean isStringPresent(String strValue, ArrayList<String> alValues) {

        // cycles on the values of the AL
        for (int j = 0; j < alValues.size(); j++) {

            // gets the j-th item
            String strVal = alValues.get(j);

            // if is equal to the string passed returns true
            if (strVal.equals(strValue)) {
                return true;
            }
        }

        // if arrives here, there's no equal string into the AL
        return false;
    }

    /**
     * returns true if the specified string is present into the array list; else
     * returns false
     *
     * @param strValue the string to search into the AL
     * @param strValues the array of values
     * @return
     */
    public static boolean isStringPresent(String strValue, String[] strValues) {

        // cycles on the values of the AL
        for (int j = 0; j < strValues.length; j++) {

            // gets the j-th item
            String strVal = strValues[j];

            // if is equal to the string passed returns true
            if (strVal.equals(strValue)) {
                return true;
            }
        }

        // if arrives here, there's no equal string into the AL
        return false;
    }

    public static int getPositionBeforeChars(String text, int position, char charToFind, int repetitions, boolean isBackward) {

        int j = position;
        while (repetitions > 0 && j > 0 && j < text.length() - 1) {
//            LoggerManager.getLogger(StringUtils.class).severe("[" + text.charAt(j) + "] rep=" + repetitions);
            if (text.charAt(j) == charToFind) {
                repetitions--;
            }
            if (isBackward) {
                j--;
            }
            else {
                j++;
            }
        }
        return j;
    }
}
