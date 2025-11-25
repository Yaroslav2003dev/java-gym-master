package ru.yandex.practicum.gym;

import java.awt.desktop.SystemEventListener;
import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, TrainingSession> dayMap = timetable.get(day);
        if (dayMap == null) {
            dayMap = new TreeMap<>();
            timetable.put(day, dayMap);
        }
        dayMap.put(time, trainingSession);
    }

    public Collection<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, TrainingSession> trainingSessions = timetable.get(dayOfWeek);
        if (trainingSessions == null) {
            return Collections.emptyList();
        }
        return trainingSessions.values();
    }

    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, TrainingSession> trainingSessions = timetable.get(dayOfWeek);
        if (trainingSessions == null) {
            return null;
        }
        return trainingSessions.get(timeOfDay);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> mapCoach = new HashMap<>();
        for (TreeMap<TimeOfDay, TrainingSession> value : timetable.values()) {
            for (TrainingSession trainingSession : value.values()) {
                if (mapCoach.containsKey(trainingSession.getCoach())) {
                    mapCoach.put(trainingSession.getCoach(), mapCoach.get(trainingSession.getCoach()) + 1);
                } else {
                    mapCoach.put(trainingSession.getCoach(), 1);
                }
            }
        }


            List<CounterOfTrainings> list = new ArrayList<>();

        for(Map.Entry<Coach, Integer> entry: mapCoach.entrySet()) {
            CounterOfTrainings counterOfTrainings=new CounterOfTrainings(entry.getKey(),entry.getValue());
            list.add(counterOfTrainings);
        }
        Collections.sort(list);


        return list;


    }
}
