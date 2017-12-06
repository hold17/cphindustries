# Class `ApplicationConfig`

This class has been much discussed. This class is supposed to hold global information about the application, such as getting data from SharedPreferences and whether or not demo data is used.

Initial thought was to implement it as a singleton, however this design pattern does not work very well for android since the singleton object might be cleaned up by the garbage collector when the app is not used.

To prevent that, it is coded as a **single instance** instead. That meaning it is a simple class that extends `Application` and has a few static methods.
