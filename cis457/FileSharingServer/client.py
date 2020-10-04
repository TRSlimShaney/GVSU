import select
import socket
import threading
import os

#
# author:  Shane Stacy, Andrew Weston, Freeman Ogburn
# description: this python script is a P2P client
#


class centralized_client(object):
    # this is a client that communicates to the centralized server
    def __init__(self, ip, port, username, hostname, speed):
        self.ip = ip
        self.port = int(port)
        self.connected = 0
        self.username = username
        self.hostname = hostname
        self.speed = speed
        # the first argument is the ip
        # the second argument is the port
        # get host name by ip
        self.host = socket.gethostbyaddr(ip)
        # make the socket
        self.the_socket = socket.socket()
        # connect to the server
        self.the_socket.connect((self.host[0], self.port))
        print('Centralized Client:     You should now be connected.\n')
        # make the data
        command = "connect"
        # assemble the data
        data = (command + ";" + self.ip + ";" + port + ";" + self.username + ";" + self.hostname + ";" + self.speed)
        # send the data
        self.the_socket.send(data.encode())
        # wait for the server to be ready
        self.the_socket.recv(1024).decode()
        # get a local directory reading into a list
        directoryList = os.listdir()
        # initialize the data stream
        data = ""
        length = len(directoryList)
        # read the descriptions text file
        file = open("descriptions.txt", "r")
        descriptions = file.readlines()
        # strip the newline characters off
        length2 = len(descriptions)
        for v in range(0, length2):
            descriptions[v] = descriptions[v].rstrip()
            print("here " + descriptions[v])
        print(descriptions)
        for i in range(0, length):
            print(directoryList[i])
            # first, find a suitable default file description
            if (".py" in directoryList[i]):
                # determine an appropriate file description based on file extension
                meta = "Python Source File"
            elif (".txt" in directoryList[i]):
                meta = "Text File"
            elif (".opus" in directoryList[i]):
                meta = "OPUS Audio File"
            elif (".m4a" in directoryList[i]):
                meta = "MPEG-4 AAC Audio File"
            elif (".docx" in directoryList[i]):
                meta = "Microsoft XML Word Document"
            elif (".pdf" in directoryList[i]):
                meta = "Portable Document File"
            else:
                meta = "Unknown File"
            # second, if there is a preset description, add it instead
            for desc2 in descriptions:
                if (directoryList[i] in desc2):
                    u = descriptions.index(desc2)
                    meta = descriptions[u + 1]
                    print("here2 " + meta)
            # multiplex the files and their respective metadata
            data = data + directoryList[i] + ";" + meta + ";"
        # send the data
        self.the_socket.send(data.encode())

    # this function communicates with the p2p server to do a search for a string of words
    def search(self, keywords):
        if ("audio" in keywords or "music" in keywords):
            keywords = keywords + ";.opus;.mp3;.m4a"
        elif ("documents" in keywords):
            keywords = keywords + ";.txt;.docx;.xclx;.pdf"
        # define the command
        command = "search"
        # send the command
        self.the_socket.send(command.encode())
        # wait for the server to be ready
        self.the_socket.recv(1024).decode()
        # send the keywords
        self.the_socket.send(keywords.encode())
        # wait for the results to be returned
        data = self.the_socket.recv(1024).decode()
        # split the results into a list with semicolon as delimiter
        data = data.split(";")
        # return the list to the driver
        return data

    def listToString(self, s):
        # initialize an empty string
        str1 = ""
        # traverse in the string
        for element in s:
            str1 += element + ";"
            # return string
        return str1


class p2p_server(object):

    # we must define the properties for each socket so the server can be multithreaded
    def __init__(self, host, port):
        self.host = host
        self.port = port
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.sock.bind((self.host, self.port))
        self.listen()

    # the server must listen for multiple incoming connection requests so the server can be multithreaded
    def listen(self):
        # the client can connect to five others
        self.sock.listen(5)
        while True:
            client, address = self.sock.accept()
            print("Server:     New connection from: " + str(address))
            #client.settimeout(60)
            threading.Thread(target = self.listenToClient,args = (client,address)).start()

    # the server will accept client commands and carry them out
    def listenToClient(self, client, address):

        def listToString(s):
            # initialize an empty string
            str1 = ""
            # traverse in the string
            for element in s:
                str1 += " " + element
                # return string
            return str1

        # this function must list all the files in a directory
        def list(conn):
            files = os.listdir()
            liststr = listToString(files)
            # send data to the client
            conn.send(liststr.encode())

        # this function must send a file back to a client
        def retrieve(conn):
            # tell the client to continue
            conn.send("continue".encode())
            # get the file name from the client
            print("Server:     Waiting for file name...")
            filename = conn.recv(1024).decode()
            # open the file stream
            file = open(filename, "rb")
            # send the file
            while True:
                print("Server:     Sending file...")
                chunk = file.read(4096)
                if not chunk:
                    #conn.send("done".encode())
                    break
                conn.send(chunk)
            print("Server:     Done sending.")
            file.close()

        # this function must get a file from the client
        def store(conn):
            # tell the client to continue
            conn.send("continue".encode())
            # get the file name from the client
            filename = conn.recv(1024).decode()
            # receive the file
            chunk = conn.recv(4096)
            with open(filename, 'wb+') as f:
                f.write(chunk)
                print("Server:     File received! Saved as " + filename + ".")

        # this is the loop the server gets trapped in while waiting for client commands
        while True:
            try:
                # receive data stream with packets up to 1024 bytes
                command = client.recv(1024).decode()
                success = "Server:     The operation completed successfully!"
                if not command:
                    # if data is not received break
                    break
                print("Server:     Command received: (" + str(command) + ") from address " + str(address))

                if (command == 'list'):
                    list(client)
                    print("%s" % success)
                elif (command == 'retrieve'):
                    retrieve(client)
                    print("%s" % success)
                elif (command == 'store'):
                    store(client)
                    print("%s" % success)
            except:
                print("Server:     Connection closed from: " + str(address))
                client.close()
                return False




class p2p_client(object):
    # define the properties for the client connection
    def __init__(self, ip, port):
        self.ip = ip
        self.port = int(port)
        self.connected = 0
        # the first argument is the ip
        # the second argument is the port
        # get host name by ip
        self.host = socket.gethostbyaddr(ip)
        # make the socket
        self.the_socket = socket.socket()
        # connect to the server
        self.the_socket.connect((self.host[0], self.port))
        print('Client:     You should now be connected.\n')


    def retrieveFile(self, filename):
        command = 'retrieve'
        # send the command
        self.the_socket.send(command.encode())
        # wait for the server to be ready
        self.the_socket.recv(1024).decode()
        # send the file name
        print("Client:     Sending file name...")
        self.the_socket.send(filename.encode())
        f = open(filename, 'wb+')
        # receive the file
        self.the_socket.settimeout(2)
        while True:
            try:
                print("Client:     Receiving file...")
                chunk = self.the_socket.recv(4096)
                f.write(chunk)
            except:
                f.close()
                print("Client:     Done receiving.")
                self.the_socket.settimeout(0)
                return



    def storeFile(self, filename):
        command = 'store'
        # send the command
        self.the_socket.send(command.encode())
        # wait for the server to be ready
        self.the_socket.recv(1024).decode()
        # open the file stream
        file = open(filename, "rb")
        # send the file name
        self.the_socket.send(filename.encode())
        # send the file
        print("Client:     Sending file...")
        self.the_socket.sendall(file.read())
        print("Client:     File sent!")


    def list(self):
        command = 'list'
        # send the command
        self.the_socket.send(command.encode())
        # receive the directory reading
        list = self.the_socket.recv(1024).decode()
        print(list)
        return list

    def close(self):
        self.the_socket.close()
        print("Client:     Connection closed.")

