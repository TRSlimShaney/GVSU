import socket
import threading
import os

#
# author:  Shane Stacy, Andrew Weston, Freeman Ogburn
# description: this python script is a multithreaded ftp server
#


class ftp_server(object):

    # we must define the properties for each socket so the server can be multithreaded
    def __init__(self, host, port):
        self.host = host
        self.port = port
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.sock.bind((self.host, self.port))

    # the server must listen for multiple incoming connection requests so the server can be multithreaded
    def listen(self):
        self.sock.listen(5)
        while True:
            client, address = self.sock.accept()
            print("New connection from: " + str(address))
            client.settimeout(60)
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
            print("Waiting for file name...")
            filename = conn.recv(1024).decode()
            # open the file stream
            file = open(filename, "rb")
            # send the file
            print("Sending file...")
            conn.sendall(file.read())
            print("File sent!")

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
                print("File received! Saved as " + filename + ".")

        # this is the loop the server gets trapped in while waiting for client commands
        while True:
            try:
                # receive data stream with packets up to 1024 bytes
                command = client.recv(1024).decode()
                success = "The operation completed successfully!"
                if not command:
                    # if data is not received break
                    break
                print("Command received: (" + str(command) + ") from address " + str(address))

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
                print("Connection closed from: " + str(address))
                client.close()
                return False

# this is the main execution
if __name__ == "__main__":
    while True:
        port_num = input("What port should the server run on? -> ")
        try:
            port_num = int(port_num)
            break
        except ValueError:
            pass

    print("The server is now running on port %d." % port_num)
    ftp_server('',port_num).listen()
