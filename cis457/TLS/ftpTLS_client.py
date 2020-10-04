import ftplib
import socket
from ftplib import FTP_TLS



# this class is a TLS FTP client
class ftpTLS_client():
    def __init__(self, ftpServer, port):
        #host = socket.gethostbyaddr(ftpServer)
        # connect to the ftp server at this address and port
        ftplib.FTP_TLS.port = 2121
        self.myFTPwithTLS = FTP_TLS(ftpServer)
        print("Connected")
        # login as anonymous user
        self.myFTPwithTLS.login()
        print("Logged in as anonymous")
        # set up a secure connection
        self.myFTPwithTLS.prot_p()
        print("Connection secured")

    # return a list of files in the server directory
    def list(self):
        return self.myFTPwithTLS.nlst()

    # return the size of a file in the server directory
    def size(self, filename):
        return self.myFTPwithTLS.size(filename)

    # close the connection
    def close(self):
        self.myFTPwithTLS.close()

    # retrieve a file securely
    def retrieve(self, filename):
        # open the file pointer
        fp = open(filename, "wb")
        # retrieve the file
        self.myFTPwithTLS.retrbinary('RETR ' + filename, fp.write)
        # close the file pointer
        fp.close()

    # store a file securely
    def store(self, filename):
        # open the file pointer
        fp = open(filename, "rb")
        # store the file
        self.myFTPwithTLS.storbinary('STOR ' + filename, fp)
        # close the file pointer
        fp.close()
