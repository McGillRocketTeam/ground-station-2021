import sys
from PyQt5 import QtWidgets
from PyQt5.QtWidgets import QDialog, QApplication
from PyQt5.uic import loadUi
from Main import main

class LaunchSimulator(QDialog):
    # Just like in Java this is a constructor for the class LaunchSimulator
    def __init__(self):
        super(LaunchSimulator, self).__init__()
        loadUi("untitled.ui", self)
        # When the launchButton, connect to it
        if self.launchButtonClick() == "":
            print("No value has been assigned")
        else:
            self.launchButton.clicked.connect(self.launchButtonClick())


    def launchButtonClick(self):
        numOfSimulations = (self.simulationInput.text())
        main(numOfSimulations)
        print("Calling Main.py with ", numOfSimulations)


app = QApplication(sys.argv)
mainWindow = LaunchSimulator()
widget = QtWidgets.QStackedWidget()
widget = addWidget(mainWindow)
widget.setFixedWidth(700)
widget.setFixedHeight(500)
widget.show()
app.exec_()
