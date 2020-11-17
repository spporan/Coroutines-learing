import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(){
    exampleBlocking()
}

suspend fun delayedPrint(msg:String){
    delay(1000)
    println(msg)
}
fun exampleBlocking(){
    println("One")
    runBlocking {
        delayedPrint("Two")

    }
    println("Three")
}