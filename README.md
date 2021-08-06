# Ground-station_2021-2022
Ground station repo for SAC 2022

## How to contribute

Push your changes to your individual feature branch for your project then do a pull request with that project's dev branch. **_Please avoid pushing directly to the project's dev branch._**

ex: You're working on a new feature, say the dynamic map, and you're ready to push your changes. You should follow these steps:

1. create a new branch (feature-branch)
(you must be in the ground-station-2021 repo)
```
$ git branch dynamic-map
$ git checkout dynamic-map
```
2. add your changes in the working directory to the staging area
```
$ git add .
```
(note that git add . will add ALL modifications but NOT deletions to the staging area. You can use git add -A if you want to add ALL changes, INLUDING deletions. There are other commands for adding specific files as well. Any specific git commands can be found by a quick google search)
  
3. save your changes to the local repository
```
$ git commit -m 'write your message here'
```
4. upload your local repository to the remote repository
```
$ git push --set-upstream origin dynamic-map
```
  
if you're updating an already existing feature branch without creating a new one (ex: you modified the dynamic-map code, but the branch dynamic-map already exists):
  - step 1, use:
  ```
  $ git checkout dynamic-map
  ```
  - step 4, use:
  ```
  $ git push
  ```
  
Once you believe your feature branch is ready to be merged into the project's dev-branch, create a pull request. 

## Resources

### Using JavaFX

https://openjfx.io/openjfx-docs/#install-javafx

Important: Look at the "Running HelloWorld using JavaFX 13" part and not the gradle part because I couldn't get it to function with gradle properly. Also, make sure you're using JDK 11 or newer (I'm not sure it'll work without it).

Note: If you decide to use FXML (recommended, but do as you wish), css, or images, put them in the appropriate folder in "src/main/resources/"



Incomplete List (Google stuff): 

https://medium.com/information-and-technology/test-driven-development-in-javafx-with-testfx-66a84cd561e0

https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm#targetText=The%20JavaFX%20scene%20graph%2C%20which,as%20the%20JavaFX%20Application%20thread.&targetText=Instead%2C%20use%20the%20JavaFX%20APIs%20provided%20by%20the%20javafx.

You can find additional resources and information in the drive under Avionics -> Projects -> Ground Station -> GUI -> Resources

**If you have any other questions and cannot find any resources, feel free to ask!**
