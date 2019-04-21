package socks

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket

class DataClient(val socket: Socket) {

    val input = DataInputStream(socket.getInputStream())
    val output = DataOutputStream(socket.getOutputStream())

    val inputQueue = ArrayList<String>()
    val outputQueue = ArrayList<String>()

    init {
        Thread {
            while (true) {
                val input = input.readUTF()
                inputQueue.add(input)
            }
        }.start()
        Thread {
            while (true) {
                if (outputQueue.size > 0) {
                    val firstElem = outputQueue.get(0)
                    outputQueue.removeAt(0)
                    output.writeUTF(firstElem)
                }
            }
        }.start()
    }

}