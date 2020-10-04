from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtWidgets import QApplication
import os, sys
import mainWindow, aboutWindow
from ftpTLS_client import ftpTLS_client


# this is the main window
class Driver(QtWidgets.QMainWindow, mainWindow.Ui_mainWindow):
    def __init__(self, parent=None):
        super(Driver, self).__init__(parent)
        self.setupUi(self)
        self.connected = 0;
        # if the actionExit tab is clicked, handle it
        self.actionExit.triggered.connect(self.handleExit)
        # if the actionAbout tab is clicked, handle it
        self.actionAbout.triggered.connect(self.handleAbout)
        # if the connectButton is clicked, handle it
        self.connectButton.clicked.connect(self.handleConnect)
        # if the disconnectButton is clicked, handle it
        self.disconnectButton.clicked.connect(self.handleDisconnect)
        # if the refreshButton is clicked, handle it
        self.refreshButton.clicked.connect(self.handleRefresh)
        # if the storeButton is clicked, handle it
        self.storeButton.clicked.connect(self.handleStore)
        # if the retrieveButton is clicked, handle it
        self.retrieveButton.clicked.connect(self.handleRetrieve)
        print("////////////////////SecureFile Encrypted FTP Client for CIS457//////////////////////")
        print("This client is designed to be used with a GUI.  This terminal output is for")
        print("diagnostic purposes only.")
        self.debug("INFO:  SecureFile V1.0 for CIS457 is ready!")


    # handle if the About tab is clicked
    def handleAbout(self):
        # make the dialog
        self.dialog = AboutDialog()
        # show the dialog
        self.dialog.show()

    # handle if the Exit tab is clicked
    def handleExit(self):
        self.debug("INFO:  Exiting...")
        # exit the program
        exit()

    # handle if the connectButton is clicked
    def handleConnect(self):
        host = self.hostnameLine.text()
        user = self.usernameLine.text()
        password = self.passswordLine.text()
        self.ftps = ftpTLS_client(host, 2121)
        self.connected = 1
        self.debug("INFO:  FTP Connection to " + host + " successful!")
        # refresh the directory lists
        self.handleRefresh()

    # handle if the disconnectButton is clicked
    def handleDisconnect(self):
        # check if connected first
        if (self.connected == 1):
            self.debug("INFO:  Disconnecting...")
            # then close the connection
            self.ftps.close()
            self.debug("INFO:  Disconnected!")
            # set the connected flag to zero
            self.connected = 0
            # clear the GUI local list
            self.localDirList.clear()
            # clear the GUI server list
            self.serverDirList.clear()
        else:
            self.debug("ERROR:  Cannot disconnect if not connected!")

    # handle if the refreshButton is clicked
    def handleRefresh(self):
        # get a local directory reading into a list
        clientList = os.listdir()
        # clear the GUI list first
        self.localDirList.clear()
        # add the local directory list to the GUI list
        self.localDirList.addItems(clientList)
        self.debug("INFO:  Local Directory refreshed!")
        # check if connected first
        if (self.connected == 1):
            # get the list of files from the server
            serverList = self.ftps.list()
            # clear the GUI list first
            self.serverDirList.clear()
            # add them to the GUI list
            self.serverDirList.addItems(serverList)
            self.debug("INFO:  Server Directory refreshed!")
        else:
            self.debug(("INFO:  No Server Directory to refresh."))

    # handle if the storeButton is clicked
    def handleStore(self):
        # check if connected first
        if (self.connected == 1):
            # get the filename from the GUI local directory list
            try:
                # get the filename from the currently selected item in the GUI
                filename = self.localDirList.currentItem().text()
            except:
                self.debug("INFO:  Select a single file to store!")
                return
            # then store the file
            self.ftps.store(filename)
            self.debug("INFO:  " + filename + " stored!")
            # refresh the directory after storing
            self.handleRefresh()
        else:
            self.debug("ERROR:  Cannot store if not connected!")

    # handle if the retrieveButton is clicked
    def handleRetrieve(self):
        # check if connected first
        if (self.connected == 1):
            try:
                # get the filename from the currently selected item in the GUI
                filename = self.serverDirList.currentItem().text()
            except:
                self.debug("INFO:  Select a single file to retrieve!")
                return
            # then retrieve the file
            self.ftps.retrieve(filename)
            self.debug("INFO:  " + filename + " retrieved!")
            # refresh the directory after receiving
            self.handleRefresh()
        else:
            self.debug("ERROR:  Cannot retrieve if not connected!")

    # this function just streamlines printing something to both the outputList and the terminal
    def debug(self, string):
        self.outputList.addItem(string)
        print(string)



# this is the AboutDialog class
class AboutDialog(QtWidgets.QDialog, aboutWindow.Ui_About):
    def __init__(self, parent=None):
        super(AboutDialog, self).__init__(parent)
        self.setupUi(self)
        # if the aboutOk button is clicked, handle it
        self.aboutOk.clicked.connect(self.handleAboutOk)

    # handle the aboutOk button
    def handleAboutOk(self):
        # close the about dialog
        self.close()




# this is the main driver for the GUI and client
def main():
    # make the app
    app = QApplication(sys.argv)
    # make the main window form
    form = Driver()
    # show the main window
    form.show()
    # exit the program after it's done (closes or runs to end)
    exit(app.exec())


# if the script is run directly, run the main function
if __name__=='__main__':
    main()