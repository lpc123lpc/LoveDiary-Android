package com.example.lovediary.ui.tools.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class AlarmRepository {
    private AlarmDao alarmDao;
    private LiveData<List<Alarm>> alarmsLiveData;

    public AlarmRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getDatabase(application);
        alarmDao = db.alarmDao();
        alarmsLiveData = alarmDao.getAlarms();
    }

    public void insert(final Alarm alarm) {
        AlarmDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                alarmDao.insert(alarm);
            }
        });
    }

    public void update(final Alarm alarm) {
        AlarmDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                alarmDao.update(alarm);
            }
        });
    }

    public LiveData<List<Alarm>> getAlarmsLiveData() {
        return alarmsLiveData;
    }
}
