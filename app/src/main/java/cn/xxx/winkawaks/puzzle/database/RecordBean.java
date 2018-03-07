package cn.xxx.winkawaks.puzzle.database;

/**
 * Created by 54713 on 2018/2/11.
 */

public class RecordBean {
    private int record;
    private String step;
    private String currentTime;

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
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
