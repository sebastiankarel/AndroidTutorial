# Android Tutorial Application
## Task 1
Implement a simple calculator as seen in the screenshot. Add the UI elements to `activity_main.xml` and the corresponding logic to `MainActivity.kt`. Pi (3.14159265359) and e (2,7182818284) should simply insert the number.
![Screenshot_1633329558](https://user-images.githubusercontent.com/33089293/135804852-0d5ff424-7d3f-40d3-922f-371fddf851dd.png)
### Hints
* The unicode symbols for Pi and e are \u03C0 and \u212F
* Place the button and display colors in res/colors.xml
* When using MaterialButton as element, color it with e.g. app:backgroundTint="@color/black"
* When setting the width or height of an element in a ConstraintLayout to 0dp, it stretches out according to the anker points. You need to constrain both sides, though
* To make the display look like in the screenshot, simply change the background color with android:background="@color/grey" and add a padding
* Use margins (e.g. android:layout_marginHorizontal) for styling
* For now add the logic to the activity class
* Remeber: `val button = findViewById<MaterialButton>(R.id.my_button)` and `button.setOnClickListener { ... }` and `textView.text = "3.1415"`

## Task 2
Implement a simple maze like game with multiple screen using the navigation framework. Each screen shows 3 buttons (Door 1, Door 2, Door 3). On each screen, one of the buttons leads to the next screen, the others navigate back to the first screen. In the former case a Toast message should indicate success (e.g. "Correct!") and in the latter case the message should indicate that the user chose the wrong door.
### Hints
* Create a base fragment and inherit from it to save some code. The layout can be inflated in the base fragment. The click behaviour should be in each derived class.
* You can use a global navigation action to take the user back to the beginning from any screen. This drastically reduces the number of navigation actions.
* Consider overiding onBackPressed() since the user should not be able to go back.
