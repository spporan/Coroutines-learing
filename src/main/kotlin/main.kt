import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory
import kotlinx.coroutines.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

fun main(){
   // exampleBlocking()
    exampleWithContext()
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

fun exampleLaunchCoroutineScope( )= runBlocking {
    println("One -from thread ${Thread.currentThread().name}")

    //didn't blocking main thread
    //only block the local thread means that coroutine scope
    //this means this bloc execute and wait for 1000milsec
    //but call from main thread
    //when use Dispatchers.Default then create DefaultDispatcher worker
    val customDispatcher=Executors.newFixedThreadPool(2).asCoroutineDispatcher()
   launch(customDispatcher) {
        delayedPrint("Two -from thread ${Thread.currentThread().name}")

    }
    println("Three -from thread ${Thread.currentThread().name}")

    //when use custom dispatcher the programme doesn't exit
    //need to shutdown programme manually

    (customDispatcher.executor as ExecutorService).shutdown()


}

suspend  fun calculateComplexThings(num:Int):Int{
    delay(1000)
    return  num*100
}

fun exampleAwaitAsync()= runBlocking {
    val startTime=System.currentTimeMillis()

    //this line of code run asynchronously
    val num1=async {calculateComplexThings(100000)}
    val  num2=async { calculateComplexThings(10000000) }
    val  num3=async { calculateComplexThings(100000000) }

    //but if we use await this line code then run synchronously then takes time 3000 more millisec
    /*val num1=async {calculateComplexThings(100000)}.await()
    val  num2=async { calculateComplexThings(10000000) }.await()
    val  num3=async { calculateComplexThings(100000000) }.await()*/
    //that's why take time only 1024 millisec
    val sum=num1.await()+num2.await()+num3.await()

    //removed await here and taken time 3000 mili more time
    // val sum=num1+num2+num3

    println("sum of the numbers =$sum")
    val endTime=System.currentTimeMillis()
    println("Time taken =${endTime-startTime}")

}

fun exampleWithContext()= runBlocking {
    val startTime=System.currentTimeMillis()

    //when don't to need to run concurrently then use withContext otherwise use async/await
    val result1= withContext(Dispatchers.Default) {calculateComplexThings(100000)}
    val  result2= withContext(Dispatchers.Default) { calculateComplexThings(10000000) }
    val  result3= withContext(Dispatchers.Default) { calculateComplexThings(100000000) }

    val sum=result1+result2+result3

    println("sum of the numbers =$sum")
    val endTime=System.currentTimeMillis()
    println("Time taken =${endTime-startTime}")

}