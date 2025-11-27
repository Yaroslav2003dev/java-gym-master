package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(day);
        if (dayMap == null) {
            dayMap = new TreeMap<>();
            timetable.put(day, dayMap);
        }
        List<TrainingSession> trainingSessions=dayMap.get(time);
        if(trainingSessions==null){
            trainingSessions=new ArrayList<>();
            dayMap.put(time,trainingSessions);
        }

        trainingSessions.add(trainingSession);
    }

    public Collection<List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessions = timetable.get(dayOfWeek);
        if (trainingSessions == null) {
            return Collections.emptyList();
        }
        return trainingSessions.values();
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessions = timetable.get(dayOfWeek);
        if (trainingSessions == null) {
            return Collections.emptyList();
        }
        return trainingSessions.get(timeOfDay);
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> mapCoach = new HashMap<>();
        for (TreeMap<TimeOfDay, List<TrainingSession>> value : timetable.values()) {
            for (List<TrainingSession> trainingSessions : value.values()) {
                for (TrainingSession trainingSession: trainingSessions) {
                    if (mapCoach.containsKey(trainingSession.getCoach())) {
                        mapCoach.put(trainingSession.getCoach(), mapCoach.get(trainingSession.getCoach()) + 1);
                    } else {
                        mapCoach.put(trainingSession.getCoach(), 1);
                    }
                }
            }
        }


            List<CounterOfTrainings> list = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry: mapCoach.entrySet()) {
            CounterOfTrainings counterOfTrainings = new CounterOfTrainings(entry.getKey(),entry.getValue());
            list.add(counterOfTrainings);
        }
        Collections.sort(list);


        return list;


    }
}
