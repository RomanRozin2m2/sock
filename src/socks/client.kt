package socks

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket
import java.util.*

class Client(val port: Int, val adress: String) {

    fun connect() {

        val sock = Socket(
                InetAddress.getByName(adress),
                port)


        val input = DataInputStream(sock.getInputStream())
        val output = DataOutputStream(sock.getOutputStream())
        val sc = Scanner(System.`in`)

        val outputThread = Thread {
            while (true) {
                output.writeUTF(sc.nextLine())
            }
        }.start()

        val inputThread = Thread {
            while (true) {
                println(input.readUTF())
            }
        }.start()
    }

}

fun main(){
    val cl = Client(1337, "127.0.0.1")
    cl.connect()
}