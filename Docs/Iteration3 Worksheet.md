# Iteration 3 Worksheet

## What technical debt has been cleaned up
Show links to a commit where you paid off technical debt. Write 2-5 sentences that explain what debt was paid, and what its classification is.

We cleaned up technical debt in this [commit](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/commit/e3bd245b261d892d0dbe08041839d1d65a6519e7?merge_request_iid=113) as a part of this [Merge Request](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/merge_requests/113/diffs?commit_id=e3bd245b261d892d0dbe08041839d1d65a6519e7). We moved string manipulation from the logic layer to the UI. This was inadvertent wreckeless debt. Inadvertent because we were trying to keep the UI clean and we didn't realize how many UI opeations we were doing in the logic layer. This was wreckless because it violates the Single Responsibility Principle meaning that our app logic is tightly coupled with Android. If we wanted to make the app cross platform we may have to decouple the front end since we are making assumptions about how strings are displayed. Additionally, any changes we make in the future would take more time than otherwise since we are mixing logic with string manipulation. Both of these technical debt effects were paid off. 

## What technical debt did you leave?
What one item would you like to fix, and can’t? Anything you write will not be marked negatively. Classify this debt.



## Discuss a Feature or User Story that was cut/re-prioritized
When did you change the priority of a Feature or User Story? Why was it re-prioritized? Provide a link to the Feature or User Story. This can be from any iteration.

In the iteration 3 we decided to cut the feature [substitute an exercise](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/issues/10) , because it’s a lot of work for the short 2 weeks that we have. In this iteration we decided to focus on making less code but better quality and excellent documentation. 

## Acceptance test/end-to-end
Write a discussion about an end-to-end test that you wrote. What did you test, how did you set up the test so it was not flaky? Provide a link to that test.

## Acceptance test, untestable
What challenges did you face when creating acceptance tests? What was difficult or impossible to test?

## n+1 Acceptance test
From class, every team must post an acceptance test script to the forum. Look at team n+1’s acceptance test, and record yourself performing it (the android emulator can do screen recordings!). Post that recording to the forum, link to it here.

## Velocity/teamwork
Did your estimates get better or worse through the course? Show some evidence of the estimates/actuals from tasks.

The estimate got better, in iteration one we overestimated, because being the first iteration we didn’t know how much work we were actually capable to do and how to separate between all the members. A good example is this [dev task](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/issues/28) where we estimated 2 hours but we actually finished in 30 minutes. And in the iteration 2 with the information we learned after the first sprint, we are more capable to do a better estimation on how much work we wanted to get done. A lot of our dev tasks the estimated time is the same as the actual for example this [UI issue](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/issues/80) and the [code smell](https://code.cs.umanitoba.ca/3350-summer2023/funkyflamingos-4/-/issues/73).
