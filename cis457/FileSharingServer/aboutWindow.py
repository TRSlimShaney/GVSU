# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'About.ui'
#
# Created by: PyQt5 UI code generator 5.10.1
#
# WARNING! All changes made in this file will be lost!

from PyQt5 import QtCore, QtGui, QtWidgets

class Ui_About(object):
    def setupUi(self, About):
        About.setObjectName("About")
        About.resize(317, 209)
        self.aboutOk = QtWidgets.QPushButton(About)
        self.aboutOk.setGeometry(QtCore.QRect(220, 160, 87, 29))
        self.aboutOk.setObjectName("aboutOk")
        self.label = QtWidgets.QLabel(About)
        self.label.setGeometry(QtCore.QRect(20, 0, 71, 71))
        font = QtGui.QFont()
        font.setPointSize(24)
        font.setItalic(True)
        self.label.setFont(font)
        self.label.setObjectName("label")
        self.label_2 = QtWidgets.QLabel(About)
        self.label_2.setGeometry(QtCore.QRect(20, 70, 281, 20))
        self.label_2.setObjectName("label_2")
        self.label_3 = QtWidgets.QLabel(About)
        self.label_3.setGeometry(QtCore.QRect(20, 90, 101, 17))
        self.label_3.setObjectName("label_3")
        self.label_4 = QtWidgets.QLabel(About)
        self.label_4.setGeometry(QtCore.QRect(20, 130, 251, 17))
        self.label_4.setObjectName("label_4")
        self.label_5 = QtWidgets.QLabel(About)
        self.label_5.setGeometry(QtCore.QRect(20, 150, 231, 17))
        self.label_5.setObjectName("label_5")

        self.retranslateUi(About)
        QtCore.QMetaObject.connectSlotsByName(About)

    def retranslateUi(self, About):
        _translate = QtCore.QCoreApplication.translate
        About.setWindowTitle(_translate("About", "About"))
        self.aboutOk.setText(_translate("About", "Ok"))
        self.label.setText(_translate("About", "Host"))
        self.label_2.setText(_translate("About", "Freeman Ogburn, Andrew Weston, Shane Stacy"))
        self.label_3.setText(_translate("About", "CIS457, Fall 2019"))
        self.label_4.setText(_translate("About", "This project was developed in Python 3.6."))
        self.label_5.setText(_translate("About", "GUI produced with Qt5 Designer."))
