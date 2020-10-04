import socket
import threading
import os

#
# author:  Shane Stacy, Andrew Weston, Freeman Ogburn
# description: this python script is a multithreaded centralized database server to assist in P2P file-sharing
#


class centralized_server(object):

    # we must define the properties for each socket so the server can be multithreaded
    def __init__(self, host, port):
        self.host = host
        self.port = port
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.sock.bind((self.host, self.port))
        self.userDictionary = {}
        self.userFileDictionary = {}
        self.fileDictionary = {}

    # the server must listen for multiple incoming connection requests so the server can be multithreaded
    def listen(self):
        self.sock.listen(5)
        while True:
            client, address = self.sock.accept()
            print("New connection from: " + str(address))
            #client.settimeout(60)
            threading.Thread(target = self.listenToClient,args = (client,address)).start()

    # the server will accept client commands and carry them out
    def listenToClient(self, client, address):

        # this function sends an acknowledgment to let the client know the server is ready
        def acknowledgement(conn):
            # send acknowledgement to the client
            conn.send("acknowledged".encode())

        # this function streamlines translating lists to network compatible strings
        def listToString(s):
            # initialize an empty string
            str1 = ""
            # traverse in the string
            for element in s:
                str1 += element + ";"
                # return string
            return str1


        # this is the loop the server gets trapped in while waiting for client commands
        while True:
            try:
                # receive data stream of command, ip#, port, username, hostname, and speed
                print("Waiting for command from client...")
                print()
                clientData = client.recv(1024).decode()
                clientTokens = clientData.split(";")
                print("Data received:  " + clientData)
                print("Parsing request from client.")

                # if the command is connect
                if ('connect' in clientTokens[0]):
                    print("Command connect.")
                    # use a python dictionary of IP addresses mapped to list of client data (ip#, port, username, hostname, and speed)
                    user = clientTokens[3]
                    self.userDictionary[user] = clientTokens
                    # acknowledge the client's data
                    acknowledgement(client)
                    # receive data stream of IP, file name, and file description
                    fileData = client.recv(1024).decode()
                    print("Files:  " + fileData)
                    # load the multiplexed tokens into a list with semicolon as delimiter
                    fileTokens = fileData.split(';')
                    # pop off the last element because it's empty
                    fileTokens.pop()
                    # length is used to calculate the number of pairs of file and file descriptions
                    length = len(fileTokens) // 2
                    # initialize a list to hold the files
                    fileList = []
                    # initialize a list to hold the metadata
                    fileMeta = []
                    # initialize a dictionary to hold file and file description associations
                    metaDictionary = {}
                    # demultiplex the fileTokens sent by the client and add them to lists/dictionaries
                    for x in range(0, length):
                        fileList.append(fileTokens[2 * x])
                        fileMeta.append(fileTokens[(2 * x) + 1])
                        metaDictionary[fileTokens[2 * x]] = fileTokens[(2 * x) + 1]
                    # map IP address to list of files
                    self.userFileDictionary[user] = metaDictionary
                    print("File directory received.  Database updated.")
                elif ('search' in clientTokens[0]):
                    # if the command is search
                    print("Command search.")
                    # send the acknowledgement
                    acknowledgement(client)
                    # FIXME create a command and loop for repeated keyword searches
                    # wait for the client to send its search string
                    searchString = client.recv(1024).decode()
                    # load that string into a list with semicolon as delimiter
                    searchTokens = searchString.split(";")
                    print("Search Tokens:  " + searchString)
                    # initialize list to hold the searchResponse
                    searchResponse = []
                    print(self.userFileDictionary.values())
                    # iterate over the list of search terms sent by client
                    for searchWord in searchTokens:
                        print("Current search Word:  " + searchWord)
                        for theUser, dictionaries in self.userFileDictionary.items():
                            for file, meta in dictionaries.items():
                                print("File:  " + file)
                                print("Description:  " + meta)
                                # check if each search word is in the file list or file description field
                                if (searchWord in file or searchWord in meta):
                                    print("Match found:  " + file)
                                    # if the file record IP, file name, speed, and file description are not already in the list of matching files (searchResponse) then add them
                                    if (file not in searchResponse and theUser not in searchResponse):
                                        print("Adding:  " + file)
                                        # search response is a multiplexed list of files, metadata, ip, and speed
                                        searchResponse.append(file)
                                        searchResponse.append(meta)
                                        searchResponse.append((self.userDictionary.get(theUser))[3] + ":" + (self.userDictionary.get(theUser))[1])
                                        searchResponse.append((self.userDictionary.get(theUser))[5])
                    # initialize the data string response
                    data = ""
                    print("Current Search Response:  " + str(searchResponse))
                    # convert the searchResponse to a network compatible string
                    data = listToString(searchResponse)
                    # if no match is found
                    if (data == ""):
                        data = "NothingAtAll"
                        print("This means the server couldn't find a match.")
                        print("This could be due to broken code or one simply wasn't found.")
                    print("Sending search results...")
                    print("Search results:  " + data)
                    # send the data back to the client for display
                    client.send(data.encode())
                    print("Search results sent!")
                else:
                    print("The request could not be handled.  Closing socket.")
                    print("Deregistering user " + user + " at " + str(address))
                    self.userFileDictionary.pop(user)
                    client.close()
                    return False
            except:
                print("CRITICAL ERROR:  Unhandled Exception!")
                print("Connection closed from: " + str(address))
                client.close()
                return False

# this is the main execution
if __name__ == "__main__":
    while True:
        print("Centralized Server for CIS457 Project 2 V1.0")
        port_num = input("What port should the server run on? -> ")
        try:
            port_num = int(port_num)
            break
        except ValueError:
            pass

    print("The server is now running on port %d." % port_num)
    centralized_server('', port_num).listen()