package socks

import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import kotlin.collections.ArrayList

class Server(val port: Int, val backlog: Int, val adress: String) {

    var clients = ArrayList<DataClient>()
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
                for(c in clients){
                    if (c.receivedFromClient.size > 0) {
                        for (cd in clients) {
                            cd.needToSendToClient.add(c.receivedFromClient.get(0))
                            c.receivedFromClient.removeAt(0)
                        }
                    }
                }
            }
        }.start()
    }

}

fun main(){
    val srv = Server(1337, 50, "127.0.0.1")
    srv.mainloop()
}