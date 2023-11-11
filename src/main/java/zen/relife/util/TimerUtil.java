package zen.relife.util;

public class TimerUtil {
    private static long ms = getCurrentMS();
    private long lastMS;

    private static long getCurrentMS() {
        return System.currentTimeMillis();
    }

    public static boolean hasReached(float milliseconds) {
        return getCurrentMS() - ms > milliseconds;
    }

    public static void reset() {
        ms = getCurrentMS();
    }

    public boolean hasReached(final double milliseconds) {
        return getCurrentMS() - this.lastMS >= milliseconds;
    }

    public boolean delay(final float milliSec) {
        return this.getTime() - this.lastMS >= milliSec;
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }
}
