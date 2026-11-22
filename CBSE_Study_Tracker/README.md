# CBSE Study Tracker — Android Studio project

This is a starter Android app project for a Class 10 CBSE study tracker.

## Included
- Dark-themed standalone Android app
- Daily study sessions with saved checkmarks
- Weekly subject planner
- Starter NCERT chapter checklist
- Separate Maths, Science and SST PYQ count fields
- Sample-paper subject and marks record
- Local progress count; data is saved on the phone using SharedPreferences

## Build the APK on Windows
1. Install the latest stable Android Studio from the official Android developer website.
2. Extract this ZIP.
3. In Android Studio, choose **Open** and select the extracted `CBSE_Study_Tracker` folder.
4. Allow Gradle sync to finish. Android Studio may download the Android Gradle plugin and SDK platform; internet is needed.
5. If prompted, install Android SDK Platform 35.
6. Select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
7. When the build finishes, click **locate** in the notification. The APK is normally at `app/build/outputs/apk/debug/app-debug.apk`.
8. Transfer that APK to your Vivo phone and install it. Android may ask you to allow installation from that file manager; only allow it for the APK you built yourself.

## Notes
- This is a starter version, not a finished Play Store release.
- The chapter list is a starter checklist and may not exactly match the latest syllabus or your school's sequence. Edit it in `MainActivity.java` as needed.
- The timetable is prefilled from the study schedule discussed earlier; adjust the times in `MainActivity.java` if your routine changes.
- The project has not been compiled in this environment. Build it in Android Studio and share any error text if Gradle reports a problem.
