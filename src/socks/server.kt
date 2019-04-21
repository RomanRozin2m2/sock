package socks

import java.lang.Thread.sleep
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.CopyOnWriteArrayList


class Server(val port: Int, val backlog: Int, val adress: String) {

    var clients = CopyOnWriteArrayList<DataClient>()
    val server = ServerSocket(port, backlog, InetAddress.getByName(adress))

    fun mainloop() {
        Thread {
            while (true) {
                val connection: Socket = server.accept()
                val cl = DataClient(connection)
                clients.add(cl)
            }
        }.start()
        Thread {
            while (true) {
                clearCorpsesOfDeadClients()
                sleep(100)
                resendMessages()
            }
        }.start()
    }

    fun resendMessages() {
        for(c in clients){
            if (c.receivedFromClient.size > 0) {
                for (cd in clients) {
                    if (cd != c) {
                        cd.needToSendToClient.add(c.receivedFromClient.get(0))
                    }
                }
                c.receivedFromClient.removeAt(0)
            }
        }
    }

    fun clearCorpsesOfDeadClients() {
        for (c in clients){
            if (!c.isAlive){
                clients.remove(c)
            }
        }
    }

}

fun main(){
    val srv = Server(1337, 50, "127.0.0.1")
    srv.mainloop()
}