# Workflow 

We are using GitHub flow as described [here](http://scottchacon.com/2011/08/31/github-flow.html). 

## Developer task checklist 
* Make sure you understand the task and ask any necessary questions. 
* When you are ready to start, make sure the task is assigned to you and move the issue to the 'Doing' list on the board.
* Make a branch to develop from.

`git checkout -b [your name]/[task name]`

for example: 
`git checkout -b nathan/hello-world`

* Once you are finished implementing a task, push changes to main with 

`git push -u origin [branch name]`

for example: 
`git push -u origin nathan/hello-world`

* Create a Merge Request in GitLab (if you pushed with the previous command, you should see your branch in the Merge Request menu).
* Link the Developer Task in the comments, and assign/ask for review (at least one teamate should review). 
* After your changes are approved, if there are no conflicts, merge to main. 
