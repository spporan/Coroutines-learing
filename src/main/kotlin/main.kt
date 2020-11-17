import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(){
    exampleBlocking()
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