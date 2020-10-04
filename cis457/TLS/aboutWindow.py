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
        About.resize(362, 268)
        self.label = QtWidgets.QLabel(About)
        self.label.setGeometry(QtCore.QRect(20, 10, 151, 71))
        font = QtGui.QFont()
        font.setPointSize(24)
        font.setBold(False)
        font.setItalic(True)
        font.setWeight(50)
        self.label.setFont(font)
        self.label.setObjectName("label")
        self.label_2 = QtWidgets.QLabel(About)
        self.label_2.setGeometry(QtCore.QRect(20, 100, 281, 17))
        self.label_2.setObjectName("label_2")
        self.label_3 = QtWidgets.QLabel(About)
        self.label_3.setGeometry(QtCore.QRect(20, 120, 271, 17))
        self.label_3.setObjectName("label_3")
        self.label_4 = QtWidgets.QLabel(About)
        self.label_4.setGeometry(QtCore.QRect(20, 170, 241, 17))
        self.label_4.setObjectName("label_4")
        self.label_5 = QtWidgets.QLabel(About)
        self.label_5.setGeometry(QtCore.QRect(20, 190, 191, 17))
        self.label_5.setObjectName("label_5")
        self.aboutOk = QtWidgets.QPushButton(About)
        self.aboutOk.setGeometry(QtCore.QRect(230, 210, 111, 41))
        self.aboutOk.setObjectName("aboutOk")
        self.label_6 = QtWidgets.QLabel(About)
        self.label_6.setGeometry(QtCore.QRect(30, 60, 91, 17))
        self.label_6.setObjectName("label_6")

        self.retranslateUi(About)
        QtCore.QMetaObject.connectSlotsByName(About)

    def retranslateUi(self, About):
        _translate = QtCore.QCoreApplication.translate
        About.setWindowTitle(_translate("About", "About"))
        self.label.setText(_translate("About", "SecureFile"))
        self.label_2.setText(_translate("About", "Freeman Ogburn, Shane Stacy, Andrew Weston"))
        self.label_3.setText(_translate("About", "CIS457, Fall 2019"))
        self.label_4.setText(_translate("About", "This project was devloped in Python 3.6."))
        self.label_5.setText(_translate("About", "GUI produced with Qt5 Designer."))
        self.aboutOk.setText(_translate("About", "Ok"))
        self.label_6.setText(_translate("About", "TLS FTP Client"))
