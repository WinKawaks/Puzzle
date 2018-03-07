package cn.xxx.winkawaks.puzzle.database;

/**
 * Created by 54713 on 2018/2/11.
 */

public class RecordBean {
    private String record;
    private String step;
    private String currentTime;

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}
