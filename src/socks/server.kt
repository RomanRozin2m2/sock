package socks

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import kotlin.collections.ArrayList

class Server(val port: Int, val backlog: Int, val adress: String) {

    var msgs: Queue<String> = Queue<String>()
    var clients: ArrayList<Client> = ArrayList<Client>()
    val server: ServerSocket = ServerSocket(port, backlog, InetAddress.getByName(adress))

    fun mainloop() {
        Thread {
            while (true) {
                val connection: Socket = server.accept()
                val cl: Client = Client(connection.port, connection.inetAddress.toString())
                if (cl !in clients){
                    clients.add(cl)
                }
            }
        }.start()
        Thread {
            while (true){
                val currcl: Client
                for (currcl in clients) {

                }
            }
        }.start()
    }

}

fun main(){
    val srv = Server(1337, 50, "127.0.0.1")
    srv.mainloop()
}