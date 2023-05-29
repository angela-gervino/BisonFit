# Git Reference 
This is meant to be a way for us to share our personal solutions to git and version control FAQs. 

## Resolving merge conflicts 
An example workflow for dealing with merge conflicts:
* Pull main into your current branch with: 
`git pull origin main`
and handle merge conflicts locally in your IDE then push back to Merge Request with: `git push` (assuming you alread created the Merge Request).
* Tip: to mitigate horrible merge conflicts, you may want to make a habit of pulling main throughout development (i.e. `git pull origin main`). This can make for a messy commit history but this way you stay up to date with other teamate's changes. You will still have merge conflicts but you will be dealing with them as they come up rathar than all at once. 
