package socks

import java.io.DataInputStream
import java.io.DataOutputStream
import java.lang.Thread.sleep
import java.net.Socket
import java.util.concurrent.CopyOnWriteArrayList

class DataClient(val socket: Socket) {

    val input = DataInputStream(socket.getInputStream())
    val output = DataOutputStream(socket.getOutputStream())

    val receivedFromClient = CopyOnWriteArrayList<String>()
    val needToSendToClient = CopyOnWriteArrayList<String>()
    var isAlive = true

    init {
        Thread {
            while (isAlive) {
                try {
                    val input = input.readUTF()
                    receivedFromClient.add(input)
                } catch (ex: Exception){
                    println("Клиент умер или что похуже")
                    isAlive = false
                }
            }
        }.start()
        Thread {
            while (isAlive) {
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