# Iteration 2 Worksheet 

## Paying off technical debt 

1. Refactoring time until opening/closing refactor: 

The entire GymStatusHandler (now called GymHoursHandler) was overhauld to address an Open/Closed violation. The issue was that we were making a few really big assumptions: (1) A gym only opens at most once in a day, and (2) a gym is never open overnight. This is technical debt because if we wanted our app to support overnight gyms or gyms that have multiple openings in a day, we would have to change the calculation class. In other words, this class is not be closed to modification. We payed off this technical debt by rewriting this class to support any type of gym schedule: overnight, always open, multiple openings in the same day, etc. 

The majority of this refactor was done in this [commit](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/blob/29a41996edfabb8835bee94857c602e13d04cf8b/app/src/main/java/funkyflamingos/bisonfit/logic/GymStatusHandler.java#L15) (though testing/debugging was done separately): 

This was deliberate reckless debt. Deliberate because we knew we were making big assumptions about gym schedules but since we were only planning to support the Active Living Center, we were confident about making these assumptions. Reckless because if the Active Living Center decided to change their hours or if we wanted to support gyms, we'd have to change our logic layer. 

2. Adding validation : 

In the first iteration, we didn't do any validation but in this iteration, we started paying off that debt as shown [here](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/blob/29a41996edfabb8835bee94857c602e13d04cf8b/app/src/main/java/funkyflamingos/bisonfit/logic/GymStatusHandler.java#L47). This is technical debt because we are making two assumptions again: (1) the data is always in the format that we need it to be in (2) we will always have tests to tell us what the problem is. When we switched over to a real database we quickly learned that we can't make assumptions about the data we're receiving and our app would crash without giving a helpful error message. In the absence of tests, validation can help us see where things are going wrong. This is especially true if we wanted to deploy since we don't deploy with a test suite. Without validation and a helpful error message, if the app crashes for a user, it would be extremely difficult to find the bug. This is inadvertent, reckless debt. Inadvertent because we didn't realize how important it was until we needed it, and reckless because deploying an app without validation, we are creating hours and hours of work for ourselves down the line. 

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
The most important change that was implemented from [Retrospective 1](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/blob/main/Docs/Retrospective1.md) is that our code review quality has went up. There are multiple instances where a merge request was not approved, but comments with suggestions where made.

In [one instance](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/merge_requests/62#note_106097), the Reviewer catches a potential inefficiency, which was fixed in the [next](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/merge_requests/62/diffs?commit_id=3a4c520fac13c856be4c194b7360f5b11ae5d643#36f45c8f15c31153693f12c316ff06b9400bf927_41_35) commit. Even though the current state of the app will never save the name more than once, there was nothing in the database layer that would delete the old entries when a new one is created.
Only the most recent name entry is used, so leaving old entries creates data that 1) is never used and 2) cannot be deleted. This _could_ lead to dead and inaccesible data in the future.

There was also [another](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/merge_requests/65#note_106288) time when the Reviewer caught a potentially dangerours mistake. Even though the unit tests were written, they were not added to `allTests`. This could have led to a situation where it seemed like all tests where passing, but in reality, some potentially failing tests are not being ran. If those tests would actually fail, we might have submitted code with bugs, not even knowing it. Thankfully, we are implementing [Retrospective 1](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/blob/main/Docs/Retrospective1.md) and doing code review.

## Design Patterns
We use a [Singleton](https://refactoring.guru/design-patterns/singleton) to manage our database instantiation [here](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/blob/8e603a38be9ba40ae619cb64946900ad8cc7b619/app/src/main/java/funkyflamingos/bisonfit/application/Services.java#L19). This is a singlton in that  the Service only instantiates a database if that database hasn't been instantiated yet. That is there is only one instance of it. 

## Iteration 1 Feedback Fixes (How we avoid technical debt)
Writing maintainable code is crucial for avoiding technical debt. The first step towards achieving maintainability is writing understandable code. We have made a conscious effort to address this during code reviews.

In [this](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/merge_requests/59#note_105922) instance, the Reviewer is making sure the method name clearly describes what the method does. By ensuring that the method name accurately reflects its purpose, anyone reading the code can immediately grasp what the method does without having to delve into its implementation details. This approach enhances code comprehension, making it simpler to maintain or expand. Consequently, technical debt is avoided.

[Here](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/merge_requests/59#note_105924), the Reviewer is again, mindful of code readability and maintainability. Instead of burying the loop exit condition within a block, the condition was placed in an obvious and intuitive location. This decision streamlines future modifications, reviews, and debugging of the loop since it's clear where the exit condition is modified. By enhancing the ease of working with this code, it becomes more maintainable, thereby avoiding technical debt.
