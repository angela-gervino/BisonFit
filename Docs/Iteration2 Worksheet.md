# Iteration 2 Worksheet 

## Paying off technical debt 

1. Refactoring time until opening/closing refactor: 

The entire GymStatusHandler (now called GymHoursHandler) was overhauld to address an Open/Closed violation. The issue was that we were making a few really big assumptions: (1) A gym only opens at most once in a day, and (2) a gym is never open overnight. This is technical debt because if we wanted our app to support overnight gyms or gyms that have multiple openings in a day, we would have to change the calculation class. In other words, this class is not be closed to modification. We payed off this technical debt by rewriting this class to support any type of gym schedule: overnight, always open, multiple openings in the same day, etc. 

The majority of this refactor was done in this [commit](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/blame/29a41996edfabb8835bee94857c602e13d04cf8b/app/src/main/java/funkyflamingos/bisonfit/logic/GymStatusHandler.java#L15) (though testing/debugging was done separately): 

This was deliberate reckless debt. Deliberate because we knew we were making big assumptions about gym schedules but since we were only planning to support the Active Living Center, we were confident about making these assumptions. Reckless because if the Active Living Center decided to change their hours or if we wanted to support gyms, we'd have to change our logic layer. 

## SOLID Violation by Team 5

## Retrospective 

## Design Patterns

## Iteration 1 Feedback Fixes 
