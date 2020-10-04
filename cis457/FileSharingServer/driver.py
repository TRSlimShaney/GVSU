from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtWidgets import QApplication
import sys
import threading
import mainWindow, aboutWindow
from client import p2p_server
from client import p2p_client
from client import centralized_client


# this is the main window
class Driver(QtWidgets.QMainWindow, mainWindow.Ui_MainWindow):
    def __init__(self, parent=None):
        super(Driver, self).__init__(parent)
        self.setupUi(self)
        self.centralConnected = 0
        self.p2pConnected = 0
        # if the actionExit tab is clicked, handle it
        self.actionExit.triggered.connect(self.handleExit)
        # if the actionAbout tab is clicked, handle it
        self.actionAbout.triggered.connect(self.handleAbout)
        # if the connectButton is clicked, handle it
        self.connectButton.clicked.connect(self.handleConnect)
        # if the searchButton is clicked, handle it
        self.searchButton.clicked.connect(self.handleSearch)
        # if the Make It So Button is clicked, handle it
        self.goButton.clicked.connect(self.handleMakeItSo)
        # initialize the server on its own thread.  Daemon because we want this thread to stop with the main execution
        print("/////////////////////////P2P Client for CIS457////////////////////////////")
        print("This P2P client is designed to be used with a GUI.  This terminal output")
        print("is used for diagnostic purposes only.")
        self.debug("INFO:  P2P Client for CIS457 V1.0 is ready!")
        self.debug("INFO:  P2P Server thread starting...")
        # start the server thread.  Daemon thread means when the main execution ends so does this thread.
        threading.Thread(target=p2p_server, args=('', 5000), daemon=True).start()
        self.debug("INFO:  P2P Server thread started.")

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

    # handle if the Connect button is clicked
    def handleConnect(self):
        # get the server line text field
        server = self.serverLine.text()
        # get the port line text field
        port = self.portLine.text()
        # get the username line text field
        username = self.usernameLine.text()
        # get the hostname line text field
        hostname = self.hostnameLine.text()
        # get the speed line text field
        speed = self.speedDropdown.currentText()
        self.debug("INFO:  Connecting to centralized server...")
        # start the client and connect to the server
        self.myClient = centralized_client(server, port, username, hostname, speed)
        self.centralConnected = 1
        self.debug("INFO:  Connected to centralized server!")

    # handle if the Search button is clicked
    def handleSearch(self):
        if (self.centralConnected == 1):
            keywords = self.keywordLine.text()
            # gets a list back from the centralized server
            self.debug("INFO:  Sending out the search to the centralized server...")
            # call the client to do the search and get the list
            list = self.myClient.search(keywords)
            # pop the last element because it's empty
            if (len(list) > 1):
                list.pop()
            self.debug("INFO:  Search result received!")
            self.debug("INFO:  Results:  " + str(list))
            # if the nothing code is returned
            if ("NothingAtAll" in list[0]):
                self.debug("INFO:  Your query did not return any results.")
            row = 0
            self.debug("INFO:  Updating the table...")
            # update the table in the GUI
            # clear it first if it has entries in it
            for i in reversed(range(self.searchTable.rowCount())):
                self.searchTable.removeRow(i)
            # calculate how many rows there will be.  Get whole integer division
            length = len(list) // 4
            # for each row
            for i in range(0, length):
                # get the bottommost row
                rowPosition = self.searchTable.rowCount()
                # insert the initially blank row
                self.searchTable.insertRow(rowPosition)
                # set the row properties
                self.searchTable.setItem(rowPosition, 0, QtWidgets.QTableWidgetItem(list[i * 4]))
                for x in range(1, 4):
                    # set the column properties for the row
                    self.searchTable.setItem(rowPosition, x, QtWidgets.QTableWidgetItem(list[((i * 4) + x)]))
            # resize the columns so the data is all visible
            self.searchTable.resizeColumnsToContents()
            self.debug("INFO:  Table updated!")
        else:
            self.debug("ERROR:  Cannot search if not connected!")


    # handle if the Make It So button is clicked
    def handleMakeItSo(self):
        # get the command as a list with space as a delimiter
        command = (self.commandLine.text()).split()
        # clear the command line
        self.commandLine.clear()
        # if the command is connect
        if (command[0] == "connect"):
            # start the p2p client with ip and port as arguments
            self.p2pclient = p2p_client(command[1], command[2])
            self.debug("INFO:  P2P Client connected to " + command[1] + " P2P Server.")
            # set the p2p connected flag
            self.p2pConnected = 1
        elif (self.p2pConnected == 0):
            # if not p2p connected
            self.debug("ERROR:  Command not available if not connected!")
        elif (command[0] == "list"):
            # if the command is list, get a list of files from the p2p server and display them
            list = self.p2pclient.list()
            self.debug("INFO:  Here are the files on the P2P Server:  " + list)
        elif (command[0] == "store"):
            # this functionality is disabled as store functionality is not required
            #self.p2pclient.storeFile(command[1])
            self.debug("INFO:  Not implemented!")
        elif (command[0] == "retrieve"):
            # if the command is retrieve, call the p2p client to retrieve the file
            self.p2pclient.retrieveFile(command[1])
            self.debug("INFO:  File " + command[1] + " retrieved from P2P Server.")
        elif (command[0] == "quit"):
            # if the command is quit, close the p2p client
            self.p2pclient.close()
            self.debug("INFO:  Connection closed with P2P Server.")
        else:
            self.debug("ERROR:  Command not recognized.")

    # streamlines printing to both terminal and commandList
    def debug(self, string):
        self.commandList.addItem(string)
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