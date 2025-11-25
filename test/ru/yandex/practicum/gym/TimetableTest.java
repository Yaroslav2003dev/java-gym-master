package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    @DisplayName("Получение всех тренировок, упорядоченных по времени начала, за конкретный день недели")
    public void testGetTrainingSessionsForDay_WhenSingleSessionsExist_ReturnCountSessionsInDay() {
        //given
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        //when
        timetable.addNewTrainingSession(singleTrainingSession);
        Collection<TrainingSession> trainingSessionMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Collection<TrainingSession> trainingSessionTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        //then
        Assertions.assertEquals(1, trainingSessionMonday.size(),"Ожидается 1 занятие в понедельник");
        Assertions.assertEquals(0, trainingSessionTuesday.size(),"Ожидается 0 занятий во вторник");
    }

    @Test
    @DisplayName("Получение всех тренировок, упорядоченных по времени начала, за конкретный день недели")
    public void testGetTrainingSessionsForDay_WhenMultipleSessionsInDay_ReturnsSortedByStartTime() {
        //given
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));
        //when
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        Collection<TrainingSession> trainingSessionMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Collection<TrainingSession> trainingsSessionThursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Collection<TrainingSession> trainingSessionTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        List<TrainingSession> sessions = new ArrayList<>(trainingsSessionThursday);
        //then
        Assertions.assertEquals(1, trainingSessionMonday.size(),"Ожидается 1 занятие в понедельник");
        Assertions.assertEquals(new TimeOfDay(13, 0), sessions.get(0).getTimeOfDay(),"Ожидается время 13:00");
        Assertions.assertEquals(new TimeOfDay(20, 0), sessions.get(1).getTimeOfDay(),"Ожидается время 20:00");
        Assertions.assertEquals(0, trainingSessionTuesday.size(),"Ожидается 0 занятий во вторник");
    }

    @Test
    @DisplayName("Получение всех тренировок, начинающихся в конкретное время, за конкретный день недели.")
    public void testGetTrainingSessionsForDayAndTime_WhenSingleSessionsExist_ReturnStartTime() {
        //given
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        //when
        timetable.addNewTrainingSession(singleTrainingSession);
        TrainingSession trainingSessionMonday = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,new TimeOfDay(13, 0));
        //then
        Assertions.assertEquals(new TimeOfDay(13, 0), trainingSessionMonday.getTimeOfDay(),"Ожидается занятие в понедельник в 13:00");
        Assertions.assertNotEquals(new TimeOfDay(14, 0), trainingSessionMonday.getTimeOfDay(),"Занятий в понедельник в 14:00 нет");
    }

    @Test
    @DisplayName("Вывод тренеров в порядке убывания количества проведённых тренировок")
    public void testGetCountByCoaches_WhenTwoCoachesHaveDifferentTrainingSessions_ReturnOfTrainersInDescendingOrderOfTrainingSessions() {
        //given
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Васильева", "Анастасия", "Николаевна");
        Group groupAcrobaticForAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayTrainingSession = new TrainingSession(groupAcrobaticForAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        Coach coach2 = new Coach("Алексеев", "Алексей", "Александрович");
        Group groupAthleticForAdult = new Group("Лёгкая атлетика", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAthleticForAdult, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(19, 0));
        Group groupAthleticForChild = new Group("Лёгкая атлетика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupAthleticForChild, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupAthleticForChild, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupAthleticForChild, coach2,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));
        //when
        timetable.addNewTrainingSession(thursdayTrainingSession);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        //then
       Assertions.assertEquals(coach2, timetable.getCountByCoaches().getFirst().getCoach(),"Алексеев Алексей Александрович провёл больше всех занятий");
       Assertions.assertEquals(coach1, timetable.getCountByCoaches().get(1).getCoach(),"Васильева Анастасия Николаевна провела меньше занятий, чем Алексеев Алексей Александрович");
    }

    @Test
    @DisplayName("Вывод тренеров в порядке убывания количества проведённых тренировок")
    public void testGetCountByCoaches_WhenThreeCoachesHaveDifferentTrainingSessions_ReturnOfTrainersInDescendingOrderOfTrainingSessions() {
        //given
        Timetable timetable = new Timetable();
        Coach coach1 = new Coach("Васильева", "Анастасия", "Николаевна");
        Group groupAcrobaticForAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayTrainingSession = new TrainingSession(groupAcrobaticForAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        Coach coach2 = new Coach("Алексеев", "Алексей", "Александрович");
        Group groupAthleticForAdult = new Group("Лёгкая атлетика", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAthleticForAdult, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(19, 0));
        Group groupAthleticForChild = new Group("Лёгкая атлетика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupAthleticForChild, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupAthleticForChild, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupAthleticForChild, coach2,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));
        Coach coach3 = new Coach("Брумель", "Валерий", "Николаевич");
        Group groupHighJumpForAdult = new Group("Прыжки в высоту", Age.ADULT, 90);
        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupHighJumpForAdult, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Group groupHighJumpForChild = new Group("Прыжки в высоту", Age.CHILD, 90);
        TrainingSession mondayChildTrainingSession2 = new TrainingSession(groupHighJumpForChild, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(18, 0));
        //when
        timetable.addNewTrainingSession(thursdayTrainingSession);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        timetable.addNewTrainingSession(mondayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession2);
        //then
        Assertions.assertEquals(coach2, timetable.getCountByCoaches().getFirst().getCoach(),"Алексеев Алексей Александрович провёл больше всех занятий");
        Assertions.assertEquals(coach3, timetable.getCountByCoaches().get(1).getCoach(),"Брумель Валерий Николаевич меньше занятий, чем Алексеев Алексей Александрович, но больше, чем Васильева Анастасия Николаевна");
        Assertions.assertEquals(coach1, timetable.getCountByCoaches().get(2).getCoach(),"Васильева Анастасия Николаевна провела меньше занятий, чем Алексеев Алексей Александрович");
    }

}
