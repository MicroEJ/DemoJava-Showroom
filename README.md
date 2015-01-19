# Overview
This showroom application demonstrates MicroEJ UI libraries features.
[Video](http://www.youtube.com/watch?v=Wm-UgvCvTKI).

On start-up, the application starts the desktop. It contains several applications, each represented with an icon (picto). The applications are registered with the component framework (`ej.components`). Touch the picto to start the application. Go back to the desktop from any application by touching the home picto in the top right corner.  

Each application has an additional project .activor that is used by the component framework (`ej.components`) to add itself to the desktop (provide name, entry point, picto, how to start/top the application).

Some demos are using the MICROUI LAYERS library and requires a specific hardware acceleration for transparency to run smoothly. To run this demo on devices without hardware transparency acceleration,  

Available applications:

**Settings**: This demo represents a settings menu you can find on your smartphone. It demonstrates widgets like scrolling list/text, font zoom in/out, check-box, switch, progress bar, horizontal/vertical lists.
Screens left/right animations are configured to use the MICROUI-LAYERS library with fade in of the incoming screen (transparency 0 to 255) and fade out of the out-coming screen (transparency 255 to 0).
Project: `com.is2t.demo.settings`

**Graph**: This demo shows a curve representing randomly generated data. 
- You can affect the values manually by dragging the right cursor. 
- The colourized drawing below the curve can be dynamically disabled with one of the one board button.
- Touching the curve will stop it and display the current curve value of the touched area.
This demo uses the NLS library for the top messages in US and FR translations.
Project: `com.is2t.demo.graph`

**Domotic**: A simulation of a domotic application which can control the shutters, ligh and temperature in each room of an house. No source code are provided for this demo, only a .jar.
Project: `com.is2t.demo.domotic`

**Circular Progress Bar**: demonstrates a circular progress bar. Touch the setting value on the left to set the new value. The light value on the right will progressively reach this value.
Project: `com.is2t.demo.circularprogressbar`

**Elastic**: Elastic is a small game where the goal is to move the spider on the screen which can bounce against the wall depending from the speed. An anti-aliased line is drawn between your finger and the spider.
Project: `com.is2t.demo.elastic`

**Stretch**: The demos shows the deformation of an image.
Project: `com.is2t.demo.stretch`

**Desktop**: is the desktop for all applications. Holds the weather, date and clock widgets.
Project: `com.is2t.com.showroom`

Other projects:
- `com.is2t.fonts`: contains the common fonts used in the applications.
- `com.is2t.layers`: layers framework.
- `com.is2t.transition`: transition framework which use layers.
- `com.is2t.utilities`: various utilities.
- `com.is2t.widgets`: main widgets.

This demo includes MicroEJ launchers configuration for the following Java platform:
- simulator
- ST STM32F429I-EVAL
- Renesas RSK+RZA1H

Each launcher can be configured in Run -> Run configuration... -> MicroEJ Application -> Showroom XXXX -> JRE: set VM arguments to the specific options values.
- `-Dmicroej.java.property.com.is2t.demo.NoLayer=SET` to deactivate the layers in the transitions between pages.
- `-Dmicroej.java.property.com.is2t.demo.NoAnimation=SET` to deactivate totally the transitions between pages.


# Project Setup

First of all, you have to download the entire repository by using the `Download` button or by cloning the repository. After having retreived the repository content, open your MicroEJ and then import _Existing project into workspace_ by selecting either the ZIP file or the root directory.

## Requirements

- JRE 7 (or later) x86
- MicroEJ 3.1 or later
- Java platform with (at least): MicroUI1.5, MWT1.0, EDC1.2, EJ.COMPONENTS2.0, MICROUI LAYER 2.0.0, EJ.FLOW2.0, EJ.FLOW.MWT3.0, EJ.MOTION2.1
- Hardware: this demo has been tested on ST STM32F429I-EVAL (480x272 display) and Renesas RSK+RZA1H (800x480 display) boards.

## Project structure

Each project follows this structure:
  - `src/`
  	- Java sources
  	- Resources: images, fonts...
  - `launches/`: MicroEJ launches in the project `com.is2t.demo.showroom`

# Usage
To launch the application, right-click on the project, select _Run as_, _MicroEJ Application_ and choose _Showroom (Simulation)_ Another launcher is available to execute on the target board.

# Changes
- Dec 2014: initial version

# License
See the license file `LICENSE.md` located at the root of this repository.
