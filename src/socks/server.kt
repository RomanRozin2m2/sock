package socks

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.util.*
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
    }

}

fun main(){
    val srv = Server(1337, 50, "127.0.0.1")
    srv.mainloop()
}