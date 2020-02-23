package utilities;

import java.util.GregorianCalendar;

public class DateCheckResult {
    private boolean result;
    private GregorianCalendar date;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public DateCheckResult(boolean result, GregorianCalendar date) {
        this.result = result;
        this.date = date;
    }
}
