# Tutorial: android-minesweeper

This repository contains source code for a simple [Minesweeper](https://en.wikipedia.org/wiki/Microsoft_Minesweeper) game app for Android. The main purpose is to teach Android development and to apply the know-how to making a small game. Learning something is much simpler when you can apply the knowledge directly and make something fun using it.

My idea is to describe in a series of steps how I implemented the various features, including thoughts and principles used.
The source code is tagged before and after implementing a certain feature, so that you can checkout the code at a certain point in time to examine it to see which code does what. 


## Pre-requisites

To follow along you need:

* basic knowledge of programming (understand Java-like syntax and abstract data types)
* a [git client](https://www.sourcetreeapp.com/) to checkout the source code
* [Android Studio](https://developer.android.com/studio/index.html) installed
* To run the app on a phone, you need a [USB-driver](https://developer.android.com/studio/run/win-usb.html) and enabling [USB debugging](http://www.howtogeek.com/129728/how-to-access-the-developer-options-menu-and-enable-usb-debugging-on-android-4.2/) on the phone




## My planning

I have been told planning should make up 80% of the project time to get it right. That number might be high for a small project, but it is always a good idea before coding to break down the work, and think through uncertain areas. Below, I have listed some of the questions that arose for me, and how I resolved them. It goes without saying that I know something about programming Android apps.


### Know the rules of the game

* The playing field is made up of a grid of tiles. The playing field contains a set number of mines, placed at random tiles
* All tiles start as unrevealed
* When a player clicks a tile it is revealed
* The purpose of the game is to reveal every tile that has no mine on it
* If the tile revealed is a mine, the player loses the game
* If the tile revealed is not a mine, see if any of the neighboring tiles are mines
	* If so, the tile displays a count of the number of neighboring tiles that are mines
	* If not, display a blank tile, and reveal all surrounding tiles


### A way to display 2d graphics

I have previously been in contact with [SurfaceView](https://developer.android.com/reference/android/view/SurfaceView.html) which is a base View used for displaying custom graphics, which should be suitable for our needs. It provides a SurfaceHolder, whose lockCanvas() method give us low-level pixel access.


### A way to load tile images from file(s)

Android's Bitmap class is used to hold images in memory and load them from the Android resource system. To load multiple images, I have previously used a technique called Spritemaps (or Sprite sheets), which I think is suitable here. The idea is to stitch together all images needed into a single image and let the program separate out which part of the bigger image to use at one time. This way the loading is more efficient and the images take up less memory once loaded. 

The drawBitmap method in the [Canvas](https://developer.android.com/reference/android/graphics/Canvas.html) class draws a subsection of a Bitmap onto a subsection of a Canvas. This makes sense for us, since we do not want to draw the full spritemap image at once and the drawing target being a Canvas, which is what lockCanvas() gives us.


### How large playing field should be displayed?

As Android devices come with various screen sizes this can be worth thinking about. To keep things simple I have chosen a fixed size of tiles as 100x100 pixels. 
To simplify screen rotation I have chosen to have the playing field square for the moment. 

This is how I find the size:

Take the smallest value of width and height, and use the closest smaller value to fit the maximum number of tiles evenly.

E.g: if the screen is 2048x1024, the smaller of width and height is 1024. To get an even number of tiles, divide 1024/100 and round down = 10. 10x100 = 1000, so the playing field will be 1000x1000 pixels.

Hope this makes sense. The result is not very good looking, and is too rigid to be of use to a real game. Thus this is an obvious area of improvement for you!


### What should happen on screen rotation?

This is something for all Android apps to [bother with](https://developer.android.com/guide/topics/resources/runtime-changes.html). I save and restore data for the playing field so that we can continue displaying an identical grid configuration regardless of screen orientation. If the grid had been just any rectangle, we would need a different solution here. 


### What features make up the app and which order should they be implemented?

## Implementation steps

| Step   | Outcome
| ------ | ---------------------------------------
| step 0 | [New project](step0.md)
| step 1 | [Basic structure and screen layout](step1.md)
| step 2 | [Base code to handle screen rotation](step2.md)
| step 3 | [Data structures of the game](step3.md)
| step 4 | [Drawing tiles](step4.md)
| step 5 | [Keeping board state on screen rotation](step5.md)
| step 6 | [Interacting with the game](step6.md)
| step 7 | [Game logic](step7.md)
| step 8 | [Score keeping](step8.md)


From here you can click on a step above to get a description of that code. You can also checkout the a git tag corresponding to the step (e.g. step 4 has tag step4) to see the resulting code after the step has been implemented. By examining indvidual commits you can see how the app has evolved.



		