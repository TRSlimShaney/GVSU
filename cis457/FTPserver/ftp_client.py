import socket


#
# author:  Shane Stacy, Andrew Weston, Freeman Ogburn
# description: this python script is an ftp client
#


def listcommands():
    print('Here are the commands:\n')
    print('connect')
    print('list')
    print('retrieve')
    print('store')
    print('quit')


def ftp_client():
    # this is a connected flag to keep track of client state
    connected = 0
    # reserve this variable
    command = ''

    # while the command isn't 'quit'
    while (command != 'quit'):
        print("///////////////////////FTP Client for CIS457/////////////////////")
        if (connected == 0):
            print ("You are NOT connected right now. Only connect command is available!")
        elif (connected == 1):
            print ("You ARE connected right now. All commands available!")
        # list the commands
        listcommands()
        # get the command and arguments as a list
        input_list = input('Command? -> ').split()
        # get the command from the list
        command = input_list[0]

        # if the command is quit, terminate
        if (command == 'quit'):
            break
        elif (command == 'connect' and connected == 0):
            # if the command is connect and the client isn't connected
            # the first argument is the ip
            ip = input_list[1]
            # the second argument is the port
            port = int(input_list[2])

            # get host name by ip
            host = socket.gethostbyaddr(ip)

            # make the socket
            the_socket = socket.socket()
            # connect to the server
            the_socket.connect((host[0], port))
            print('You should now be connected.\n')
            connected = 1
        elif (command == 'connect' and connected == 1):
            # if the command is connect and the client is connected
            print("You are already connected!\n")
        elif (connected == 0):
            # if not connected, don't allow the other following commands
            print("This command cannot be performed because you are not connected yet!\n")
        elif (command == 'list' and connected == 1):
            # if the command is list, and the client is connected
            # send the command
            the_socket.send(command.encode())
            # receive the directory reading
            list = the_socket.recv(1024).decode()
            print(list)
            print("")
        elif (command == 'retrieve' and connected == 1):
            # if the command is retrieve and the client is connected
            # send the command
            the_socket.send(command.encode())
            # wait for the server to be ready
            the_socket.recv(1024).decode()
            # take argument1 as the file name
            filename = input_list[1]
            # send the file name
            print("Sending file name...")
            the_socket.send(filename.encode())
            # receive the file
            chunk = the_socket.recv(4096)
            with open(filename, 'wb+') as f:
                f.write(chunk)
                print("File received! Saved as " + filename + ".")
        elif (command == 'store' and connected == 1):
            # if the command is store and the client is connected
            # send the command
            the_socket.send(command.encode())
            # wait for the server to be ready
            the_socket.recv(1024).decode()
            # take argument1 as the file name
            filename = input_list[1]
            # open the file stream
            file = open(filename, "rb")
            # send the file name
            the_socket.send(filename.encode())
            # send the file
            print("Sending file...")
            the_socket.sendall(file.read())
            print("File sent!")
        else:
            ("Unknown Command!\n")

    the_socket.close()  # close the connection

if __name__ == '__main__':
    ftp_client()
