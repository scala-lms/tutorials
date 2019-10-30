package scala.lms.tutorial

import java.io.FileReader
import java.io.BufferedReader

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels._
import collection.JavaConverters._

class StringScanner(line: String) {
  private[this] var pending: String = line
  def next(delim: Char): String = {
    if (delim == '\n' ) {
      pending
    } else {
      val i = pending.indexOf(delim)
      val field = pending.substring(0,i)
      pending = pending.substring(i+1)
      field
    }
  }
}

class Scanner(filename: String) {
  val br = new BufferedReader(new FileReader(filename))
  private[this] var pending: String = br.readLine()
  def next(delim: Char): String = {
    if (delim == '\n' ) {
      val field = pending
      pending = br.readLine()
      field
    } else {
      val i = pending.indexOf(delim)
      val field = pending.substring(0,i)
      pending = pending.substring(i+1)
      field
    }
  }
  def hasNext = pending ne null
  def close = br.close
}

class EventHandler(srcs: (String, Int)*) {

  def run(f: (Int, StringScanner) => Unit) = {

    val selector = Selector.open()
    val buffer = ByteBuffer.allocate(256);

    val socks = for ((addr, port) <- srcs) yield {
      val socketChannel = SocketChannel.open()
      socketChannel.connect(new InetSocketAddress(addr, port))
      socketChannel
    }

    try {
      while (true) {
        selector.select();
        for (key <- selector.selectedKeys.asScala)
          if (key.isReadable) {
            val client = key.channel.asInstanceOf[SocketChannel];
            client.read(buffer)
            f(socks.indexOf(client), new StringScanner(new String(buffer.array)))
          }
      }
    } finally {
      socks.foreach(_.close)
    }
  }
}
