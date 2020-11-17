import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory
import kotlinx.coroutines.*

fun main(){
   // exampleBlocking()
    exampleLaunchGlobalWaiting()
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


fun exampleLaunchGlobal ( )= runBlocking {
    println("One -from thread ${Thread.currentThread().name}")

    //didn't blocking main thread
    GlobalScope.launch {
        delayedPrint("Two -from thread ${Thread.currentThread().name}")

    }
    println("Three -from thread ${Thread.currentThread().name}")


    //for delayed Global scope
    delay(3000)

}

fun exampleLaunchGlobalWaiting( )= runBlocking {
    println("One -from thread ${Thread.currentThread().name}")

    //didn't blocking main thread
    val job=GlobalScope.launch {
        delayedPrint("Two -from thread ${Thread.currentThread().name}")

    }
    println("Three -from thread ${Thread.currentThread().name}")


    //for waiting for  job
    job.join()

}

