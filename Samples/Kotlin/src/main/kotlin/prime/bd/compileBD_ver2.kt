package prime.bd

import java.io.FileOutputStream
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File
import java.io.FileWriter
import java.io.BufferedWriter

// Logger creado sin un nombre específico
val logger: Logger = LogManager.getLogger()




fun procesoGenerarBinarioPrimos(nombreFichero:String) {

    val startByte: Int = 0 // Cambia esto según la posición de inicio
      /* cada byte almacena 8 números; terminados en 1, 3, 7 o 9
         de forma, que en 64MB almacenamos 536.870.912 números

         en este caso el primer número representado es el 11
           y el último número representado es el 1.342.177.289
       */

    // Inicializamos una lista mutable vacía
    var numerosCandidatos = listOf(0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L)   //Cambia la declaración de numerosCandidatos para que use Long

    if (nombreFichero == "0000") {
        val nuevosValores1 = listOf(29L, 27L, 23L, 21L, 19L, 17L, 13L, 11L) // primeros 64MB
        numerosCandidatos = numerosCandidatos.mapIndexed { index, _ ->
            nuevosValores1[index] // Asignar los valores uno a uno desde la lista `nuevosValores`
        }
    }
    if (nombreFichero == "0001") {
        /*
        1342177289 + 20 = 1342177309
        1342177287 + 20 = 1342177307
        1342177283 + 20 = 1342177303
        1342177281 + 20 = 1342177301

        1342177279 + 20 = 1342177299
        1342177277 + 20 = 1342177297
        1342177273 + 20 = 1342177293
        1342177271 + 20 = 1342177291
                 */

        val nuevosValores2 = listOf(1342177309L, 1342177307L, 1342177303L, 1342177301L, 1342177299L, 1342177297L, 1342177293L, 1342177291L)
        numerosCandidatos = numerosCandidatos.mapIndexed { index, _ ->
            nuevosValores2[index] // Asignar los valores uno a uno desde la lista `nuevosValores`
        }
    }
    if (nombreFichero == "0002") {
        /*
        2684354569 + 20 = 2684354589L
        2684354567 + 20 = 2684354587L
        2684354563 + 20 = 2684354583L
        2684354561 + 20 = 2684354581L

        2684354559 + 20 = 2684354579L
        2684354557 + 20 = 2684354577L
        2684354553 + 20 = 2684354573L
        2684354551 + 20 = 2684354571L
         */
        val nuevosValores3 = listOf(2684354589L, 2684354587L, 2684354583L, 2684354581L, 2684354579L, 2684354577L, 2684354573L, 2684354571L)
        numerosCandidatos = numerosCandidatos.mapIndexed { index, _ ->
            nuevosValores3[index] // Asignar los valores uno a uno desde la lista `nuevosValores`
        }
    }
    logger.info("En fichero $nombreFichero guardamos los PRIMOS.")


    // Copiar los valores a un DoubleArray que usaremos para escribir el fichero CSV
    val primerosNumerosinicio: DoubleArray = numerosCandidatos.map { it.toDouble() }.toDoubleArray()


    var p = primo()
    var byte = 0  // Se usa Int para las operaciones bit a bit
    val endBytes = 64 * 1024 * 1024 // 64MB en bytes
    var buffer = ByteArray(endBytes) // Buffer de 64MB para almacenar los bits
    val listaNumerosGeneral = DoubleArray(endBytes+1) { 0.0 } // Inicializar con ceros, aqui guardamos la lista de numeros que luego volcaremos en un excel
    var punteroListaNumerosGeneral=0 // para moverse en el indice de listaNumerosGeneral

    val tiempoInicioCalculoPrimos = System.currentTimeMillis()
    logger.info("Iniciamos la generacion de PRIMOS")

    for (pos in startByte until endBytes-1) {

        if (pos == startByte){
            logger.info(" candidato en byte $startByte, para analizar: $numerosCandidatos")
        }
        if (pos == endBytes-1 ){
            logger.info(" candidato en byte ${endBytes-1}, para analizar: $numerosCandidatos")
        }

        for ((pos2, num2) in numerosCandidatos.withIndex()) {
            if (p.raizCuadrada(num2.toDouble())) {
             // byte = byte or (1 shl pos2)  // Activar el bit menos significativo primero
              byte = byte or (1 shl (7 - pos2))  // Activar el bit más significativo primero
                /* nota: el bit más significativo (MSB, por sus siglas en inglés) es el bit que se encuentra más a la izquierda.

                shl(n) es un "shift left", que mueve los bits de un número hacia la izquierda n posiciones.
                   Esto equivale a multiplicar el número por "2" elevado a "𝑛"
                El operador "or" es una operación bit a bit que activa (pone a 1) un bit si al menos uno de los operandos tiene ese bit en 1. Si ambos bits son 0, entonces el resultado es 0.

                 */
                if (num2<64) {  // para tener en los logs una pequeña muestra
                    logger.info("Número: $num2, bit: $pos2, Byte actual: ${byte.toString(2).padStart(8, '0')}")
                }
            }
        }

        buffer[pos] = byte.toByte() // Guardamos el byte en el buffer

        // Generamos la nueva lista sumando 20 a cada elemento para el próximo bucle
        //numerosCandidatos = numerosCandidatos.map { it + 20 } // esto produce desbordamiento y genera numeros negativos en ese momento
        numerosCandidatos = numerosCandidatos.map { it + 20L }
        byte = 0 // inicializamos para el siguiente conjunto de 8 numeros


    }

    /* El último byte se deja a cero como en el archivo original, ya que al compararlos sin hacer esto, la unica diferencia
    comparando ambos archivos ha sido en el byte 03FFFFFF: 00 A0 */
    byte = 0 // inicializamos para el siguiente conjunto de 8 numeros
    buffer[endBytes-1] = byte.toByte() // Guardamos el byte en el buffer

    val tiempoFinCalculoPrimos = System.currentTimeMillis()
    val tiempoTranscurrido = tiempoFinCalculoPrimos - tiempoInicioCalculoPrimos
    var tiempoSegundos = tiempoTranscurrido / 1000.0
    var tiempoMinutos = tiempoSegundos / 60
    var tiempoHoras = tiempoMinutos / 60
    logger.info("Tiempo transcurrido para generar PRIMOS: %.2f segundos (%.2f minutos, %.2f horas)".format(tiempoSegundos, tiempoMinutos, tiempoHoras))

    // Escribir todo el buffer en un solo paso
    val tiempoInicioFicBinario = System.currentTimeMillis()

    // directorio
     //val url:String="./PrimesDB/"
    val url:String="../../PrimesDB/"

    // escribimos fichero binario, con extensión pdb (primesDB)
    FileOutputStream(url+nombreFichero+".pdb").use { it.write(buffer) }

    val tiempoFinFicBinario = System.currentTimeMillis()
    val tiempoTranscurridoFicBinario = tiempoFinFicBinario - tiempoInicioFicBinario
    tiempoSegundos = tiempoTranscurridoFicBinario / 1000.0
    tiempoMinutos = tiempoSegundos / 60
    tiempoHoras = tiempoMinutos / 60
    logger.info("Tiempo transcurrido para grabar BINARIO: %.2f segundos (%.2f minutos, %.2f horas)".format(tiempoSegundos, tiempoMinutos, tiempoHoras))

       logger.info("fin del proceso.")

}

fun main() {

    procesoGenerarBinarioPrimos("0000") // primeros 64MB, MD5 del fichero 0000.pdb: bcf68203922ed3549b9225e8609924ad

    // procesoGenerarBinarioPrimos("0001") // segundos 64MB

    // procesoGenerarBinarioPrimos("0002") // tercer bloque de 64MB



}