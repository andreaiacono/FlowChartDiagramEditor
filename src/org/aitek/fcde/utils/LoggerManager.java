package org.aitek.fcde.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

/**
 *
 * @author andrea
 */
public class LoggerManager {

    private static StreamHandler consoleHandler;

    static {
        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SingleLineFormatter());
    }

    public static Logger getLogger(String className) {
        Logger logger = null;

        try {
            logger = Logger.getLogger(className);
            logger.addHandler(consoleHandler);
            logger.setUseParentHandlers(false);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return logger;
    }

    public static Logger getLogger(Class clazz) {

        return getLogger(clazz.getName());
    }

    public static void flush() {
        consoleHandler.flush();
    }

    public static class SingleLineFormatter extends Formatter {

        private String LINE_SEPARATOR = System.getProperty("line.separator");
        private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

        @Override
        public String format(LogRecord record) {
            StringBuilder stringBuilder = new StringBuilder("[");
            stringBuilder.append(simpleDateFormat.format(new Date(record.getMillis()))).append("]");
            stringBuilder.append(" [TID:").append(getThreadId()).append("] [");
            stringBuilder.append(getCallingInfo(7));
            stringBuilder.append("[").append(record.getLevel().getLocalizedName()).append("]: ").append(formatMessage(record)).append(LINE_SEPARATOR);

            if (record.getThrown() != null) {

                try {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    record.getThrown().printStackTrace(pw);
                    pw.close();
                    stringBuilder.append(sw.toString());
                }
                catch (Exception ex) {
                    // ignore
                }
            }

            return stringBuilder.toString();
        }

        private String getThreadId() {

            return String.format("%04d", Thread.currentThread().getId());
        }

        private String getCallingInfo(int stackLevel) {

            StackTraceElement ste = new Throwable().fillInStackTrace().getStackTrace()[stackLevel];

            // gets the calling class
            StringBuilder stringBuilder = new StringBuilder(ste.getClassName().substring(ste.getClassName().lastIndexOf(".") + 1));
            stringBuilder.append(".").append(ste.getMethodName()).append("():").append(ste.getLineNumber()).append("] ");
            return stringBuilder.toString();
        }
    }
}
