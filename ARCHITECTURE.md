<div align=center><b><i>
This document outlines the underlying architecure of BisonFit.
</i></b></div>

## Three+ layer apprach

#### 1. UI Layer
The _UI Layer_ contains the `Java` file required for each `Activity`, and any `XML` files required. The `Java` file performs no logic, but only handles events, updates the UI, and makes calls to the _Logic Layer_. All the `Activity` files are located in `App/BisonFit/java/UI/`. All the `XML` files are located in `App/res/layout/`.

#### 2. Logic Layer
The _Logic Layer_ represents the connection between the _UI Layer_ and the _Persistence Layer_. It consists of `Java` classes. The _UI Layer_ handles an event, communicated to the _Logic Layer_, which processes the request, makes calls to the _Persitence Layer_, recieves data from the _Persistence Layer_, and communicates back to the _UI Layer_.	The _Logic Layer_ does not directly change the UI, and does not directly access the Databases. The `Java` files for the _Logic Layer_ are located in `App/BisonFit/java/Logic/`.

#### 3. Persistence Layer 
The _Persistence Layer_ is responsible for persisting data storage of the app. It recieves calls from the _Logic Layer_, processes the calls, manipulates the Databases through `SQL`, and returns data back to the _Logic Layer_. It consists of `Java` classes, and is the only layer that can make `SQL` queries. The _Persistence Layer_ `Java` files are located in `App/BisonFit/java/Persistence/`.

#### + DSO
The _Domain Specific Object (DSO)_ does not represents a layer per se., but describes the objects that represent and group data that is passed around between layers. The _Persistence Layer_ retrieves data from the Database, creates an instance of a _DSO_, and passed it to the _Logic Layer_. The _Logic Layer_ performs some logical operations on the _DSO_, and passes it to the _UI Layer_, which displays it. The same chain of calls is made in reverse order. The _Objects_ are instances of `Java` classes, located in `App/BisonFit/java/DSO/`.


## Database tables
The following are the `SQL` tables that required.

####Static Lookup Tables:
**exercise\_lookup\_table**<br>
- <ins>exercise\_id</ins><br>
- exercise\_name<br>
- exercise\_type<br>

####Workout History Tables:
**set\_history\_item**<br>
- <ins>workout\_id</ins><br>
- <ins>index</ins><br>
- exercise\_id<br>
- property\_a<br>
- property\_b<br>

**workout\_history\_item**<br>
- <ins>workout\_history\_id</ins><br>
- start\_timestamp<br>
- end\_timestamp<br>

####Saved Workouts Tables:
**saved\_routines**<br>
- <ins>workout\_id</ins><br>
- workout\_name<br>

**saved\_routine\_exercises**<br>
- <ins>workout\_id</ins><br>
- <ins>exercise\_id</ins><br>
- <ins>index</ins><br>

####Water Tracking Tables:
**water\_record**<br>
- <ins>date\_timestamp</ins><br>
- cups_drank<br>
- goal<br>

## DSO
