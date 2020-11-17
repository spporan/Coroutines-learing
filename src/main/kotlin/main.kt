import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(){
   // exampleBlocking()
    exampleBlockingDispatcher()
}

suspend fun delayedPrint(msg:String){
    delay(1000)
    println(msg)
}
fun exampleBlocking()= runBlocking{
    println("One")

    delayedPrint("Two")

    println("Three")
}

//Running for another thread but still blocking  main thread
fun exampleBlockingDispatcher(){

    runBlocking(Dispatchers.Default) {
        println("One -from thread ${Thread.currentThread().name}")
        delayedPrint("Two -from thread ${Thread.currentThread().name}")

    }

    //outside the runBlocking to show that it's running in the blocked main thread
    println("Three -from thread ${Thread.currentThread().name}")

    //It still run after the runBlocking done

}