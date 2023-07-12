# Iteration 2 Worksheet 

## Paying off technical debt 

1. Refactoring time until opening/closing refactor: 

The entire GymStatusHandler (now called GymHoursHandler) was overhauld to address an Open/Closed violation. The issue was that we were making a few really big assumptions: (1) A gym only opens at most once in a day, and (2) a gym is never open overnight. This is technical debt because if we wanted our app to support overnight gyms or gyms that have multiple openings in a day, we would have to change the calculation class. In other words, this class is not be closed to modification. We payed off this technical debt by rewriting this class to support any type of gym schedule: overnight, always open, multiple openings in the same day, etc. 

The majority of this refactor was done in this [commit](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/blob/29a41996edfabb8835bee94857c602e13d04cf8b/app/src/main/java/funkyflamingos/bisonfit/logic/GymStatusHandler.java#L15) (though testing/debugging was done separately): 

This was deliberate reckless debt. Deliberate because we knew we were making big assumptions about gym schedules but since we were only planning to support the Active Living Center, we were confident about making these assumptions. Reckless because if the Active Living Center decided to change their hours or if we wanted to support gyms, we'd have to change our logic layer. 

2. Adding validation : 

In the first iteration, we didn't do any validation but in this iteration, we started paying off that debt as shown [here](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/blob/6253913661a6b84090bb756bfef6af3e7063eed0/app/src/main/java/funkyflamingos/bisonfit/logic/GymHoursHandler.java#L250). This is technical debt because we are making two assumptions again: (1) the data is always in the format that we need it to be in (2) we will always have tests to tell us what the problem is. When we switched over to a real database we quickly learned that we can't make assumptions about the data we're receiving and our app would crash without giving a helpful error message. In the absence of tests, validation can help us see where things are going wrong. This is especially true if we wanted to deploy since we don't deploy with a test suite. Without validation and a helpful error message, if the app crashes for a user, it would be extremely difficult to find the bug. This is inadvertent, reckless debt. Inadvertent because we didn't realize how important it was until we needed it, and reckless because deploying an app without validation, we are creating hours and hours of work for ourselves down the line. 

## SOLID Violation by Team 5
Here is the [Issue](https://code.cs.umanitoba.ca/3350-summer2023/highschool-hub/-/issues/71) we made for Team 5. 

This is a copy of the feedback we left: 

By my assessment, the 
[ConnectionsManager](https://code.cs.umanitoba.ca/3350-summer2023/highschool-hub/-/blob/3a59318060d572278da5ded65cffb96bb7e6702e/app/src/main/java/comp3350/highschoolhub/business/ConnectionsManager.java#L10) 
class violates the Single Responsibility principle. There are two things happening here that can be broken out into separate classes: managing requests, and managing current connections. Perhaps this difference isn't self evident but looking at the code, there are 2 static variables that are only used by the code that manages requests. The 
[getHighSchoolConnections](https://code.cs.umanitoba.ca/3350-summer2023/highschool-hub/-/blob/3a59318060d572278da5ded65cffb96bb7e6702e/app/src/main/java/comp3350/highschoolhub/business/ConnectionsManager.java#L17) 
and 
[getMatchingConnections](https://code.cs.umanitoba.ca/3350-summer2023/highschool-hub/-/blob/3a59318060d572278da5ded65cffb96bb7e6702e/app/src/main/java/comp3350/highschoolhub/business/ConnectionsManager.java#L42) 
methods don't use these variables nor do they relate to these variables which lowers the cohesiveness of this class. 

I have two recommendations to improve this code. The first recommendation is to make a RequestHandler class and move all the request specific code to this class. My other recommendation is to consider making this more OO if possible. That is try to reduce the amount of static code. If you could use instance variables instead of static variables, it will be easier to see what is responsible for what. Like if you have lots of public static code that isn't called by any instance methods then perhaps it doesn't need to be in the same class.

## Retrospective 

## Design Patterns
We use a [Singleton](https://refactoring.guru/design-patterns/singleton) to manage our database instantiation [here](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/blob/8e603a38be9ba40ae619cb64946900ad8cc7b619/app/src/main/java/funkyflamingos/bisonfit/application/Services.java#L19). This is a singlton in that  the Service only instantiates a database if that database hasn't been instantiated yet. That is there is only one instance of it. 

## Iteration 1 Feedback Fixes 
