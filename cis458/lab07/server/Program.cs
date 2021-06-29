using System;
using System.Threading;
using System.Collections.Generic;
using Framework;
using static Framework.Utilities;
using static Framework.Logger;

namespace server
{
    class Program
    {
        const string name = "Program";

        const string servername = "server";
        const string ip = "127.0.0.1";
        const int port = 25561;
        const LoggingLevels level = LoggingLevels.Everything;

        static void Main(string[] args)
        {
            GlobalLoggingLevel = level;
            const string routine = "Main";
            Entering(name, routine);

            Debug(name, routine, "Starting server...");
            var queue = new Queue<IncomingRequest>();
            var server = new FrwServer(ip, port, servername, queue, GlobalLoggingLevel);
            var t = new Thread(server.StartListener);
            t.Start();
            Debug(name, routine, "Server listening.");

            while (true)
            {
                while (IsGreaterThan(queue.Count, 0))
                {
                    Debug(name, routine, queue.Dequeue().msg);
                }
                Thread.Sleep(1000);
            }
        }
    }
}
