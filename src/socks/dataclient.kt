package socks

import java.io.DataInputStream
import java.io.DataOutputStream
import java.lang.Thread.sleep
import java.net.Socket

class DataClient(val socket: Socket) {

    val input = DataInputStream(socket.getInputStream())
    val output = DataOutputStream(socket.getOutputStream())

    val receivedFromClient = ArrayList<String>()
    val needToSendToClient = ArrayList<String>()

    init {
        Thread {
            while (true) {
                val input = input.readUTF()
                receivedFromClient.add(input)
            }
        }.start()
        Thread {
            while (true) {
                if (needToSendToClient.size > 0) {
                    val firstElem = needToSendToClient.get(0)
                    needToSendToClient.removeAt(0)
                    output.writeUTF(firstElem)
                }
                sleep(100)
            }
        }.start()
    }

}