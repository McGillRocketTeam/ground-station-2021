# -*- coding: utf-8 -*-

################################################################################
## Form generated from reading UI file 'ProgressCircleicQhoV.ui'
##
## Created by: Qt User Interface Compiler version 5.14.1
##
## WARNING! All changes made in this file will be lost when recompiling UI file!
################################################################################

from PySide2.QtCore import (QCoreApplication, QMetaObject, QObject, QPoint,
    QRect, QSize, QUrl, Qt)
from PySide2.QtGui import (QBrush, QColor, QConicalGradient, QCursor, QFont,
    QFontDatabase, QIcon, QLinearGradient, QPalette, QPainter, QPixmap,
    QRadialGradient)
from PySide2.QtWidgets import *


class Ui_LoadingScreen(object):
    def setupUi(self, LoadingScreen):
        if LoadingScreen.objectName():
            LoadingScreen.setObjectName(u"LoadingScreen")
        LoadingScreen.resize(340, 340)
        self.centralwidget = QWidget(LoadingScreen)
        self.centralwidget.setObjectName(u"centralwidget")
        self.ProgressBarFrame = QFrame(self.centralwidget)
        self.ProgressBarFrame.setObjectName(u"ProgressBarFrame")
        self.ProgressBarFrame.setEnabled(True)
        self.ProgressBarFrame.setGeometry(QRect(10, 10, 320, 320))
        self.ProgressBarFrame.setFrameShape(QFrame.NoFrame)
        self.ProgressBarFrame.setFrameShadow(QFrame.Raised)
        self.circularProgressBar = QFrame(self.ProgressBarFrame)
        self.circularProgressBar.setObjectName(u"circularProgressBar")
        self.circularProgressBar.setEnabled(True)
        self.circularProgressBar.setGeometry(QRect(10, 10, 300, 300))
        self.circularProgressBar.setStyleSheet(u"QFrame{\n"
"	\n"
"	border-radius: 150px;\n"
"	\n"
"	background-color: qconicalgradient(cx:0.5, cy:0.5, angle:90, stop:0.749 rgba(0, 0, 0, 0), stop:0.750 rgba(140, 0, 20, 255));\n"
"\n"
"\n"
"}")
        self.circularProgressBar.setFrameShape(QFrame.NoFrame)
        self.circularProgressBar.setFrameShadow(QFrame.Raised)
        self.circularBG = QFrame(self.ProgressBarFrame)
        self.circularBG.setObjectName(u"circularBG")
        self.circularBG.setEnabled(True)
        self.circularBG.setGeometry(QRect(10, 10, 300, 300))
        self.circularBG.setStyleSheet(u"QFrame{\n"
"	border-radius: 150px;\n"
"	background-color: rgba(255, 255, 255, 100);\n"
"\n"
"\n"
"\n"
"\n"
"}")
        self.circularBG.setFrameShape(QFrame.NoFrame)
        self.circularBG.setFrameShadow(QFrame.Raised)
        self.container = QFrame(self.ProgressBarFrame)
        self.container.setObjectName(u"container")
        self.container.setEnabled(True)
        self.container.setGeometry(QRect(30, 30, 260, 260))
        self.container.setStyleSheet(u"QFrame{\n"
"\n"
"	border-radius: 130px;\n"
"	background-color: rgb(54, 55, 56);\n"
"\n"
"}\n"
"")
        self.container.setFrameShape(QFrame.StyledPanel)
        self.container.setFrameShadow(QFrame.Raised)
        self.Percent_Label = QLabel(self.container)
        self.Percent_Label.setObjectName(u"Percent_Label")
        self.Percent_Label.setEnabled(True)
        self.Percent_Label.setGeometry(QRect(10, 80, 261, 101))
        font = QFont()
        font.setFamily(u"Impact")
        font.setPointSize(60)
        font.setBold(False)
        font.setItalic(False)
        font.setUnderline(False)
        font.setWeight(50)
        self.Percent_Label.setFont(font)
        self.Percent_Label.setStyleSheet(u"background-color: none;\n"
"color: #FFFFFF")
        self.Percent_Label.setAlignment(Qt.AlignCenter)
        self.circularBG.raise_()
        self.circularProgressBar.raise_()
        self.container.raise_()
        LoadingScreen.setCentralWidget(self.centralwidget)

        self.retranslateUi(LoadingScreen)

        QMetaObject.connectSlotsByName(LoadingScreen)
    # setupUi

    def retranslateUi(self, LoadingScreen):
        LoadingScreen.setWindowTitle(QCoreApplication.translate("LoadingScreen", u"MainWindow", None))
        self.Percent_Label.setText(QCoreApplication.translate("LoadingScreen", u"<p><span style=\" font-size:45pt;\">0</span><span style=\" font-size:40pt; vertical-align:super;\">%</span></p>", None))
    # retranslateUi

