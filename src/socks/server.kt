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
                val cl = DataClient(connection.inetAddress, connection.port)
                if (cl !in clients){
                    clients.add(cl)
                }
            }
        }.start()
        Thread {
            while (true){
                val currcl: DataClient
                for (currcl in clients) {
                    val sock = Socket(currcl.adress,currcl.port)
                    val input = DataInputStream(sock.getInputStream())
                    val output = DataOutputStream(sock.getOutputStream())
                    output.writeUTF(input.readUTF())
                }
            }
        }.start()
    }

}

fun main(){
    val srv = Server(1337, 50, "127.0.0.1")
    srv.mainloop()
}