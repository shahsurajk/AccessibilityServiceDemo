# AccessibilityServiceDemo

The app create an `AccessibiltyService` which listens to the native Chrome Android app for URL changes, 
and triggers a notification if an app exists for the mentioned URL. 

## Components Used

1. `AccessiblityService` : An accessibilty service binds itself to the android system callbacks and give UI/View hierarchies
and various action changes, the app listens to `ACTION_TEXT_CHANGED` type. Check the complete doc [here](https://developer.android.com/reference/android/accessibilityservice/AccessibilityService.html)

2. `NotificationManager` : A NotificationManager API helps you handle system notifications. It requires a number of helper classes, like `Notification` etc. Check the complete doc [here](https://developer.android.com/reference/android/support/v4/app/NotificationManagerCompat.html)


Feel free to use any code without hesitation. Happy Coding!
