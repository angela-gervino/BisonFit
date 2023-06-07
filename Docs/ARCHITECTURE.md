# ARCHITECTURE
<div align=center><b><i>
This document outlines the underlying architecure of BisonFit.
</i></b></div>

## Three+ layer apprach

#### 1. UI Layer
The _UI Layer_ contains the Java file required for each Activity, and any XML files required. The Java file performs no logic, but only handles events, updates the UI, and makes calls to the _Logic Layer_. All the Activity files are located in:

`app/src/main/java/funkyflamingos/bisonfit/ui` 

All the XML files are located in: 

`app/src/main/res/layout`

#### 2. Logic Layer
The _Logic Layer_ represents the connection between the _UI Layer_ and the _Persistence Layer_. It consists of Java classes. The _UI Layer_ handles an event, communicated to the _Logic Layer_, which processes the request, makes calls to the _Persitence Layer_, recieves data from the _Persistence Layer_, and communicates back to the _UI Layer_.	The _Logic Layer_ does not directly change the UI, and does not directly access the Databases. The Java files for the _Logic Layer_ are located in: 

`app/src/main/java/funkyflamingos/bisonfit/logic` 

#### 3. Persistence Layer 
The _Persistence Layer_ is responsible for persisting data storage of the app. It recieves calls from the _Logic Layer_, processes the calls, manipulates the Databases through SQL, and returns data back to the _Logic Layer_. It consists of Java classes, and is the only layer that can make SQL queries. The _Persistence Layer_ Java files are located in:

`app/src/main/java/funkyflamingos/bisonfit/persistence`

#### + DSO
The _Domain Specific Object (DSO)_ does not represents a layer per se., but describes the objects that represent and group data that is passed around between layers. The _Persistence Layer_ retrieves data from the Database, creates an instance of a _DSO_, and passed it to the _Logic Layer_. The _Logic Layer_ performs some logical operations on the _DSO_, and passes it to the _UI Layer_, which displays it. The same chain of calls is made in reverse order. The _Objects_ are instances of Java classes, located in:

`app/src/main/java/funkyflamingos/bisonfit/objects`

### Iteration 1 Diagram

![architecture](Architecture-1.jpg)

## Database tables
The following are the SQL tables that are required. Underlined attributes make up the primary key. Tables with same attribute names represent data of same category, linked according to the values of common attributes.

#### Static Lookup Tables:
Tables needed to lookup exercises. Not meant to be changed by the user.<br><br>
**exercise\_lookup**<br>
|Attribute|Type|Description|
|-|-|-|
|<ins>exercise_id<ins>|`int`|Unique exercise identifier
|exercise_name|`varchar`|Display name of exercise
|exercise_type|`int`|`0` for weight exercise, `1` for other (eg.running)

#### Saved Routines Tables:
Tables that contain the routines that have been created and saved by the user or exist by deafult.<br><br>
**saved\_routines**<br>
|Attribute|Type|Description|
|-|-|-|
|<ins>routine\_id</ins>|`int`|Unique routine identifier
|routine_name|`varchar`|Display name of routine

**saved\_routine\_exercises**<br>
|Attribute|Type|Description|
|-|-|-|
|<ins>routine\_id</ins>|`int`|Identifier of routine to which this exercise belongs|
|<ins>exercise\_id</ins>|`int`|Identifier of exercise
|<ins>index</ins>|`int`|The position of this exercise in this routine

#### Workout Record Tables:
Tables that hold the record of routines and exercises done by the user.<br><br>

**performed\_routine\_record**<br>
|Attribute|Type|Description|
|-|-|-|
|<ins>routine\_record\_id</ins>|`int`|Unique identifier of performed routine|
|routine\_id|`int`|Identifier of routine performed
|start_timestamp|`timestamp`|Unix time stamp at start of routine
|end\_timestamp|`timestamp`|Unix time stamp at end of routine

**performed\_exercise\_record**<br>
|Attribute|Type|Description|
|-|-|-|
|<ins>routine\_record\_id</ins|`int`|Identifier of performed routine
|<ins>index</ins>|`int`|The position of exercise in performed routine
|exercise\_id|`int`|Identifier of exercise
|weight|`int`|The weight in lbs performed in this set, `-1` if not required
|reps|`int`|The number of repitions performed in this set, `-1` if not required
|distance|`int`|The distance in meters covered, `-1` if not required
|time|`int`|The time in seconds it took to cover the distance, `-1` if not required


#### Water Tracking Tables:
Tables that hold data on daily water drinking goal progress.
|Attribute|Type|Description|
|-|-|-|
|<ins>date\_timestamp<ins>|`timestamp`|Unix time stamp at 12:00AM of day in reference|
|cups_drank|`int`|The number of cups drank this day
|goal|`int`|The goal for this day 

