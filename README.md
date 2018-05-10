# How are memory leaks & crashes avoided here?
First of all, we must acknoledge the fact that Android Activities are *destroyed* and created again upon configuration changes
(i.e. orientation change of the device). Thus, background tasks such as AsyncTasks and Thread executions
deliver progress updates to a non-existent activity after said event transpires.
The best and most convenient method to overcome this issue is have the background task executed from a Fragment.
In general Fragments are destroyed and recreated along with their parent Activity when a configuration change occurs.
Calling *setRetainInstance(true)* inside the Fragment protects from destruction and recreation and retains the current instance of the Fragment when the activity is recreated.
Fragments like that are called "Worker Fragments".

Read the short description of *Fragment#setretaininstance* from the Fragment documentation:
https://developer.android.com/reference/android/app/Fragment#setretaininstance
