from pyftpdlib import servers
from pyftpdlib.handlers import TLS_FTPHandler
from pyftpdlib.authorizers import DummyAuthorizer



# this class is a TLS FTP server
class ftpTLS_server():
    def __init__(self, address, port):
        # define the host
        host = (address, port)
        # make an authorizer to handle anonymous users
        authorizer = DummyAuthorizer()
        authorizer.add_anonymous('.', perm = 'elradfmwM')
        # make a handler to handle the additional TLS protocols
        handler = TLS_FTPHandler
        # define the certificate
        handler.certfile = 'keycert.pem'
        # set the authorizer to the handler
        handler.authorizer = authorizer
        server = servers.FTPServer(host, handler) # the server will run on the host
        print("Serve forever.")
        server.serve_forever() # serve all requests

# this is the main driver for the server
def main():
    # start the server on localhost, port 2121
    ftps = ftpTLS_server('0.0.0.0', 2121)


# if the script is run directly, run the main function
if __name__=='__main__':
    print("TLS FTP Server for CIS457 V1.0")
    print("Starting TLS FTP Server on local machine...")
    main()