package tests

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.util.*


fun main() {

    val server = ServerSocket(
        23456,
        50,
        InetAddress.getByName("192.168.0.109"))


    val connection: Socket = server.accept()

    val input = DataInputStream(connection.getInputStream())
    val output = DataOutputStream(connection.getOutputStream())

    val sc = Scanner(System.`in`)

    Thread {
        while (true) {
            println(input.readUTF())
        }
    }.start()

    Thread {
        while (true) {
            output.writeUTF(sc.nextLine())
        }
    }.start()
}
