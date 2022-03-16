# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'GUI_design_V2.ui'
#
# Created by: PyQt5 UI code generator 5.15.2
#
# WARNING: Any manual changes made to this file will be lost when pyuic5 is


from PyQt5 import QtCore, QtGui, QtWidgets
#from .simulator.main.main import *
#from Descent_Simulator.simulator.main.main import main

class Ui_Dialog(object):
    def setupUi(self, Dialog):
        Dialog.setObjectName("Dialog")
        Dialog.resize(1001, 850)
        Dialog.setStyleSheet("background-color: rgb(74, 75, 72);")


        self.appFrame = QtWidgets.QFrame(Dialog)
        self.appFrame.setGeometry(QtCore.QRect(0, 0, 1000, 850))
        self.appFrame.setStyleSheet("background-color: rgb(54, 55, 56);")
        self.appFrame.setFrameShape(QtWidgets.QFrame.StyledPanel)
        self.appFrame.setFrameShadow(QtWidgets.QFrame.Raised)
        self.appFrame.setObjectName("appFrame")

        self.mrtLabel = QtWidgets.QLabel(self.appFrame)
        self.mrtLabel.setGeometry(QtCore.QRect(250, -30, 690, 150))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(42)
        font.setBold(False)
        font.setItalic(False)
        font.setWeight(50)
        self.mrtLabel.setFont(font)
        self.mrtLabel.setAutoFillBackground(False)
        self.mrtLabel.setStyleSheet("font: 42pt \"Impact\";\n"
"color: rgb(207, 28, 22);\n"
"")
        self.mrtLabel.setObjectName("mrtLabel")

        self.inputFrame = QtWidgets.QFrame(self.appFrame)
        self.inputFrame.setGeometry(QtCore.QRect(44, 80, 911, 421))
        self.inputFrame.setStyleSheet("background-color: rgb(145, 145, 145);")
        self.inputFrame.setFrameShape(QtWidgets.QFrame.StyledPanel)
        self.inputFrame.setFrameShadow(QtWidgets.QFrame.Raised)
        self.inputFrame.setObjectName("inputFrame")

        self.apogeeLabel = QtWidgets.QLabel(self.inputFrame)
        self.apogeeLabel.setGeometry(QtCore.QRect(10, 10, 92, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.apogeeLabel.setFont(font)
        self.apogeeLabel.setObjectName("apogeeLabel")

        self.massLabel = QtWidgets.QLabel(self.inputFrame)
        self.massLabel.setGeometry(QtCore.QRect(410, 10, 71, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.massLabel.setFont(font)
        self.massLabel.setObjectName("massLabel")

        self.speedRailLabel = QtWidgets.QLabel(self.inputFrame)
        self.speedRailLabel.setGeometry(QtCore.QRect(210, 10, 92, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.speedRailLabel.setFont(font)
        self.speedRailLabel.setObjectName("speedRailLabel")

        self.rocketCSALabel = QtWidgets.QLabel(self.inputFrame)
        self.rocketCSALabel.setGeometry(QtCore.QRect(620, 10, 61, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.rocketCSALabel.setFont(font)
        self.rocketCSALabel.setObjectName("rocketCSALabel")

        self.mainAltLabel = QtWidgets.QLabel(self.inputFrame)
        self.mainAltLabel.setGeometry(QtCore.QRect(10, 240, 121, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.mainAltLabel.setFont(font)
        self.mainAltLabel.setObjectName("mainAltLabel")

        self.mainParaCSALabel = QtWidgets.QLabel(self.inputFrame)
        self.mainParaCSALabel.setGeometry(QtCore.QRect(210, 240, 121, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.mainParaCSALabel.setFont(font)
        self.mainParaCSALabel.setObjectName("mainParaCSALabel")

        self.mainVCDLabel = QtWidgets.QLabel(self.inputFrame)
        self.mainVCDLabel.setGeometry(QtCore.QRect(410, 240, 111, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.mainVCDLabel.setFont(font)
        self.mainVCDLabel.setObjectName("mainVCDLabel")

        self.mainTCDLabel = QtWidgets.QLabel(self.inputFrame)
        self.mainTCDLabel.setGeometry(QtCore.QRect(620, 240, 121, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.mainTCDLabel.setFont(font)
        self.mainTCDLabel.setObjectName("mainTCDLabel")

        self.longitudeLabel = QtWidgets.QLabel(self.inputFrame)
        self.longitudeLabel.setGeometry(QtCore.QRect(620, 340, 131, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.longitudeLabel.setFont(font)
        self.longitudeLabel.setObjectName("longitudeLabel")

        self.windDataLabel = QtWidgets.QLabel(self.inputFrame)
        self.windDataLabel.setGeometry(QtCore.QRect(780, 245, 56, 21))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.windDataLabel.setFont(font)
        self.windDataLabel.setObjectName("windDataLabel")

        self.apogeeInput = QtWidgets.QLineEdit(self.inputFrame)
        self.apogeeInput.setGeometry(QtCore.QRect(10, 40, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.apogeeInput.setFont(font)
        self.apogeeInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.apogeeInput.setObjectName("apogeeInput")

        self.speedInput = QtWidgets.QLineEdit(self.inputFrame)
        self.speedInput.setGeometry(QtCore.QRect(210, 40, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.speedInput.setFont(font)
        self.speedInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.speedInput.setObjectName("speedInput")

        self.massInput = QtWidgets.QLineEdit(self.inputFrame)
        self.massInput.setGeometry(QtCore.QRect(410, 40, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.massInput.setFont(font)
        self.massInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.massInput.setObjectName("massInput")

        self.rocketCSAInput = QtWidgets.QLineEdit(self.inputFrame)
        self.rocketCSAInput.setGeometry(QtCore.QRect(620, 40, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.rocketCSAInput.setFont(font)
        self.rocketCSAInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.rocketCSAInput.setObjectName("rocketCSAInput")

        self.drogueAltInput = QtWidgets.QLineEdit(self.inputFrame)
        self.drogueAltInput.setGeometry(QtCore.QRect(10, 160, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.drogueAltInput.setFont(font)
        self.drogueAltInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.drogueAltInput.setObjectName("drogueAltInput")

        self.drogueParaCSAInput = QtWidgets.QLineEdit(self.inputFrame)
        self.drogueParaCSAInput.setGeometry(QtCore.QRect(210, 160, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.drogueParaCSAInput.setFont(font)
        self.drogueParaCSAInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.drogueParaCSAInput.setObjectName("drogueParaCSAInput")

        self.drogueVCDInput = QtWidgets.QLineEdit(self.inputFrame)
        self.drogueVCDInput.setGeometry(QtCore.QRect(410, 160, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.drogueVCDInput.setFont(font)
        self.drogueVCDInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.drogueVCDInput.setObjectName("drogueVCDInput")

        self.drogueTCDInput = QtWidgets.QLineEdit(self.inputFrame)
        self.drogueTCDInput.setGeometry(QtCore.QRect(620, 160, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.drogueTCDInput.setFont(font)
        self.drogueTCDInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.drogueTCDInput.setObjectName("drogueTCDInput")

        self.zenithLabel = QtWidgets.QLabel(self.inputFrame)
        self.zenithLabel.setGeometry(QtCore.QRect(10, 340, 71, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.zenithLabel.setFont(font)
        self.zenithLabel.setObjectName("zenithLabel")

        self.mainAltInput = QtWidgets.QLineEdit(self.inputFrame)
        self.mainAltInput.setGeometry(QtCore.QRect(10, 270, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.mainAltInput.setFont(font)
        self.mainAltInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.mainAltInput.setObjectName("mainAltInput")

        self.mainParaCSAInput = QtWidgets.QLineEdit(self.inputFrame)
        self.mainParaCSAInput.setGeometry(QtCore.QRect(210, 270, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.mainParaCSAInput.setFont(font)
        self.mainParaCSAInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.mainParaCSAInput.setObjectName("mainParaCSAInput")

        self.mainVCDInput = QtWidgets.QLineEdit(self.inputFrame)
        self.mainVCDInput.setGeometry(QtCore.QRect(410, 270, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.mainVCDInput.setFont(font)
        self.mainVCDInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.mainVCDInput.setObjectName("mainVCDInput")

        self.mainTCDInput = QtWidgets.QLineEdit(self.inputFrame)
        self.mainTCDInput.setGeometry(QtCore.QRect(620, 270, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.mainTCDInput.setFont(font)
        self.mainTCDInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.mainTCDInput.setObjectName("mainTCDInput")

        self.windDataInput = QtWidgets.QLineEdit(self.inputFrame)
        self.windDataInput.setGeometry(QtCore.QRect(750, 270, 150, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.windDataInput.setFont(font)
        self.windDataInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.windDataInput.setObjectName("windDataInput")

        self.zenithInput = QtWidgets.QLineEdit(self.inputFrame)
        self.zenithInput.setGeometry(QtCore.QRect(10, 370, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.zenithInput.setFont(font)
        self.zenithInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.zenithInput.setObjectName("zenithInput")

        self.azimuthInput = QtWidgets.QLineEdit(self.inputFrame)
        self.azimuthInput.setGeometry(QtCore.QRect(210, 370, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.azimuthInput.setFont(font)
        self.azimuthInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.azimuthInput.setObjectName("azimuthInput")

        self.latitudeInput = QtWidgets.QLineEdit(self.inputFrame)
        self.latitudeInput.setGeometry(QtCore.QRect(410, 370, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.latitudeInput.setFont(font)
        self.latitudeInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.latitudeInput.setObjectName("latitudeInput")


        self.drogueParaCSALabel = QtWidgets.QLabel(self.inputFrame)
        self.drogueParaCSALabel.setGeometry(QtCore.QRect(210, 130, 121, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.drogueParaCSALabel.setFont(font)
        self.drogueParaCSALabel.setObjectName("drogueParaCSALabel")

        self.drogueAltLabel = QtWidgets.QLabel(self.inputFrame)
        self.drogueAltLabel.setGeometry(QtCore.QRect(10, 130, 131, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.drogueAltLabel.setFont(font)
        self.drogueAltLabel.setObjectName("drogueAltLabel")

        self.drogueVCDLabel = QtWidgets.QLabel(self.inputFrame)
        self.drogueVCDLabel.setGeometry(QtCore.QRect(410, 130, 111, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.drogueVCDLabel.setFont(font)
        self.drogueVCDLabel.setObjectName("drogueVCDLabel")

        self.drogueTCDLabel = QtWidgets.QLabel(self.inputFrame)
        self.drogueTCDLabel.setGeometry(QtCore.QRect(620, 130, 121, 31))
        self.drogueTCDLabel.setFont(font)
        self.drogueTCDLabel.setObjectName("drogueTCDLabel")
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)

        self.azimuthLabel = QtWidgets.QLabel(self.inputFrame)
        self.azimuthLabel.setGeometry(QtCore.QRect(210, 340, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.azimuthLabel.setFont(font)
        self.azimuthLabel.setObjectName("azimuthLabel")

        self.latitudeLabel = QtWidgets.QLabel(self.inputFrame)
        self.latitudeLabel.setGeometry(QtCore.QRect(410, 340, 91, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        self.latitudeLabel.setFont(font)
        self.latitudeLabel.setObjectName("latitudeLabel")

        self.longitudeInput = QtWidgets.QLineEdit(self.inputFrame)
        self.longitudeInput.setGeometry(QtCore.QRect(620, 370, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.longitudeInput.setFont(font)
        self.longitudeInput.setStyleSheet("color: rgb(255, 255, 255);")
        self.longitudeInput.setObjectName("longitudeInput")

        self.simulationLabel = QtWidgets.QLabel(self.inputFrame)
        self.simulationLabel.setGeometry(QtCore.QRect(780, 340, 125, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(10)
        font.setBold(False)
        font.setItalic(False)
        font.setWeight(50)
        self.simulationLabel.setFont(font)
        self.simulationLabel.setStyleSheet("\n"
"font: 10pt \"Impact\";")
        self.simulationLabel.setObjectName("simulationLabel")

        self.simulationInput_7 = QtWidgets.QLineEdit(self.inputFrame)
        self.simulationInput_7.setGeometry(QtCore.QRect(780, 370, 81, 31))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(12)
        self.simulationInput_7.setFont(font)
        self.simulationInput_7.setStyleSheet("color: rgb(255, 255, 255);")
        self.simulationInput_7.setObjectName("simulationInput_7")

        self.circularFrame = QtWidgets.QFrame(self.appFrame)
        self.circularFrame.setGeometry(QtCore.QRect(260, 510, 461, 311))
        self.circularFrame.setFrameShape(QtWidgets.QFrame.StyledPanel)
        self.circularFrame.setFrameShadow(QtWidgets.QFrame.Raised)
        self.circularFrame.setObjectName("circularFrame")
        self.ProgressBarFrame = QtWidgets.QFrame(self.circularFrame)
        self.ProgressBarFrame.setGeometry(QtCore.QRect(70, 0, 320, 320))
        self.ProgressBarFrame.setStyleSheet("")
        self.ProgressBarFrame.setFrameShape(QtWidgets.QFrame.NoFrame)
        self.ProgressBarFrame.setFrameShadow(QtWidgets.QFrame.Raised)
        self.ProgressBarFrame.setObjectName("ProgressBarFrame")
        self.circularProgressBar = QtWidgets.QFrame(self.ProgressBarFrame)
        self.circularProgressBar.setGeometry(QtCore.QRect(10, 10, 300, 300))
        self.circularProgressBar.setStyleSheet("QFrame{\n"
"    \n"
"    border-radius: 150px;\n"
"    \n"
"    background-color: qconicalgradient(cx:0.5, cy:0.5, angle:90, stop:0.749 rgba(0, 0, 0, 0), stop:0.750 rgba(150, 0, 20, 255));\n"
"\n"
"\n"
"}")
        self.circularProgressBar.setFrameShape(QtWidgets.QFrame.NoFrame)
        self.circularProgressBar.setFrameShadow(QtWidgets.QFrame.Raised)
        self.circularProgressBar.setObjectName("circularProgressBar")
        self.container = QtWidgets.QFrame(self.circularProgressBar)
        self.container.setGeometry(QtCore.QRect(20, 20, 260, 260))
        self.container.setStyleSheet("QFrame{\n"
"\n"
"    border-radius: 130px;\n"
"    background-color: rgb(54, 55, 56);\n"
"\n"
"}\n"
"")
        self.container.setFrameShape(QtWidgets.QFrame.StyledPanel)
        self.container.setFrameShadow(QtWidgets.QFrame.Raised)
        self.container.setObjectName("container")
        self.Percent_Label = QtWidgets.QLabel(self.container)
        self.Percent_Label.setGeometry(QtCore.QRect(70, 70, 131, 101))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(60)
        font.setBold(False)
        font.setItalic(False)
        font.setUnderline(False)
        font.setWeight(50)
        self.Percent_Label.setFont(font)
        self.Percent_Label.setStyleSheet("background-color: none;\n"
"color: #FFFFFF")
        self.Percent_Label.setAlignment(QtCore.Qt.AlignCenter)
        self.Percent_Label.setObjectName("Percent_Label")
        self.circularBG = QtWidgets.QFrame(self.ProgressBarFrame)
        self.circularBG.setGeometry(QtCore.QRect(10, 10, 300, 300))
        self.circularBG.setStyleSheet("QFrame{\n"
"    border-radius: 150px;\n"
"    background-color: rgba(255,255, 255, 110);\n"
"}")
        self.circularBG.setFrameShape(QtWidgets.QFrame.NoFrame)
        self.circularBG.setFrameShadow(QtWidgets.QFrame.Raised)
        self.circularBG.setObjectName("circularBG")
        self.circularBG.raise_()
        self.circularProgressBar.raise_()
        self.launchButton = QtWidgets.QPushButton(self.appFrame)
        self.launchButton.setGeometry(QtCore.QRect(720, 630, 201, 91))
        font = QtGui.QFont()
        font.setFamily("Impact")
        font.setPointSize(20)
        self.launchButton.setFont(font)
        self.launchButton.setStyleSheet("color: rgb(207, 28, 22);\n"
"border-color: rgb(255, 0, 0);")
        self.launchButton.setObjectName("launchButton")
        ui.launchButton.clicked.connect(ui.readText) # Calls the readText function on press of the launch button

        self.retranslateUi(Dialog)
        QtCore.QMetaObject.connectSlotsByName(Dialog)

    def retranslateUi(self, Dialog):
        _translate = QtCore.QCoreApplication.translate
        Dialog.setWindowTitle(_translate("Dialog", "Rocket Team Simulator"))
        self.mrtLabel.setText(_translate("Dialog", "McGill Rocket Team Simulator"))
        self.apogeeLabel.setText(_translate("Dialog", "Expected Apogee"))
        self.massLabel.setText(_translate("Dialog", "Rocket Mass"))
        self.speedRailLabel.setText(_translate("Dialog", "Speed Off Rail"))
        self.rocketCSALabel.setText(_translate("Dialog", "Rocket CSA"))
        self.mainAltLabel.setText(_translate("Dialog", "Main Deploy Altitude"))
        self.mainParaCSALabel.setText(_translate("Dialog", "Main Parachute CSA"))
        self.mainVCDLabel.setText(_translate("Dialog", "Main Vertical Cd"))
        self.mainTCDLabel.setText(_translate("Dialog", "Main Transverse Cd"))
        self.longitudeLabel.setText(_translate("Dialog", "Launch Longitude"))
        self.windDataLabel.setText(_translate("Dialog", "Wind Data"))
        self.drogueParaCSALabel.setText(_translate("Dialog", "Drogue Parachute CSA"))
        self.drogueAltLabel.setText(_translate("Dialog", "Drogue Deploy Altitude"))
        self.drogueVCDLabel.setText(_translate("Dialog", "Drogue Vertical Cd"))
        self.drogueTCDLabel.setText(_translate("Dialog", "Drogue Transverse Cd"))
        self.zenithLabel.setText(_translate("Dialog", "Zenith Angle"))
        self.azimuthLabel.setText(_translate("Dialog", "Azimuth Angle"))
        self.latitudeLabel.setText(_translate("Dialog", "Launch Latitude"))
        self.simulationLabel.setText(_translate("Dialog", "Number of Simulations"))
        self.Percent_Label.setText(_translate("Dialog", "<p><span style=\" font-size:68pt;\">0</span><span style=\" font-size:48pt; vertical-align:super;\">%</span></p>"))
        self.launchButton.setText(_translate("Dialog", "Launch"))


    # A function that assigns each input of the GUI to a variable. Is called on when launch button is pressed
    def readText(self):
        error=0
        try:
                drogueDeployAltitude = float(self.drogueAltInput.text())
        except:
                error=1
                sendErrorMessage("Drogue Deploy Altitude")
        try:
                fileStr = str(self.windDataInput.text())
                windDataFile = "././sample_data/wind_data/"+fileStr
                fl = open(windDataFile, "r")
        except:
                error=1
                print("Incorrect Wind Data file. Make sure the file is in /sample_data/wind_data/ and is of format txt. Please include '.txt'.")
        try:
                rocketMass = float(self.massInput.text())
        except:
                error=1
                sendErrorMessage("Rocket Mass")
        try:
                speedOffRail = float(self.speedInput.text())
        except:
                error=1
                sendErrorMessage("Speed Off Rail")
        try:
                expectedApogee = float(self.apogeeInput.text())
        except:
                error=1
                sendErrorMessage("Expected Apogee")
        try:
                mainDeployAltitude = float(self.mainAltInput.text())
        except:
                error=1
                sendErrorMessage("Main Deploy Altitude")
        try:
                mainParachuteCSA = float(self.mainParaCSAInput.text())
        except:
                error=1
                sendErrorMessage("Main Parachute CSA")
        try:
                drogueVerticalCD = float(self.drogueVCDInput.text())
        except:
                error = 1
                sendErrorMessage("Drogue Vertical CD")
        try:
                mainVerticalCD = float(self.mainVCDInput.text())
        except:
                error=1
                sendErrorMessage("Main Vertical CD")
        try:
                mainTransverseCD = float(self.mainTCDInput.text())
        except:
                error=1
                sendErrorMessage("Main Transverse CD")
        try:
                launchLatitude = float(self.latitudeInput.text())
        except:
                error=1
                sendErrorMessage("Launch Latitude")
        try:
                drogueTransverseCD = float(self.drogueTCDInput.text())
        except:
                error=1
                sendErrorMessage("Drogue Transverse CD")
        try:
                rocketCSA = float(self.rocketCSAInput.text())
        except:
                error=1
                sendErrorMessage("Rocket CSA Input")
        try:
                drogueParachuteCSA = float(self.drogueParaCSAInput.text())
        except:
                error=1
                sendErrorMessage("Drogue Parachute CSA")
        try:
                zenithAngle = float(self.zenithInput.text())
        except:
                error=1
                sendErrorMessage("Zenith Angle")
        try:
                azimuthAngle = float(self.azimuthInput.text())
        except:
                error=1
                sendErrorMessage("Azimuth Angle")
        try:
                launchLongitude = float(self.longitudeInput.text())
        except:
                error=1
                sendErrorMessage("Launch Longitude")
        try:
                num_launches = int(self.simulationInput_7.text())
        except:
                error=1
                print("Error: Number of Launches must be an integer!")
        if (error==0):
                main(num_launches, mainDeployAltitude, expectedApogee, speedOffRail, rocketCSA,
                drogueParachuteCSA, mainParachuteCSA, drogueVerticalCD,
                mainVerticalCD, drogueDeployAltitude,
                drogueTransverseCD, mainTransverseCD,
                rocketMass, windDataFile)

"""def checkNoNum(var):
        if isinstance(var,int):
                return True
        elif isinstance(var,float):
                return True
        else:
                return False"""

def sendErrorMessage(var):
        print("Error: " + var + " must be a number!")


if __name__ == "__main__":
    import sys
    app = QtWidgets.QApplication(sys.argv)
    Dialog = QtWidgets.QDialog()
    ui = Ui_Dialog()
    ui.setupUi(Dialog)
    Dialog.show()
    sys.exit(app.exec_())